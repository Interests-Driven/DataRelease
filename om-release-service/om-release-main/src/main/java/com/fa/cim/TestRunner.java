package com.fa.cim;

import com.fa.cim.dto.code.EquipmentStateConversionDTO;
import com.fa.cim.dto.code.EquipmentStateDTO;
import com.fa.cim.factory.core.ProductBOFactory;
import com.fa.cim.release.common.service.api.basic.DefaultBOReleaseService;
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

	@Autowired
	private DefaultBOReleaseService releaseService;

	@Autowired
	private ProductBOFactory productBOFactory;

	@Override
	public void run(String... args) throws Exception {

//		ProductManager manager = productBOFactory.getBOInstance(ProductBOFactory.CLASS_PRODUCT_MANAGER);
//
//		CimLotTypeDO lotTypeDO = new CimLotTypeDO();
//		lotTypeDO.setLotTypeID("PROD");
//		lotTypeDO.setLotType("PROD");
//		lotTypeDO.setLatestUsedNumber(1);
//		lotTypeDO.setDescription("Some Description");
//
//		lotTypeDO = (CimLotTypeDO) manager.save(lotTypeDO);
//
//		CimLotTypeSubLotTypeDO subLotTypeDO = new CimLotTypeSubLotTypeDO();
//		subLotTypeDO.setSubLotType("PROD-EN");
//		subLotTypeDO.setReferenceKey(lotTypeDO.getId());
//		subLotTypeDO.setLeadingChar("K");
//		subLotTypeDO.setDescription("Some Description");
//		subLotTypeDO.setDuration(100);
//		manager.save(subLotTypeDO);
//
//		subLotTypeDO.setSubLotType("PROD-QT");
//		subLotTypeDO.setId(null);
//		manager.save(subLotTypeDO);

		List<String> nextEqpst = new ArrayList<>(3);
		nextEqpst.add("ST");
		nextEqpst.add("EQ");

		List<String> sublotTypes = new ArrayList<>(2);
		sublotTypes.add("PROD-QT");
		sublotTypes.add("PROD-EN");

		List<EquipmentStateConversionDTO> list = new ArrayList<>(2);
		EquipmentStateConversionDTO con1 = new EquipmentStateConversionDTO();
		con1.setAttributeValue("123");
		con1.setCheckSequence("kkk");
		con1.setConvertingLogic("logic");
		con1.setToEquipmentStateCode("SI");
		list.add(con1);
		EquipmentStateConversionDTO con2 = new EquipmentStateConversionDTO();
		con2.setAttributeValue("123");
		con2.setCheckSequence("kkk");
		con2.setConvertingLogic("logic");
		con2.setToEquipmentStateCode("SO");
		list.add(con2);
		EquipmentStateDTO dto = new EquipmentStateDTO();
		dto.setE10State("ENG");
		dto.setEqpStateDesc("Some Description");
		dto.setAvailableSubLotTypes(sublotTypes);
		dto.setChangeFromOtherE10Flag(true);
		dto.setChangeToOtherE10Flag(true);
		dto.setConditionalAvailableFlag(true);
		dto.setManufacturingStateChangeableFlag(false);
		dto.setEquipmentAvailableFlag(true);
		dto.setEquipmentStateCode("STB");
		dto.setEquipmentStateName("DDK");
		dto.setNextTransitionStates(nextEqpst);
		dto.setConvertingConditions(list);
		releaseService.release(dto);
	}
}
