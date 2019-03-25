package com.fa.cim.release.dto;

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
public class UdataDTO extends AbstractDTO{

	private String type;
	private String value;
	private String name;

}
