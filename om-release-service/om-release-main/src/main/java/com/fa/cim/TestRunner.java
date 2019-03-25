package com.fa.cim;

import antlr.debug.MessageListener;
import com.fa.cim.core.bo.pcdmg.CimCategory;
import com.fa.cim.core.bo.pcdmg.CodeManager;
import com.fa.cim.core.bo.pprmg.CimLotType;
import com.fa.cim.core.bo.pprmg.ProductManager;
import com.fa.cim.dto.Result;
import com.fa.cim.dto.User;
import com.fa.cim.entity.CimCategoryDO;
import com.fa.cim.entity.CimCodeDO;
import com.fa.cim.entity.CimE10StateDO;
import com.fa.cim.entity.lottype.CimLotTypeDO;
import com.fa.cim.entity.lottype.CimLotTypeSubLotTypeDO;
import com.fa.cim.entity.persongroup.CimPersonGroupDO;
import com.fa.cim.factory.core.CodeBOFactory;
import com.fa.cim.factory.core.ProductBOFactory;
import com.fa.cim.pojo.CimSyncChannel;
import com.fa.cim.producer.MessageSender;
import com.fa.cim.release.dto.CompanyDTO;
import com.fa.cim.release.dto.EquipmentStateDTO;
import com.fa.cim.release.dto.RelationDTO;
import com.fa.cim.release.service.api.basic.DefaultBOReleaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * <p>TestRunner .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/17        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/17 17:43
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Component
@Slf4j
public class TestRunner implements CommandLineRunner {

	private short TRUE = 1;
	private short FALSE = 0;

	@Autowired
	private MessageSender messageSender;

	@Override
	public void run(String... args) throws Exception {

		CimSyncChannel<Result> cimSyncChannel = new CimSyncChannel<>("RELEASE-REQ-TEST-05", "RELEASE-RSP-TEST-05", Result.class);

//		messageSender.send(cimSyncChannel, new User());

		Result result = messageSender.call(cimSyncChannel, new User());
		log.info("The result received is ----------> " + result.isSuccessFlag());
	}
}
