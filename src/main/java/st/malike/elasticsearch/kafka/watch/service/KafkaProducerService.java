package st.malike.elasticsearch.kafka.watch.service;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.elasticsearch.common.settings.Settings;
import st.malike.elasticsearch.kafka.watch.config.PluginConfig;
import st.malike.elasticsearch.kafka.watch.model.KafkaEvent;

import java.util.Properties;

/**
 * @autor malike_st
 */
public class KafkaProducerService {

    private static Logger log = Logger.getLogger(KafkaProducerService.class);
    private final PluginConfig pluginConfig;
    private final Settings settings;
    private Producer<String, String> producer;

    public KafkaProducerService(PluginConfig pluginConfig, Settings settings) {
        this.settings = settings;
        this.pluginConfig = new PluginConfig(settings);
        Properties props = new Properties();
        props.put("bootstrap.servers", pluginConfig.getKafkaWatchBootstrapServers());
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }


    public void send(KafkaEvent event) {
        producer.send(new ProducerRecord<>(
                pluginConfig.getKafkaWatchTopic(),
                new Gson().toJson(event)));
    }

}
