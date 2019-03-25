package com.fa.cim.pojo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * description:
 * <p>CimMessageKey .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/19        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/2/19 13:12
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
public class CimMessageKey {

	private UUID uuid;
	private String transactionID;
	private Timestamp timestamp;

}
