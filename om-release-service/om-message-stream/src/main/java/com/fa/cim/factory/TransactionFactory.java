package com.fa.cim.factory;

import com.fa.cim.common.annotations.ListenableController;
import com.fa.cim.common.utils.SpringContextUtil;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * description:
 * <p>TransactionFactory .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/25        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/25 18:58
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Component
public class TransactionFactory {

	private Map<String, Object> controllerBeanPool = null;

	/**
	 * description:
	 * <p><br/></p>
	 * change history:
	 * date             defect             person             comments
	 * ---------------------------------------------------------------------------------------------------------------------
	 *
	 * @param transactionID String
	 * @return Object Controller Bean
	 * @author Yuri
	 * @date 2019/3/25 19:32:26
	 */
	public Object getControllerBean (String transactionID) {
		if (null == controllerBeanPool) {
			this.controllerBeanPool = new ConcurrentHashMap<>(128);
			Map<String, Object> cimControllerBeans = SpringContextUtil.getApplicationContext().getBeansWithAnnotation(ListenableController.class);
			Set<String> keySet = cimControllerBeans.keySet();
			for (String key : keySet) {
				Object bean = cimControllerBeans.get(key);
				ListenableController listenableController = bean.getClass().getAnnotation(ListenableController.class);
				this.controllerBeanPool.put(listenableController.transactionID(), bean);
			}
		}
		return this.controllerBeanPool.get(transactionID);
	}

}
