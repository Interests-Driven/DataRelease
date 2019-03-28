package com.fa.cim.release.common.service.api.basic;

import com.fa.cim.core.bo.CimBO;
import com.fa.cim.release.common.service.api.base.BOVerifyService;

/**
 * description:
 * <p>DefaultBOVerifyService .<br/></p>
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
public interface DefaultBOVerifyService extends BOVerifyService {

	<BO extends CimBO> BO verify(BO bo);

}
