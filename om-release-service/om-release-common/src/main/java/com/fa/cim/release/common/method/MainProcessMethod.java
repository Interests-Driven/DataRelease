package com.fa.cim.release.common.method;

import com.fa.cim.common.exception.ServiceException;
import com.fa.cim.common.support.ObjectIdentifier;
import com.fa.cim.common.utils.ObjectUtils;
import com.fa.cim.common.utils.SpringContextUtil;
import com.fa.cim.common.utils.StringUtils;
import com.fa.cim.core.bo.BaseBO;
import com.fa.cim.entitysuper.BaseEntity;
import com.fa.cim.release.common.abstractobj.AbstractMain;
import com.fa.cim.release.common.annotations.DOAttribute;
import com.fa.cim.release.common.annotations.entity.AsInfoStruct;
import com.fa.cim.release.common.annotations.entity.DOEntity;
import com.fa.cim.release.common.annotations.entity.SetInfoBy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * <p>MainProcessMethod .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/26        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/26 10:06
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Service
@Slf4j
public class MainProcessMethod {

	private static final String DEFAULT_CONVERT_METHOD_NAME = "convertObjectIdentifierToEntity";

	/**
	 * Extract the main Object from the database if the object not found return a new instance;
	 * @param dto dto object
	 * @param <M> extend from AbstractMain of the DTO
	 * @param <E> extend from the BaseEntity of the OM System
	 * @return E
	 */
	@SuppressWarnings("unchecked")
	public <M extends AbstractMain, E extends BaseEntity> E getMainDataObject (M dto) {
		Class<M> dtoClass = (Class<M>) dto.getClass();
		DOEntity doEntityAnno = dtoClass.getAnnotation(DOEntity.class);
		Assert.notNull(doEntityAnno, "Coding Error - Not Found Annotation [ DOEntity ]");

		String[] idFieldNames =  doEntityAnno.identifiers();

		String queryMethodName = doEntityAnno.method();
		Class[] paramTypes = doEntityAnno.paramType();

		// check if it is a default method

		boolean isDefaultQueryMethod = StringUtils.equals(DEFAULT_CONVERT_METHOD_NAME, queryMethodName)
				&& paramTypes.length == 1 && ObjectIdentifier.class.equals(paramTypes[0]);
		if (isDefaultQueryMethod) {
			paramTypes = new Class[]{ObjectIdentifier.class, Class.class};
		}

		// Get the Query Method
		Class<?> boType = doEntityAnno.query();

		Method queryMethod = ReflectionUtils.findMethod(boType, queryMethodName, paramTypes);
		Assert.notNull(queryMethod, String.format("Coding Error - Not Found Method [ %s ]", queryMethodName));

		Object queryBO = SpringContextUtil.getBean(boType);
		Assert.notNull(queryBO, String.format("Coding Error - Not Found CimBO Bean [ %s ]", boType.getSimpleName()));

		// Get the query params
		Object[] queryParams = new Object[paramTypes.length];
		if (isDefaultQueryMethod) {
			Field idField = ReflectionUtils.findField(dtoClass, idFieldNames[0]);
			Assert.notNull(idField, String.format("Coding Error - Not Found Field [ %s ]", idFieldNames[0]));
			ReflectionUtils.makeAccessible(idField);
			String id = (String) ReflectionUtils.getField(idField, dto);
			queryParams[0] = ObjectIdentifier.buildWithValue(id);
			queryParams[1] = doEntityAnno.type();
		} else {
			int index = 0;
			for (String idFieldName : idFieldNames) {
				Field idField = ReflectionUtils.findField(dtoClass, idFieldName);
				Assert.notNull(idField, String.format("Coding Error - Not Found Field [ %s ]", idFieldName));
				queryParams[index++] = ReflectionUtils.getField(idField, dto);
			}
		}

		E queryResult = (E) ReflectionUtils.invokeMethod(queryMethod, queryBO, queryParams);

		// create a new object if the instance is not found
		if (ObjectUtils.isEmpty(queryResult) || StringUtils.isEmpty(queryResult.getId())) {
			queryResult = (E) this.createNewInstanceByClass(doEntityAnno.type());
		}

		return queryResult;
	}

	/**
	 * Apply to those entity type that annotated with @SetInfoBy
	 * @param dto AbstractMain
	 * @param doEntity main doEntity
	 * @param <M> AbstractMain
	 * @param <E> BaseEntity
	 */
	@SuppressWarnings("unchecked")
	public <M extends AbstractMain, E extends BaseEntity> void releaseByInfoStruct (M dto, E doEntity) {

		Assert.notNull(dto, "System Error - Null DTO Object");
		Assert.notNull(doEntity, "System Error - Null BaseEntity Object");

		Class<M> dtoClass = (Class<M>) dto.getClass();
		DOEntity doEntityAnno = dto.getClass().getAnnotation(DOEntity.class);
		Assert.notNull(doEntityAnno, "Coding Error - Not Found Annotation [ DOEntity ]");
		SetInfoBy setInfoByAnno = dtoClass.getAnnotation(SetInfoBy.class);
		Assert.notNull(setInfoByAnno, "Coding Error - Not Found Annotation [ SetInfoBy ]");

		Class<?> infoStructType =  setInfoByAnno.paramType();
		Object infoStruct = this.convertIntoInfoStruct(dto, infoStructType);

		Class<? extends BaseBO> boType = setInfoByAnno.cimBO();
		BaseBO cimBO = SpringContextUtil.getBean(boType);
		Assert.notNull(cimBO, String.format("Coding Error - Not Found CimBO Bean [ %s ]", boType.getSimpleName()));

		Class[] paramTypes = new Class[] {doEntityAnno.type(), infoStructType};

		String setMethodName = setInfoByAnno.method();
		Method setInfoMethod = ReflectionUtils.findMethod(boType, setMethodName, paramTypes);
		Assert.notNull(setInfoMethod, String.format("Coding Error - Not Found Method [ %s ]", setMethodName));
		ReflectionUtils.invokeMethod(setInfoMethod, cimBO, doEntity, infoStruct);
	}

