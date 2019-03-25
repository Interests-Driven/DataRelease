package com.fa.cim.release.common.annotations;

import com.fa.cim.core.bo.ManagerBO;
import com.fa.cim.core.impl.bo.BaseManager;
import com.fa.cim.entitysuper.BaseEntity;

import java.lang.annotation.*;

/**
 * description:
 * <p>DOEntity .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 11:52
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DOEntity {

	/**
	 * the queryBy that is responsible for the entity;
	 * @return ? extends BaseManager
	 */
	Class<? extends ManagerBO> manager ();

	/**
	 * the entity class type
	 * @return ? extends BaseEntity
	 */
	Class<? extends BaseEntity> type ();

	/**
	 * the unique identifier of the target entity;
	 * for referencing other attribute of the DTO, add prefix "this.{attributeName}"
	 * @return string
	 */
	String[] identifiers();

}
