package com.fa.cim.pojo;

import lombok.Data;

/**
 * description:
 * <p>CimChannel .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/2/12        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/2/12 10:10
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
public class CimChannel {

	protected String requestTopic;

	protected Integer partition;

	public CimChannel(String requestTopic) {
		this.requestTopic = requestTopic;
	}

	public CimChannel(String requestTopic, Integer partition) {
		this.requestTopic = requestTopic;
		this.partition = partition;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		CimChannel that = (CimChannel) o;

		if (!requestTopic.equals(that.requestTopic)) return false;
		return partition != null ? partition.equals(that.partition) : that.partition == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + requestTopic.hashCode();
		result = 31 * result + (partition != null ? partition.hashCode() : 0);
		return result;
	}
}
