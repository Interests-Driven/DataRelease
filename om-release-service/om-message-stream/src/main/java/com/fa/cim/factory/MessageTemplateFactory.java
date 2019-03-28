package com.fa.cim.factory;

import com.fa.cim.config.MessageConfig;
import com.fa.cim.dto.CimMessageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * description:
 * <p>SyncRequestTemplateFacotry .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/2/11        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/2/11 10:52
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@EnableKafka
@Slf4j
@Configuration
public class MessageTemplateFactory {

	@Autowired
	private MessageConfig config;

	@Bean
	public ReplyingKafkaTemplate<String, CimMessageWrapper, CimMessageWrapper> replyKafkaTemplate(ProducerFactory<String, CimMessageWrapper> pf,
	                                                                                              KafkaMessageListenerContainer<String, CimMessageWrapper> container) {
		ReplyingKafkaTemplate<String, CimMessageWrapper, CimMessageWrapper> template = new ReplyingKafkaTemplate<>(pf, container);
		template.setReplyTimeout(config.getReplyTimeoutMs());
		return template;
	}
	@Bean
	public KafkaMessageListenerContainer<String, CimMessageWrapper> replyContainer(ConsumerFactory<String, CimMessageWrapper> cf) {
		ContainerProperties containerProperties = new ContainerProperties(config.getReplyTopics());
		return new KafkaMessageListenerContainer<>(cf, containerProperties);
	}
	@Bean
	public ProducerFactory<String, CimMessageWrapper> producerFactory() {
		return new DefaultKafkaProducerFactory<>(this.config.producerConfigs());
	}

	@Bean
	public ConsumerFactory<String, CimMessageWrapper> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(this.config.replyConsumerConfigs(),new StringDeserializer(), new JsonDeserializer<>(CimMessageWrapper.class));
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CimMessageWrapper>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, CimMessageWrapper> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setReplyTemplate(kafkaTemplate());
		return factory;
	}

	@Bean
	public KafkaTemplate<String, CimMessageWrapper> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
