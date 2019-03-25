package com.fa.cim.release.common.annotations;

import com.fa.cim.release.factory.BOReleaseServiceFactory;
import com.fa.cim.release.service.api.base.BOReleaseService;
import com.fa.cim.release.service.api.basic.DefaultBOReleaseService;

import java.lang.annotation.*;

/**
 * description:
 * <p>ReleaseBy .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 14:01
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ReleaseBy {

	/**
	 * the class type of the release service with default value of "DefaultBOReleaseServiceImpl"
	 * @return ? extends BOReleaseService
	 */
	Class<? extends BOReleaseService> type () default DefaultBOReleaseService.class;

	/**
	 * the name of the method to release the object with default value of "release"
	 * @return string
	 */
	String release () default BOReleaseService.DEFAULT_RELEASE_NAME;
}
