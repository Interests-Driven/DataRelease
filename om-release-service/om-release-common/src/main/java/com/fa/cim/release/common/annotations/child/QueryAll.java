package com.fa.cim.release.common.annotations.child;

import com.fa.cim.core.bo.BaseBO;
import com.fa.cim.entitysuper.BaseEntity;

import java.lang.annotation.*;

/**
 * description:
 * <p>QueryAll .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/26        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/26 10:17
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface QueryAll {

	String method();

}
