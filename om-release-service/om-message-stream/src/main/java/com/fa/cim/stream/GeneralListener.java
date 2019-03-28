package com.fa.cim.stream;

import com.fa.cim.common.support.Response;
import com.fa.cim.dto.CimMessage;
import com.fa.cim.dto.CimMessageWrapper;
import com.fa.cim.factory.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * description:
 * <p>GeneralListener .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/25        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/25 18:56
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
//@EnableKafka
@Component
public class GeneralListener {

	@Autowired
	private TransactionFactory transactionFactory;

//	@KafkaListener(topics = "OM-REQUEST")
//	@SendTo
	public CimMessageWrapper listen (CimMessageWrapper inbound) {
		CimMessage cimMessage = inbound.getRequest();
		Object controllerBean = this.transactionFactory.getControllerBean(cimMessage.getFunctionID());
		Method method = controllerBean.getClass().getMethods()[0];
		Object requestBody = method.getParameterTypes()[0].cast(cimMessage.getRequestBody());
		Response response = (Response) ReflectionUtils.invokeMethod(method, controllerBean, requestBody);
		return CimMessageWrapper.create(response);
	}

}
