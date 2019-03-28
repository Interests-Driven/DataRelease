package com.fa.cim.dto.code;

import com.fa.cim.release.common.abstractobj.AbstractChild;
import com.fa.cim.release.common.annotations.DOAttribute;
import lombok.Data;

/**
 * description:
 * <p>EquipmentStateConversionDTO .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/26        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/26 11:30
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
public class EquipmentStateConversionDTO extends AbstractChild {

	@DOAttribute(toAttribute = "checkSequence")
	private String checkSequence;

	@DOAttribute(toAttribute = "convertingLogic")
	private String convertingLogic;

	@DOAttribute(toAttribute = "attributeValue")
	private String attributeValue;

	@DOAttribute(toAttribute = "toEquipmentStateCode", convertTo = DOAttribute.Type.OBJECT_IDENTIFIER)
	private String toEquipmentStateCode;

}
