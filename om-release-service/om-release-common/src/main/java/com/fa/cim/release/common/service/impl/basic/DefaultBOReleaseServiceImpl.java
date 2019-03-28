package com.fa.cim.release.common.service.impl.basic;

import com.fa.cim.common.exception.ServiceException;
import com.fa.cim.common.utils.StringUtils;
import com.fa.cim.entitysuper.BaseEntity;
import com.fa.cim.lock.OmZookeeperLock;
import com.fa.cim.release.common.abstractobj.AbstractMain;
import com.fa.cim.release.common.annotations.entity.SetInfoBy;
import com.fa.cim.release.common.method.MainProcessMethod;
import com.fa.cim.release.common.service.api.basic.DefaultBOReleaseService;
import com.fa.cim.support.SnowflakeIDWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

/**
 * description:
 * <p>DefaultBOReleaseServiceImpl .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 14:41
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Component
@Slf4j
public class DefaultBOReleaseServiceImpl implements DefaultBOReleaseService {

	@Autowired
	private MainProcessMethod processMethod;

	@Autowired
	private OmZookeeperLock locker;


	@Override
	@SuppressWarnings("unchecked")
	public <DTO extends AbstractMain> void release(DTO dto) {

		Class<DTO> dtoClass = (Class<DTO>) dto.getClass();
		BaseEntity mainObj = processMethod.getMainDataObject(dto);

		if (StringUtils.isEmpty(mainObj.getId())) {
			mainObj.setId(SnowflakeIDWorker.getInstance().generateId(mainObj.getClass()));
		}

		Lock lock = locker.obtain(mainObj);
		try {
			if (lock.tryLock()) {
				if (dtoClass.isAnnotationPresent(SetInfoBy.class)) {
					processMethod.releaseByInfoStruct(dto, mainObj);
				}
			}
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			ServiceException se = new ServiceException("System Error");
			se.setData(e);
			throw se;
		} finally {
			lock.unlock();
		}


	}

}
