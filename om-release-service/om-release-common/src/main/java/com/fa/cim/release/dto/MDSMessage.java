package com.fa.cim.release.dto;

import lombok.Data;

/**
 * description:
 * <p>MDSMessage .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 14:46
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
public class MDSMessage <T extends AbstractDTO> {

	private String objectType;
	private T releaseContent;
	private String actionType;
	private String userID;

}
