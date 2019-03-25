package com.fa.cim.release.common.annotations;

import com.fa.cim.common.support.ObjectIdentifier;
import com.fa.cim.core.bo.BaseBO;
import com.fa.cim.core.bo.CimBO;
import com.fa.cim.core.bo.ManagerBO;
import com.fa.cim.entitysuper.BaseEntity;

import java.lang.annotation.*;

/**
 * description:
 * <p>DOChild .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 17:06
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DOChild {

	/**
	 * the class type of the child entity;
	 * @return ? extends BaseEntity
	 */
	Class<? extends BaseEntity> child ();

	/**
	 * the save manager class type
	 * @return ? extends ManagerBO
	 */
	Class<? extends ManagerBO> manager ();

	/**
	 * the names of the identities to query this entity;
	 * @return string
	 */
	String[] identifiers() default "";

//	Class<? extends BaseBO> boType ();
//
//	String queryAll ();
//
//	Class<?>[] queryAllParamType() default {BaseEntity.class};
//
//	String add ();
//
//	Class<?>[] addParamType () default {BaseEntity.class, String.class};
//
//	String remove ();
//
//	Class<?>[] removeParamType

}
