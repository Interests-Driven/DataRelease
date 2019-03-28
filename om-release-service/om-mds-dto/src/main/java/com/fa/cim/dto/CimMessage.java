package com.fa.cim.dto;

import com.alibaba.fastjson.JSONObject;
import com.fa.cim.common.support.User;
import lombok.Data;

/**
 * description:
 * The standard Request message DTO for calling the transaction of the OM service
 * <p>CimMessage .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/25        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/25 17:39
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
public class CimMessage {

	private User user;
	private String functionID;
	private Long sendTime;
	private Object requestBody;

	/**
	 * description:
	 * Create the CimMessage that can be sent with CimMessageWrapper
	 * <p><br/></p>
	 * change history:
	 * date             defect             person             comments
	 * ---------------------------------------------------------------------------------------------------------------------
	 *
	 * @param user User
	 * @param functionID String
	 * @param requestBody T
	 * @return CimMessage
	 * @author Yuri
	 * @date 2019/3/25 19:38:09
	 */
	public static <T> CimMessage create(User user,
	                                    String functionID,
	                                    T requestBody) {
		Object jsonObject = JSONObject.toJSON(requestBody);
		CimMessage cimMessage = new CimMessage();
		cimMessage.requestBody = jsonObject;
		cimMessage.functionID = functionID;
		cimMessage.user = user;
		cimMessage.sendTime = System.currentTimeMillis();
		return cimMessage;
	}
}
