package com.fa.cim.release.common.factory;


import com.fa.cim.release.common.service.api.basic.DefaultBOReleaseService;

/**
 * description:
 * <p>BOReleaseServiceFactory .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 13:57
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
public interface BOReleaseServiceFactory {

	Class<DefaultBOReleaseService> DEFAULT_BO_RELEASE_SERVICE_CLASS = DefaultBOReleaseService.class;



}
