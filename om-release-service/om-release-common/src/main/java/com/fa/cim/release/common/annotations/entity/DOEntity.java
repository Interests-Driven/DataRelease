package com.fa.cim.release.common.annotations.entity;

import com.fa.cim.common.support.ObjectIdentifier;
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
	 * the entity class returnType
	 * @return ? extends BaseEntity
	 */
	Class<? extends BaseEntity> type ();

	/**
	 * the unique identifier of the target entity;
	 * for referencing other attribute of the DTO, add prefix "this.{attributeName}"
	 * @return string
	 */
	String[] identifiers();

	/**
	 * the queryBy that is responsible for the entity;
	 * @return ? extends BaseManager
	 */
	Class<?> query ();

	/**
	 * the name of the query method
	 * @return string
	 */
	String method() default "convertObjectIdentifierToEntity";

	/**
	 * the param type of the query method and by default method, the class type is excluded as it has a different process
	 * logic for the default method
	 * @return any type
	 */
	Class<?>[] paramType() default {ObjectIdentifier.class};

}
