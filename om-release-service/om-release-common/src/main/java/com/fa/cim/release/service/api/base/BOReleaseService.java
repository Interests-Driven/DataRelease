package com.fa.cim.release.service.api.base;

import com.fa.cim.entitysuper.BaseEntity;
import com.fa.cim.release.dto.AbstractDTO;

/**
 * description:
 * <p>BOReleaseService .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 12:05
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
public interface BOReleaseService<DTO extends AbstractDTO> {

	String DEFAULT_RELEASE_NAME = "release";

}
