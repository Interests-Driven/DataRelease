package com.fa.cim.common.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * description:
 * <p>ListenableController .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/25        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/25 18:30
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@RestController
public @interface ListenableController {

	@AliasFor(annotation = RestController.class)
	String value() default "";

	String transactionID ();

}
