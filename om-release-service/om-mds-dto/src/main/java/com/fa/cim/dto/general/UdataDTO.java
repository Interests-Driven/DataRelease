package com.fa.cim.dto.general;

import com.fa.cim.release.common.annotations.DOAttribute;
import lombok.Data;

/**
 * description:
 * <p>UdataDTO .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 15:00
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
public class UdataDTO{

	@DOAttribute(toAttribute = "type")
	private String type;

	@DOAttribute(toAttribute = "value")
	private String value;

	@DOAttribute(toAttribute = "name")
	private String name;

}
