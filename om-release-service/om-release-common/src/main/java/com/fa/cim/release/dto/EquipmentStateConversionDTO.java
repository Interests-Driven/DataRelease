package com.fa.cim.release.dto;

import com.fa.cim.core.bo.pcdmg.CimMachineState;
import com.fa.cim.core.bo.pcdmg.CodeManager;
import com.fa.cim.entity.eqpstate.CimEquipmentStateConversionDO;
import com.fa.cim.entity.eqpstate.CimEquipmentStateDO;
import com.fa.cim.factory.core.CodeBOFactory;
import com.fa.cim.release.common.annotations.DOAttribute;
import com.fa.cim.release.common.annotations.DOEntity;
import com.fa.cim.release.common.annotations.QueryBy;
import lombok.Data;

/**
 * description:
 * <p>EquipmentStateConversionDTO .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 15:27
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
@DOEntity(manager = CodeManager.class,
		type = CimEquipmentStateConversionDO.class,
		identifiers = {"this.refIdent", "this.toEqStIdent"})
@QueryBy(query = CimMachineState.class,
		type = CimEquipmentStateDO.class)
public class EquipmentStateConversionDTO extends AbstractDTO {

	@DOAttribute(toAttribute = "sequenceNumber")
	private Integer dseqno;

	@DOAttribute(toAttribute = "checkSequence")
	private String convChkSeq;

	@DOAttribute(toAttribute = "convertLogic")
	private String convLogic;

	@DOAttribute(toAttribute = "attributeValue")
	private String attrbtValue;

	@DOAttribute(toAttribute = "eqpStateId")
	private String toEqStIdent;

}
