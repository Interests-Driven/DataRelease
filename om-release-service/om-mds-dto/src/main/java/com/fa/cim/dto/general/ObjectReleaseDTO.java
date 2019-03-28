package com.fa.cim.dto.general;

import com.alibaba.fastjson.JSONObject;
import com.fa.cim.common.support.User;
import com.fa.cim.release.common.abstractobj.AbstractMain;
import lombok.Data;

/**
 * description:
 * <p>ObjectReleaseDTO .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/26        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/26 09:31
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
public class ObjectReleaseDTO {

	private String actionType;
	private String releaseType;
	private String releaseId;
	private String main;
	private User user;

	/**
	 * parse the main String into a Java Main Object by the given class
	 * @param mClass the class returnType
	 * @param <M> the main class returnType
	 * @return M
	 */
	public <M extends AbstractMain> M getMainObject (Class<M> mClass) {
		return JSONObject.parseObject(this.main, mClass);
	}

}
