package com.fa.cim.dto;

import com.fa.cim.common.support.Response;
import lombok.Data;

/**
 * description:
 * The standard communication DTO between services which can be used for both request message and get the reply message
 * <p>CimMessageWrapper .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/25        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/25 17:53
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
public class CimMessageWrapper {

	private CimMessage request;
	private Response response;

	/**
	 * description:
	 * <p><br/></p>
	 * change history:
	 * date             defect             person             comments
	 * ---------------------------------------------------------------------------------------------------------------------
	 *
	 * @param cimMessage CimMessage
	 * @return CimMessageWrapper with request Object
	 * @author Yuri
	 * @date 2019/3/25 18:11:40
	 */
	public static CimMessageWrapper create(CimMessage cimMessage) {
		CimMessageWrapper dto = new CimMessageWrapper();
		dto.request = cimMessage;
		return dto;
	}

	/**
	 * description:
	 * <p><br/></p>
	 * change history:
	 * date             defect             person             comments
	 * ---------------------------------------------------------------------------------------------------------------------
	 *
	 * @param response Response
	 * @return CimMessageWrapper with response Object
	 * @author Yuri
	 * @date 2019/3/25 18:12:16
	 */
	public static CimMessageWrapper create(Response response) {
		CimMessageWrapper dto = new CimMessageWrapper();
		dto.response = response;
		return dto;
	}

}
