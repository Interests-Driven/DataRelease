package com.fa.cim.release.common.annotations.entity;

import com.fa.cim.core.bo.BaseBO;
import com.fa.cim.entitysuper.BaseEntity;

import java.lang.annotation.*;

/**
 * description:
 * <p>SetInfoBy .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/26        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/26 10:26
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SetInfoBy {

	/**
	 * the cimBO object that use to call the set method;
	 * @return ? extends BaseBO
	 */
	Class<? extends BaseBO> cimBO();

	/**
	 * the name of the set method
	 * @return string
	 */
	String method();

	/**
	 * the paramType of the set method, the main object is excluded and it will be added during the process
	 * @return any type
	 */
	Class<?> paramType();

}
