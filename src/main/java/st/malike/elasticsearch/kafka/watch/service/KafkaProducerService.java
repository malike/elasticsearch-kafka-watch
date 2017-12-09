package st.malike.elasticsearch.kafka.watch.service;

import org.apache.log4j.Logger;
import st.malike.elasticsearch.kafka.watch.ElasticKafkaWatchPlugin;
import st.malike.elasticsearch.kafka.watch.model.KafkaEvent;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**
 * @autor malike_st
 */
public class KafkaProducerService {

    private static Logger log = Logger.getLogger(KafkaProducerService.class);
    private static Producer<String,KafkaEvent> producer;


    public void startKafka(){
        Properties props = new Properties();
        props.put("bootstrap.servers", ElasticKafkaWatchPlugin.getKafkaWatchBootstrapServers());
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
         producer = new KafkaProducer<>(props);
    }

    public void send(KafkaEvent event) {
        throw new UnsupportedOperationException();
    }

}
