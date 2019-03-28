package com.fa.cim.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * description:
 * <p>CimSyncChannel .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/2/12        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/2/12 13:47
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Getter
@Setter
@ToString
public class CimSyncChannel<R> extends CimChannel{

	private String replyTopic;
	private Class<R> replyMessageClass;

	public CimSyncChannel(String requestTopic) {
		super(requestTopic);
	}

	public CimSyncChannel(String requestTopic, Integer partition) {
		super(requestTopic, partition);
	}

	public CimSyncChannel (CimChannel cimChannel, String replyTopic, Class<R> replyMessageClass) {
		super(cimChannel.getRequestTopic(), cimChannel.getPartition());
		this.replyTopic = replyTopic;
		this.replyMessageClass = replyMessageClass;
	}

	public CimSyncChannel(String requestTopic, String replyTopic, Class<R> replyMessageClass) {
		super(requestTopic);
		this.replyTopic = replyTopic;
		this.replyMessageClass = replyMessageClass;
	}

	public CimSyncChannel(String requestTopic, Integer partition, String replyTopic, Class<R> replyMessageClass) {
		super(requestTopic, partition);
		this.replyTopic = replyTopic;
		this.replyMessageClass = replyMessageClass;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		CimSyncChannel<?> that = (CimSyncChannel<?>) o;

		if (!replyTopic.equals(that.replyTopic)) return false;
		return replyMessageClass.equals(that.replyMessageClass);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + replyTopic.hashCode();
		result = 31 * result + replyMessageClass.hashCode();
		return result;
	}
}
