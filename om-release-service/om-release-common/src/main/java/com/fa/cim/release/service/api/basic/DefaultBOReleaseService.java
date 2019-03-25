package com.fa.cim.release.service.api.basic;

import com.fa.cim.entitysuper.BaseEntity;
import com.fa.cim.release.dto.AbstractDTO;
import com.fa.cim.release.service.api.base.BOReleaseService;

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
public interface DefaultBOReleaseService extends BOReleaseService <AbstractDTO> {

	<DTO extends AbstractDTO> void release (DTO dto);
}
