package com.fa.cim.producer;

import com.fa.cim.common.exception.ServiceException;
import com.fa.cim.common.support.User;
import com.fa.cim.config.MessageConfig;
import com.fa.cim.dto.CimMessage;
import com.fa.cim.dto.CimMessageWrapper;
import com.fa.cim.pojo.CimChannel;
import com.fa.cim.pojo.CimSyncChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * description:
 * <p>MessageSender .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/2/13        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/2/13 15:38
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Data
@Component
@Slf4j
public class MessageSender {

	@Autowired
	private ReplyingKafkaTemplate<String, CimMessageWrapper, CimMessageWrapper> template;

	@Autowired
	private MessageConfig config;

	/**
	 * Send the message and regardless the response;
	 * @param channel CimChannel
	 * @param message M
	 * @param <M> messageType
	 */
	public <M> void send (CimChannel channel,
	                      String transactionID,
	                      User user,
	                      M message) {
		ProducerRecord<String, CimMessageWrapper> producerRecord = this.createProducerRecord(channel,transactionID, user, message);
		template.send(producerRecord)
				.addCallback(result -> log.debug(String.format("Send Message Successfully [ %s ]", channel.toString())),
						ex -> {
							throw new ServiceException(String.format("Message Sending failed - Channel [ %s ]", channel.toString()));
						});
	}

	/**
	 * Send the message and wait for the response from the receiver;
	 * @param channel CimSyncChannel
	 * @param message M
	 * @param <M> message Type
	 * @param <R> response Type
	 * @return R
	 */
	public <M, R> R call (CimSyncChannel<R> channel,
	                      String transactionID,
	                      User user,
	                      M message) {

		ProducerRecord<String, CimMessageWrapper> producerRecord = this.createProducerRecord(channel, transactionID, user, message);
		producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, channel.getReplyTopic().getBytes()));
		Integer partition =  channel.getPartition();
		if (null != partition) {
			producerRecord.headers().add(new RecordHeader(KafkaHeaders.RECEIVED_PARTITION_ID, partition.toString().getBytes()));
			producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_PARTITION, partition.toString().getBytes()));
		}
		R reply = null;
		try {
			RequestReplyFuture<String, CimMessageWrapper, CimMessageWrapper> requestFuture = template.sendAndReceive(producerRecord);
			Object replyObj = requestFuture.get(config.getReplyTimeoutMs(), TimeUnit.MILLISECONDS).value().getResponse().getBody();
			reply = channel.getReplyMessageClass().cast(replyObj);
		} catch (TimeoutException  e) {
			throw new ServiceException(String.format("Sender Calling Timeout by [ %d ms ] - Channel [ %s ]", config.getReplyTimeoutMs(), channel.toString()));
		} catch (InterruptedException e) {
			throw new ServiceException("Sender Calling Interrupted Unexpected");
		} catch (ExecutionException e){
			throw new ServiceException("Sender Calling Retrieve Result Failed");
		}
		return reply;
	}

	/**
	 * create the producerRecord based on the CimChannel and the message
	 * @param channel CimChannel
	 * @param message M
	 * @return ProducerRecord
	 */
	private <M> ProducerRecord<String, CimMessageWrapper> createProducerRecord (CimChannel channel,
	                                                                            String transactionID,
	                                                                            User user,
	                                                                            M message) {
		CimMessage cimMessage = CimMessage.create(user, transactionID, message);
		CimMessageWrapper cimMessageWrapper = CimMessageWrapper.create(cimMessage);
		Integer partition = channel.getPartition();
		return null == partition ?
				new ProducerRecord<>(channel.getRequestTopic(), cimMessageWrapper)
				: new ProducerRecord<>(channel.getRequestTopic(), partition, null, cimMessageWrapper);
	}

}
