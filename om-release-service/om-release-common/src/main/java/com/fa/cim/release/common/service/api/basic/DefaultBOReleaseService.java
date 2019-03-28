package com.fa.cim.release.common.service.api.basic;


import com.fa.cim.release.common.abstractobj.AbstractMain;
import com.fa.cim.release.common.service.api.base.BOReleaseService;

/**
 * description:
 * <p>DefaultBOReleaseServiceImpl .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 13:58
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
public interface DefaultBOReleaseService extends BOReleaseService<AbstractMain> {

	<DTO extends AbstractMain> void release(DTO dto);
}
