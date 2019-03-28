package com.fa.cim.dto.code;

import com.fa.cim.core.bo.pcdmg.CimMachineState;
import com.fa.cim.dto.Infos;
import com.fa.cim.dto.general.UdataDTO;
import com.fa.cim.entity.eqpstate.CimEquipmentStateDO;
import com.fa.cim.release.common.abstractobj.AbstractMain;
import com.fa.cim.release.common.annotations.DOAttribute;
import com.fa.cim.release.common.annotations.entity.AsInfoStruct;
import com.fa.cim.release.common.annotations.entity.DOEntity;
import com.fa.cim.release.common.annotations.entity.SetInfoBy;
import lombok.Data;

import java.util.List;

/**
 * description:
 * <p>EquipmentStateDTO .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/26        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/26 10:09
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
@DOEntity(type = CimEquipmentStateDO.class, query = CimMachineState.class, identifiers = "equipmentStateCode")
@SetInfoBy(cimBO = CimMachineState.class, method = "setEquipmentStateInfo", paramType = Infos.BrEquipmentStateInfo.class)
public class EquipmentStateDTO extends AbstractMain {

	@DOAttribute(toAttribute = "equipmentStateCode")
	private String equipmentStateCode;

	@DOAttribute(toAttribute = "equipmentStateName")
	private String equipmentStateName;

	@DOAttribute(toAttribute = "equipmentStateDescription")
	private String eqpStateDesc;

	@DOAttribute(toAttribute = "e10State", convertTo = DOAttribute.Type.OBJECT_IDENTIFIER)
	private String e10State;

	@DOAttribute(toAttribute = "equipmentAvailableFlag")
	private Boolean equipmentAvailableFlag;

	@DOAttribute(toAttribute = "conditionalAvailableFlag")
	private Boolean conditionalAvailableFlag;

	@DOAttribute(toAttribute = "manufacturingStateChangeableFlag")
	private Boolean manufacturingStateChangeableFlag;

	@DOAttribute(toAttribute = "changeFromOtherE10Flag")
	private Boolean changeFromOtherE10Flag;

	@DOAttribute(toAttribute = "changeToOtherE10Flag")
	private Boolean changeToOtherE10Flag;

	@DOAttribute(toAttribute = "availableSubLotTypes")
	private List<String> availableSubLotTypes;

	@DOAttribute(toAttribute = "convertingConditions")
	@AsInfoStruct(type = Infos.BrEquipmentStateConvertingConditionData.class)
	private List<EquipmentStateConversionDTO> convertingConditions;

	@DOAttribute(toAttribute = "nextTransitionStates", convertTo = DOAttribute.Type.OBJECT_IDENTIFIER)
	private List<String> nextTransitionStates;

	@DOAttribute(toAttribute = "userDataSets")
	@AsInfoStruct(type = Infos.UserDataSet.class)
	private List<UdataDTO> userDataSets;

}
