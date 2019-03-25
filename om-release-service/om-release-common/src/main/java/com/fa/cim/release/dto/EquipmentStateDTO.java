package com.fa.cim.release.dto;

import com.fa.cim.core.bo.pcdmg.CimMachineState;
import com.fa.cim.core.bo.pcdmg.CodeManager;
import com.fa.cim.core.bo.pprmg.CimLotType;
import com.fa.cim.entity.CimE10StateDO;
import com.fa.cim.entity.eqpstate.CimEquipmentStateConversionDO;
import com.fa.cim.entity.eqpstate.CimEquipmentStateDO;
import com.fa.cim.entity.eqpstate.CimEquipmentStateNextTransitionDO;
import com.fa.cim.entity.eqpstate.CimEquipmentStateSltDO;
import com.fa.cim.entity.lottype.CimLotTypeSubLotTypeDO;
import com.fa.cim.release.common.annotations.*;
import lombok.Data;

import java.util.List;

/**
 * description:
 * <p>EquipmentStateDTO .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 15:23
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
@DOEntity(type = CimEquipmentStateDO.class,
		manager = CodeManager.class,
		identifiers = {"this.eqpState"})
@QueryBy(query = CimMachineState.class,
		type = CimEquipmentStateDO.class)
public class EquipmentStateDTO extends AbstractDTO {

	@DOAttribute(toAttribute = "equipmentStateID")
	private String eqpState;

	@DOAttribute(toAttribute = "equipmentStateName")
	private String name;

	@DOAttribute(toAttribute = "description")
	private String eqpStateDesc;

	@ConvertToDO
	@QueryBy(query = CodeManager.class,
			type = CimE10StateDO.class,
			method = "findE10StateNamed", paramType = String.class)
	@DOAttribute(fromAttribute = "id", toAttribute = "e10StateObj")
	@DOAttribute(fromAttribute = "stateId", toAttribute = "e10StateId")
	private String e10State;

	@DOAttribute(convertTo = DOAttribute.Type.BOOLEAN,
			toAttribute = "availableFlag")
	private Short eqpAvailableFlg;

	@DOAttribute(convertTo = DOAttribute.Type.BOOLEAN,
			toAttribute = "condtnAvailableFlag")
	private Short condAvailableFlg;

	@DOAttribute(convertTo = DOAttribute.Type.BOOLEAN,
			toAttribute = "manualStateChangeFlag")
	private Short manufacturingChFlg;

	@DOAttribute(convertTo = DOAttribute.Type.BOOLEAN,
			toAttribute = "fromOtherFlag")
	private Short changeFromFlg;

	@DOAttribute(convertTo = DOAttribute.Type.BOOLEAN,
			toAttribute = "toOtherFlag")
	private Short changeToFlg;

	@DOChild(manager = CodeManager.class,
			child = CimEquipmentStateSltDO.class,
			identifiers = {"attribute.refId"})
	@QueryBy(query = CimLotType.class,
			type = CimLotTypeSubLotTypeDO.class)
	@DOAttribute(fromAttribute = "subLotType", toAttribute = "subLotType")
	@DOAttribute(fromAttribute = "main.id", toAttribute = "refKey")
	private List<RelationDTO> subLotTypes;

	@DOChild(manager = CodeManager.class,
			child = CimEquipmentStateNextTransitionDO.class,
			identifiers = {"attribute.refId"})
	@QueryBy(query = CimMachineState.class,
			type = CimEquipmentStateDO.class)
	@DOAttribute(fromAttribute = "equipmentStateID", toAttribute = "nextEqpStateId")
	@DOAttribute(fromAttribute = "main.id", toAttribute = "eqpStateKey")
	@DOAttribute(fromAttribute = "id", toAttribute = "nextEqpStateObj")
	private List<RelationDTO> eqstNxt;

	@DOChild(manager = CodeManager.class,
			child = CimEquipmentStateConversionDO.class)
	@DOAttribute(fromAttribute = "main.id", toAttribute = "refKey")
	private List<EquipmentStateConversionDTO> eqstCv;

	// @TODO - wait for udata development in om
	private List<UdataDTO> userData;

}
