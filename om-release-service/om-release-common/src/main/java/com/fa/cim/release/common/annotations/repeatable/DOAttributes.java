package com.fa.cim.release.common.annotations.repeatable;

import com.fa.cim.release.common.annotations.DOAttribute;

import java.lang.annotation.*;

/**
 * description:
 * <p>DOAttributes .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 14:06
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DOAttributes {
	DOAttribute[] value();
}
