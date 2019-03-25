package com.fa.cim.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * <p>User .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/19        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/19 21:39
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
public class User {

	private String password = "1234";
	private String username = "ABCD";
	private List<String> privileges = new ArrayList<>();
}
