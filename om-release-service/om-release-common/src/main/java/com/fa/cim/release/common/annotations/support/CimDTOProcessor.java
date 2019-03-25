package com.fa.cim.release.common.annotations.support;

import com.fa.cim.common.support.ObjectIdentifier;
import com.fa.cim.common.utils.ObjectUtils;
import com.fa.cim.common.utils.SpringContextUtil;
import com.fa.cim.common.utils.StringUtils;
import com.fa.cim.core.bo.BaseBO;
import com.fa.cim.core.bo.ManagerBO;
import com.fa.cim.entitysuper.BaseEntity;
import com.fa.cim.release.common.annotations.*;
import com.fa.cim.release.dto.AbstractDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


/**
 * description:
 * <p>AnnotationParser .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 21:23
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Component
@Slf4j
public class CimDTOProcessor {

	private static final String THIS = "this"; // refer to the DTO;
	private static final String MAIN = "main"; // refer to The main DO;
	private static final String ATTRIBUTE = "attribute"; // refer to the attribute within the attribute of the main;

	private static final String DEFAULT_METHOD_NAME = "convertObjectIdentifierToEntity";
	/**
	 * description:
	 * populate the target baseEntity from the dto class that sent from the MDS by the rule according to the DOAttributes
	 * and the QueryBy attribute;
	 * @TODO Do not support Collection attributes in the dto, because it seems not to be necessary;
	 * ConvertToDO and DOAttributes
	 * change history:
	 * date             defect             person             comments
	 * ---------------------------------------------------------------------------------------------------------------------
	 *
	 * @param field current processing field
	 * @param targetObj target object that need to be populated
	 * @param dto the source of the data
	 * @return populated ? extends BaseEntity
	 * @author Yuri
	 * @date 2019/3/18 01:36:27
	 */
	@SuppressWarnings("unchecked")
	public <DO extends BaseEntity, DTO extends AbstractDTO> DO processDOAttributeWithDOConversion(Field field,
	                                                                                              DO targetObj,
	                                                                                              DTO dto) {


		ConvertToDO convertToDO = field.getAnnotation(ConvertToDO.class);
		DOAttribute[] doAttributes = field.getAnnotationsByType(DOAttribute.class);
		QueryBy queryBy = field.getAnnotation(QueryBy.class);
		BaseEntity converted = convertToDO(convertToDO, queryBy, field, dto);

		for (DOAttribute doAttribute : doAttributes) {
			String fromAttributeName = doAttribute.fromAttribute();
			Object value = this.getValue(fromAttributeName, converted);
			String toAttributeName = doAttribute.toAttribute();
			DOAttribute.Type convertTo = doAttribute.convertTo();
			targetObj = this.convertAndSet(toAttributeName, targetObj, value, convertTo);
		}

		return targetObj;
	}

	/**
	 * description:
	 * populate the target baseEntity from the plaint rule of DOAttribute;
	 * @TODO Do not support Collection attributes in the dto, because it seems not to be necessary;
	 * change history:
	 * date             defect             person             comments
	 * ---------------------------------------------------------------------------------------------------------------------
	 *
	 * @param field current processing field
	 * @param targetObj target object that need to be populated
	 * @param dto the source of the data
	 * @return populated ? extends BaseEntity
	 * @return
	 * @author Yuri
	 * @date 2019/3/18 14:28:42
	 */
	@SuppressWarnings("unchecked")
	public <DO extends BaseEntity, DTO extends AbstractDTO> DO processPlaintDOAttribute(Field field,
	                                                                                    DO targetObj,
	                                                                                    DTO dto) {
		DOAttribute[] doAttributes = field.getAnnotationsByType(DOAttribute.class);
		Class<DTO> dtoType = (Class<DTO>) dto.getClass();
		for (DOAttribute doAttribute: doAttributes) {
			String attrName = doAttribute.fromAttribute();
			Field fromField = StringUtils.isEmpty(attrName) ? field : ReflectionUtils.findField(dtoType, attrName);
			Assert.notNull(fromField, "Coding Error -> Field Not Found");
			Object value = ReflectionUtils.getField(fromField, dto);
			String targetAttrName = doAttribute.toAttribute();
			DOAttribute.Type convertTo = doAttribute.convertTo();
			targetObj = this.convertAndSet(targetAttrName, targetObj, value, convertTo);
		}
		return targetObj;
	}

	@SuppressWarnings("unchecked")
	public <DO extends BaseEntity, DTO extends AbstractDTO> DO queryBOEntity (DOEntity doEntity, QueryBy queryBy, DTO dto) {
		String[] identifiers = doEntity.identifiers();
		Object[] queryArgs = this.processQueryByForArgs(queryBy, identifiers, dto, null, true);
		Class<? extends BaseBO> queryClass = queryBy.query();
		BaseBO queryBO = SpringContextUtil.getBean(queryClass);
		Method queryMethod = ReflectionUtils.findMethod(queryClass, queryBy.method(), queryBy.paramType());
		Assert.notNull(queryMethod, String.format("Method Not Found [ %s ]", queryBy.method()));
		return (DO) ReflectionUtils.invokeMethod(queryMethod, queryBO, queryArgs);
	}

	@SuppressWarnings("unchecked")
	public <DO extends BaseEntity, DTO extends AbstractDTO> DO queryChildBOEntity(DOChild doChild, DTO dto) {
//		String[] identifiers = doChild.identifiers();
//		Object[] queryArgs = this.processQueryByForArgs(doChild.paramType(), doChild.method(), doChild.child(), identifiers, dto, null, true);
//		Class<? extends BaseBO> queryClass = doChild.query();
//		BaseBO queryBO = SpringContextUtil.getBean(queryClass);
//		Method queryMethod = ReflectionUtils.findMethod(queryClass, doChild.method(), doChild.paramType());
//		Assert.notNull(queryMethod, String.format("Method Not Found [ %s ]", doChild.method()));
//		return (DO) ReflectionUtils.invokeMethod(queryMethod, queryBO, queryArgs);
		return null;
	}

	@SuppressWarnings("unchecked")
	public <DO extends BaseEntity, DTO extends AbstractDTO> void processDOAttributeWithDOChild(Field field,
	                                                                                         DO parentObj,
	                                                                                         DTO dto) {
		DOChild doChild = field.getAnnotation(DOChild.class);
		DOAttribute[] doAttributes = field.getAnnotationsByType(DOAttribute.class);
		QueryBy queryBy = field.getAnnotation(QueryBy.class);

		boolean isCollection = Collection.class.isAssignableFrom(field.getDeclaringClass());
		ReflectionUtils.makeAccessible(field);

		Class<? extends ManagerBO> managerBOClass = doChild.manager();
		ManagerBO managerBO = SpringContextUtil.getBean(managerBOClass);
		Object value = ReflectionUtils.getField(field, dto);
		if (isCollection){
			Collection<AbstractDTO> collection = (Collection<AbstractDTO>) value;
			if (!ObjectUtils.isEmpty(collection)) {
				for (AbstractDTO obj : collection) {
					BaseEntity childEntity = this.processChild(doChild, queryBy, doAttributes, dto, obj, parentObj);
					managerBO.save(childEntity);
				}
			}
		} else {
			BaseEntity childEntity = this.processChild(doChild, queryBy, doAttributes, dto, (AbstractDTO) value, parentObj);
			managerBO.save(childEntity);
		}
	}

	@SuppressWarnings("unchecked")
	public <DO extends BaseEntity, DTO extends AbstractDTO> DO processChild (DOChild doChild,
	                                                                          QueryBy queryBy,
	                                                                          DOAttribute[] doAttributes,
	                                                                          DTO dto, DTO obj, BaseEntity parentObj) {

		String[] identifiers = doChild.identifiers();

		BaseBO queryBO = SpringContextUtil.getBean(queryBy.query());
		Assert.notNull(queryBO, "Not Found Query BO");
		Method queryMethod = ReflectionUtils.findMethod(queryBy.query(), queryBy.method(), queryBy.paramType());
		Assert.notNull(queryMethod, String.format("Not Found Method [ %s ]", queryBy.method()));
		BaseEntity childEntity = this.queryChildBOEntity(doChild, obj);
		if (ObjectUtils.isEmpty(childEntity) || StringUtils.isEmpty(childEntity.getId())) {
			try {
				childEntity = doChild.child().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Object[] args = this.processQueryByForArgs(queryBy, identifiers, obj, parentObj, false);
		BaseEntity externalEntity = (BaseEntity) ReflectionUtils.invokeMethod(queryMethod, queryBO, args);
		for (DOAttribute doAttribute: doAttributes) {
			String from = doAttribute.fromAttribute();
			String[] fromSplited = from.split("\\.");
			boolean isWithPrefix = fromSplited.length > 1;
			String fromFieldName = isWithPrefix ? fromSplited[1] : fromSplited[0];
			Object fromObject = isWithPrefix && StringUtils.equals(fromSplited[1], MAIN) ? parentObj : externalEntity;
			fromObject = isWithPrefix && StringUtils.equals(fromSplited[1], THIS) ? dto : fromObject;
			Object fromValue =  this.getValue(fromFieldName, fromObject);
			childEntity = this.convertAndSet(doAttribute.toAttribute(), childEntity, fromValue, doAttribute.convertTo());
		}
		Class<DTO> objClass = (Class<DTO>) obj.getClass();

		if (objClass.isAnnotationPresent(DOEntity.class)) {
			List<Field> childFieldList = new LinkedList<>();
			Field[] childFields = objClass.getDeclaredFields();
			for (Field childField : childFields) {
				if (childField.isAnnotationPresent(DOChild.class)) {
					childFieldList.add(childField);
				} else {
					if (childField.isAnnotationPresent(ConvertToDO.class))
						this.processDOAttributeWithDOConversion(childField, externalEntity, obj);
					else
						this.processPlaintDOAttribute(childField, externalEntity, obj);
				}

			}
			for (Field childField: childFieldList) {
				this.processDOAttributeWithDOChild(childField, parentObj, obj);
			}
		}

		return (DO) childEntity;
	}



	/*
	 *******************************************************************************************
	 *
	 *                                  Utility Private Methods
	 *
	 * *****************************************************************************************
	 */
	private Object getValue (String attrName, Object fromObj) {

		Class fromObjClass = fromObj.getClass();
		Field field = ReflectionUtils.findField(fromObjClass, attrName);
		Assert.notNull(field, String.format("Field not Found [ %s ]", attrName));
		ReflectionUtils.makeAccessible(field);
		return ReflectionUtils.getField(field, fromObj);
	}

	private <DO extends BaseEntity> DO setValue (String attrName, DO targetObj, Object value) {
		Class targetObjClass = targetObj.getClass();
		Field field = ReflectionUtils.findField(targetObjClass, attrName);
		Assert.notNull(field, String.format("Field not Found [ %s ]", attrName));
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, targetObj, value);
		return targetObj;
	}

	@SuppressWarnings("unchecked")
	private <DO extends BaseEntity> DO convertAndSet (String attrName, DO targetObj, Object value, DOAttribute.Type convertTo) {
		if (value instanceof Short && convertTo == DOAttribute.Type.BOOLEAN) {
			Boolean boolValue = this.convert((Short) value);
			return this.setValue(attrName, targetObj, boolValue);
		}
		if (value instanceof Number) {
			Class<?> convertType = convertTo.getClassType();
			switch (convertTo) {
				case LONG:
					Long longValue = this.convert((Class<Long>) convertType, value, "longValue");
					return this.setValue(attrName, targetObj, longValue);
				case INT:
					Integer intValue = this.convert((Class<Integer>) convertType, value, "intValue");
					return this.setValue(attrName, targetObj, intValue);
				case FLOAT:
					Float floatValue = this.convert((Class<Float>) convertType, value, "floatValue");
					return this.setValue(attrName, targetObj, floatValue);
				case BYTE:
					Byte byteValue = this.convert((Class<Byte>) convertType, value, "byteValue");
					return this.setValue(attrName, targetObj, byteValue);
				case DOUBLE:
					Double doubleValue = this.convert((Class<Double>) convertType, value, "doubleValue");
					return this.setValue(attrName, targetObj, doubleValue);
				case SHORT:
					Short shortValue = this.convert((Class<Short>) convertType, value, "shortValue");
					return this.setValue(attrName, targetObj, shortValue);
			}
		}
		if (convertTo == DOAttribute.Type.STRING) {
			String stringValue = value.toString();
			return this.setValue(attrName, targetObj, stringValue);
		}
		return this.setValue(attrName, targetObj, value);
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

	private boolean isDefaultIdentifiers (String [] identifiers) {
		return identifiers.length < 2 && StringUtils.isEmpty(identifiers[0]);
	}

	@SuppressWarnings("unchecked")
	private <DO extends BaseEntity, DTO extends AbstractDTO> DO convertToDO (ConvertToDO convertToDO,
	                                                                         QueryBy queryBy,
	                                                                         Field field,
	                                                                         DTO dto, DO parent) {
		Class<? extends BaseBO> queryClass = queryBy.query();
		Method method = ReflectionUtils.findMethod(queryClass, queryBy.method(), queryBy. paramType());
		Assert.notNull(method, String.format("Method not Defined [ %s ]", queryBy.method()));
		BaseBO queryBO = SpringContextUtil.getBean(queryClass);
		String[] identifiers = convertToDO.identifiers();
		if (isDefaultIdentifiers(identifiers))
			identifiers[0] = field.getName();
		Object[] args = processQueryByForArgs(queryBy, identifiers, dto, parent,false);
		return  (DO) ReflectionUtils.invokeMethod(method, queryBO, args);
	}
	private <DO extends BaseEntity, DTO extends AbstractDTO> DO convertToDO (ConvertToDO convertToDO,
	                                                                         QueryBy queryBy,
	                                                                         Field field,
	                                                                         DTO dto) {
		return this.convertToDO(convertToDO, queryBy, field, dto, null);
	}

	@SuppressWarnings("unchecked")
	private  <DTO extends AbstractDTO, DO extends BaseEntity> Object[] processQueryByForArgs(QueryBy queryBy,
	                                                                                         String[] identifiers,
	                                                                                         DTO dto,
	                                                                                         DO parent,
	                                                                                         boolean entityFlag) {

		return this.processQueryByForArgs(queryBy.paramType(), queryBy.method(), queryBy.type(), identifiers, dto, parent, entityFlag);
	}

	@SuppressWarnings("unchecked")
	private  <DTO extends AbstractDTO, DO extends BaseEntity> Object[] processQueryByForArgs(Class<?>[] paramTypes,
	                                                                                         String method,
	                                                                                         Class<? extends BaseEntity> type,
	                                                                                         String[] identifiers,
	                                                                                         DTO dto,
	                                                                                         DO parent,
	                                                                                         boolean entityFlag) {

		Class<DTO> dtoClass = (Class<DTO>) dto.getClass();
		Class<DO> parentClass = (Class<DO>) (ObjectUtils.isEmpty(parent) ? BaseEntity.class : parent.getClass());

		boolean isDefaultQueryMethod = StringUtils.equals(DEFAULT_METHOD_NAME, method);
		int argsSize = isDefaultQueryMethod ? 2: paramTypes.length;
		Assert.isTrue(argsSize == paramTypes.length, "Mismatched Parameters");
		Object[] args = new Object[argsSize];
		for (int i = 0; i < argsSize; i ++) {
			String nameWithPrefix = identifiers[i];
			String [] nameArr = nameWithPrefix.split("\\.");
			boolean withPrefix = nameArr.length > 1;
			Object fromObject = dto;
			String fieldName = null;
			Class<?> fromClass = dtoClass;
			switch (nameArr[0]) {
				case ATTRIBUTE:
				case THIS:
					fieldName = nameArr[1];
					fromClass = dtoClass;
					break;
				case MAIN:
					Assert.notNull(parent, "No Parent Object Found !!! ");
					fieldName = nameArr[1];
					fromObject = parent;
					fromClass = parentClass;
					break;
				default:
					fieldName = nameArr[0];
					break;
			}
			Field idField = ReflectionUtils.findField(fromClass, fieldName);
			Assert.notNull(idField, String.format("Field Not Defined [ %s ]", fieldName));
			ReflectionUtils.makeAccessible(idField);
			Object value = ReflectionUtils.getField(idField, fromObject);
			if (isDefaultQueryMethod) {
				args[0] = ObjectIdentifier.buildWithValue((String) value);
				args[1] = type;
				break;
			}
			if (entityFlag && idField.isAnnotationPresent(ConvertToDO.class)) {
				args[i] = this.convertToDO(idField.getAnnotation(ConvertToDO.class), idField.getAnnotation(QueryBy.class), idField, dto, parent);
			} else {
				args[i] = value;
			}
			log.info(String.format("The args[%d] type is [ %s ]", i, args[i].getClass().getSimpleName()));
		}
		return args;
	}





}
