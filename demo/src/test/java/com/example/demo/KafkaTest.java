package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author lxh
 */
public class KafkaTest {

    private final static Logger log = LoggerFactory.getLogger(KafkaTest.class);

    final Properties props = new Properties();

    @BeforeEach
    public void configProps() {
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 300);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    }

    @Test
    public void sendTest() throws JsonProcessingException {
        final String topicName = "xhliu";
        ObjectMapper mapper = new ObjectMapper();
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            for (int i = 0; i < 5; i++) {
                Message message = new Message(String.valueOf(i), "Message-" + i);
                ProducerRecord<String, String> record = new ProducerRecord<>(topicName, message.getIndex(),
                        mapper.writeValueAsString(message));
                Future<RecordMetadata> future = producer.send(record);
                RecordMetadata metadata = future.get();
                log.info("[topic]={}, [partition]={}, [offset]={}", metadata.topic(),
                        metadata.partition(), metadata.offset());
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    static class Message {
        private final String index;

        private final String des;

        Message(String index, String des) {
            this.index = index;
            this.des = des;
        }

        public String getIndex() {
            return index;
        }

        public String getDes() {
            return des;
        }
    }
}
