package com.fa.cim.release.common.annotations.entity;

import java.lang.annotation.*;

/**
 * description:
 * <p>AsInfoStruct .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/26        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/26 12:07
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AsInfoStruct {

	Class<?> type ();

}
