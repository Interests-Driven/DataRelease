package com.fa.cim.release.common.annotations.standalone;


import com.fa.cim.release.common.service.api.base.BOVerifyService;
import com.fa.cim.release.common.service.api.basic.DefaultBOVerifyService;

import java.lang.annotation.*;

/**
 * description:
 * <p>VerifyBy .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 13:36
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface VerifyBy {

	/**
	 * the class returnType to verify the legality of the attribute/entity
	 * @return ? extends BOVerifyService
	 */
	Class<? extends BOVerifyService> type () default DefaultBOVerifyService.class;

	/**
	 * the name of the verify method with default value of "verify"
	 * @return string
	 */
	String release () default BOVerifyService.DEFAULT_VERIFY_NAME;

}
