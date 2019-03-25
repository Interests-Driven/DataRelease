package com.fa.cim.release.common.annotations;

import com.fa.cim.common.support.ObjectIdentifier;
import com.fa.cim.core.bo.BaseBO;
import com.fa.cim.entitysuper.BaseEntity;

import java.lang.annotation.*;

/**
 * description:
 * <p>QueryBy .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 12:00
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface QueryBy {

	Class<? extends BaseBO> query();

	Class<? extends BaseEntity> type();

	String method() default "convertObjectIdentifierToEntity";

	Class<?>[] paramType() default {ObjectIdentifier.class, Class.class};

}
