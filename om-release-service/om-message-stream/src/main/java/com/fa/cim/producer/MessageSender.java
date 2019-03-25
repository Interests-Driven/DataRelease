package com.fa.cim.producer;

import com.alibaba.fastjson.JSONObject;
import com.fa.cim.common.exception.ServiceException;
import com.fa.cim.config.MessageConfig;
import com.fa.cim.factory.MessageTemplateFactory;
import com.fa.cim.pojo.CimChannel;
import com.fa.cim.pojo.CimSyncChannel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import java.time.Instant;
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
	private ReplyingKafkaTemplate<String, String, String> template;

	@Autowired
	private MessageConfig config;

	/**
	 * Send the message and regardless the response;
	 * @param channel CimChannel
	 * @param message M
	 * @param <M> messageType
	 */
	public <M> void send (CimChannel channel, M message) {
		ProducerRecord<String, String> producerRecord = this.createProducerRecord(channel, message);
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
	public <M, R> R call (CimSyncChannel<R> channel, M message) {

		ProducerRecord<String, String> producerRecord = this.createProducerRecord(channel, message);
		producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, channel.getReplyTopic().getBytes()));
		R reply = null;
		try {
			RequestReplyFuture<String, String, String> requestFuture = template.sendAndReceive(producerRecord);
			String replyStr = requestFuture.get(config.getReplyTimeoutMs(), TimeUnit.MILLISECONDS).value();
			reply = JSONObject.parseObject(replyStr, channel.getReplyMessageClass());
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
	private <M> ProducerRecord<String, String> createProducerRecord (CimChannel channel, M message) {
		Object jsonObject = JSONObject.toJSON(message);
		String outMessage = jsonObject.toString();
		log.info("The message is " + outMessage);
		Integer partition = channel.getPartition();
		return null == partition ?
				new ProducerRecord<>(channel.getRequestTopic(), outMessage)
				: new ProducerRecord<>(channel.getRequestTopic(), partition, null, outMessage);
	}

}
