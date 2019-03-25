package com.fa.cim.release.dto;

import com.fa.cim.core.bo.pcdmg.CimCategory;
import com.fa.cim.core.bo.pcdmg.CimCode;
import com.fa.cim.core.bo.pcdmg.CodeManager;
import com.fa.cim.entity.CimCategoryDO;
import com.fa.cim.entity.CimCodeDO;
import com.fa.cim.release.common.annotations.ConvertToDO;
import com.fa.cim.release.common.annotations.DOAttribute;
import com.fa.cim.release.common.annotations.DOEntity;
import com.fa.cim.release.common.annotations.QueryBy;
import lombok.Data;

import java.util.List;

/**
 * description:
 * <p>CompanyDTO .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 14:59
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
@DOEntity(manager = CodeManager.class,
		type = CimCodeDO.class,
		identifiers = {"this.category", "this.companyId"})
@QueryBy(query = CimCategory.class,
		type = CimCodeDO.class,
		paramType = {CimCategoryDO.class, String.class},
		method = "findCodeNamed")
public class CompanyDTO extends AbstractDTO {

	@DOAttribute(toAttribute = "codeID")
	private String companyId;

	@DOAttribute(toAttribute = "description")
	private String desc;

	@ConvertToDO
	@QueryBy(query = CimCategory.class, type = CimCategoryDO.class)
	@DOAttribute(fromAttribute = "categoryID", toAttribute = "categoryID")
	@DOAttribute(fromAttribute = "id", toAttribute = "categoryObject")
	@DOAttribute(fromAttribute = "propertyID", toAttribute = "propertyObject")
	private String category = "Company";

	private List<RelationDTO> usergroups;
	private List<UdataDTO> udata;

}
