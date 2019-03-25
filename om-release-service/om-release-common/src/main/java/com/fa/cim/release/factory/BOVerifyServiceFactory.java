package com.fa.cim.release.factory;

import com.fa.cim.release.service.api.basic.DefaultBOVerifyService;

/**
 * description:
 * <p>BOVerifyServiceFactory .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 13:37
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
public interface BOVerifyServiceFactory {

	Class<DefaultBOVerifyService> DEFAULT_BO_VERIFY_SERVICE_CLASS = DefaultBOVerifyService.class;

}
