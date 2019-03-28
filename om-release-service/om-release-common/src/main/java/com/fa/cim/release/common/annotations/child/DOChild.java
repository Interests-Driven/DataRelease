package com.fa.cim.release.common.annotations.child;

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
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DOChild {

	/**
	 * the class returnType of the child entity;
	 * @return ? the return returnType of the child calling method
	 */
	Class<?> child ();

	/**
	 * the names of the identities to cimBO this entity;
	 * @return string
	 */
	String[] identifiers() default "";

	Class<? extends BaseBO> cimBO();

}