	/*
	 *  --------------------------------------- Private Utility Methods ------------------------------------------------
	 */

	/**
	 * Convert the value into the target infoStructType Object
	 * @param source the source to convert from
	 * @param infoStrutType the target type;
	 * @return infoStructType
	 */
	private <E> E convertIntoInfoStruct (Object source, Class<E> infoStrutType) {
		// return null if the source is found null;
		if (ObjectUtils.isEmpty(source))
			return null;

		Class valueType = source.getClass();
		E infoStruct = this.createNewInstanceByClass(infoStrutType);

		// Populating the infoStruct by the source's @DOAttribute on the fields
		Field[] srcFields = valueType.getDeclaredFields();
		if (ObjectUtils.isEmpty(srcFields))
			return null;
		for (Field srcField : srcFields) {
			if(srcField.isAnnotationPresent(DOAttribute.class)) {
				DOAttribute doAttributeAnno = srcField.getAnnotation(DOAttribute.class);
				String toFieldName = doAttributeAnno.toAttribute();
				Field toField = ReflectionUtils.findField(infoStrutType, toFieldName);
				Assert.notNull(toField, String.format("Coding Error - Not Found Field [ %s ]", toFieldName));
				ReflectionUtils.makeAccessible(toField);

				Object fromValue = this.getValueFromObject(srcField, source);
				ReflectionUtils.setField(toField, infoStruct, fromValue);
			}
		}
		return infoStruct;
	}

	/**
	 * Get the value as General Object, if the field is annotated with @AsInfoStruct, the value will be converted into
	 * a the struct info that is set within @AsInfoStruct.
	 * @param field source field that is one of the attribute need to set to the target
	 * @param source the value of the field that is getting from
	 * @return Object with the value ready to be set into the target
	 */
	@SuppressWarnings("unchecked")
	private Object getValueFromObject (Field field, Object source) {

		// find the source value
		ReflectionUtils.makeAccessible(field);
		Object value = ReflectionUtils.getField(field, source);

		// return null if the value is found null;
		if (ObjectUtils.isEmpty(value))
			return null;

		Object result = null;

		// determine if the value of the source need to be converted (infoStruct / to a different type)
		boolean infoStructFlag = field.isAnnotationPresent(AsInfoStruct.class);
		AsInfoStruct infoStructAnno = field.getAnnotation(AsInfoStruct.class); // is null if infoStructFlag is false;
		DOAttribute doAttributeAnno = field.getAnnotation(DOAttribute.class);
		if (value instanceof List) { // for list, it is the each element in the list needed to be processed instead of the list itself
			List valueList = (List) value;
			List infoStructList = new ArrayList<>(valueList.size());
			for (Object toValue : valueList) {
				if (infoStructFlag) {
					infoStructList.add(this.convertIntoInfoStruct(toValue, infoStructAnno.type()));
				} else {
					infoStructList.add(this.convert(toValue, doAttributeAnno.convertTo()));
				}
			}
			result = infoStructList;
		} else {
			if (infoStructFlag) {
				result = this.convertIntoInfoStruct(value, infoStructAnno.type());
			} else {
				result = this.convert(value, doAttributeAnno.convertTo());
			}
		}

		return result;
	}

	/**
	 * Create a new instance based on the given class
	 * @param type class type
	 * @param <E> class type
	 * @return new instance
	 */
	private <E> E createNewInstanceByClass (Class<E> type) {
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			ServiceException serviceException = new ServiceException("Fatal - Failed to create Object");
			serviceException.setData(e);
			throw serviceException;
		}
	}

	/**
	 * Convert the value base on the provided type;
	 * @param value source value
	 * @param convertTo target type
	 * @return Converted value;
	 */
	@SuppressWarnings("unchecked")
	private Object convert(Object value, DOAttribute.Type convertTo) {
		if (value instanceof Short && convertTo == DOAttribute.Type.BOOLEAN) {
			return this.convert((Short) value);
		}
		if (value instanceof Number) {
			Class<?> convertType = convertTo.getClassType();
			switch (convertTo) {
				case LONG:
					return this.convert((Class<Long>) convertType, value, "longValue");
				case INT:
					return this.convert((Class<Integer>) convertType, value, "intValue");
				case FLOAT:
					return this.convert((Class<Float>) convertType, value, "floatValue");
				case BYTE:
					return this.convert((Class<Byte>) convertType, value, "byteValue");
				case DOUBLE:
					return this.convert((Class<Double>) convertType, value, "doubleValue");
				case SHORT:
					return this.convert((Class<Short>) convertType, value, "shortValue");
			}
		}
		if (convertTo == DOAttribute.Type.STRING) {
			return value.toString();
		} else if (convertTo == DOAttribute.Type.OBJECT_IDENTIFIER) {
			return ObjectIdentifier.buildWithValue(String.valueOf(value));
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	private  <N extends Number> N convert (Class<N> valueClass, Object value, String methodName) {
		Method method = ReflectionUtils.findMethod(valueClass, methodName);
		Assert.notNull(method, String.format( "%s Method Not Found", methodName));
		return (N) ReflectionUtils.invokeMethod(method, value);
	}

	private Boolean convert (Short value) {
		return value != 0;
	}

}
