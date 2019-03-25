package com.fa.cim.release.service.impl.basic;

import com.fa.cim.common.utils.SpringContextUtil;
import com.fa.cim.common.utils.StringUtils;
import com.fa.cim.core.bo.ManagerBO;
import com.fa.cim.entitysuper.BaseEntity;
import com.fa.cim.release.common.annotations.*;
import com.fa.cim.release.common.annotations.support.CimDTOProcessor;
import com.fa.cim.release.dto.AbstractDTO;
import com.fa.cim.release.service.api.basic.DefaultBOReleaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * description:
 * <p>DefaultBOReleaseServiceImpl .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 14:41
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Component
@Slf4j
public class DefaultBOReleaseServiceImpl implements DefaultBOReleaseService {

	@Autowired
	private CimDTOProcessor parser;

	@Override
	@SuppressWarnings("unchecked")
	public <DTO extends AbstractDTO> void release(DTO dto) {

		Class<DTO> dtoClass = (Class<DTO>) dto.getClass();

		DOEntity doEntity = dtoClass.getAnnotation(DOEntity.class);
		QueryBy doQueryBy = dtoClass.getAnnotation(QueryBy.class);
		Assert.notNull(doEntity, String.format("Annotation not Found [ %s ]", DOEntity.class.getSimpleName()));
		Assert.notNull(doQueryBy, String.format("Entity Query Rule not Defined Type : [ %s ]", DOEntity.class.getSimpleName()));
		Class<? extends BaseEntity> doClass = doEntity.type();
		BaseEntity doObject = parser.queryBOEntity(doEntity, doQueryBy, dto);
		if (null == doObject || StringUtils.isEmpty(doObject.getId())) {
			try {
				doObject = doClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Field[] dtoFields = dtoClass.getDeclaredFields();
		List<Field> doChildFields = new LinkedList<>(); // All Child Fields need to be processed after the save action of the parent;
		for (Field dtoField : dtoFields) {
			ReflectionUtils.makeAccessible(dtoField);
			if (dtoField.isAnnotationPresent(DOChild.class)) {
				doChildFields.add(dtoField);
			} else {
				if (dtoField.isAnnotationPresent(ConvertToDO.class)) {
					doObject = this.parser.processDOAttributeWithDOConversion(dtoField, doObject, dto);
				}
				else
					doObject = this.parser.processPlaintDOAttribute(dtoField, doObject, dto);
			}
		}

		ManagerBO mainManagerBO = SpringContextUtil.getBean(doEntity.manager());
		doObject = (BaseEntity) mainManagerBO.save(doObject);

		for (Field chiledField : doChildFields) {
			this.parser.processDOAttributeWithDOChild(chiledField, doObject, dto);
		}

	}


}
