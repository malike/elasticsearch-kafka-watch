package st.malike.elasticsearch.kafka.watch.config;

import org.elasticsearch.common.settings.Settings;

/**
 * @autor malike_st
 */
public class PluginConfig {

    private static final String KAFKA_WATCH_ELASTICSEARCH_INDEX = "kafka.watch.elasticsearch.index";
    private static final String KAFKA_WATCH_ELASTICSEARCH_TYPE = "kafka.watch.elasticsearch.type";
    private static final String KAFKA_WATCH_BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String KAFKA_WATCH_TOPIC = "kafka.watch.topic";
    private static final String KAFKA_WATCH_DISABLE = "kafka.watch.disable";
    private static final String REPORT_ENGINE_ENDPOINT = "report.engine.endpoint";
    private static final String REPORT_ENGINE_DISABLE = "report.engine.disable";


    public PluginConfig(Settings settings) {
    }

    public static String getKafkaWatchElasticsearchIndex() {
        return KAFKA_WATCH_ELASTICSEARCH_INDEX;
    }

    public static String getKafkaWatchElasticsearchType() {
        return KAFKA_WATCH_ELASTICSEARCH_TYPE;
    }

    public static String getKafkaWatchBootstrapServers() {
        return KAFKA_WATCH_BOOTSTRAP_SERVERS;
    }

    public static String getKafkaWatchTopic() {
        return KAFKA_WATCH_TOPIC;
    }

    public static Boolean getKafkaWatchDisable() {
        return KAFKA_WATCH_DISABLE.toLowerCase().equals("false");
    }

    public static Boolean getReportEngineDisable() {
        return REPORT_ENGINE_DISABLE.toLowerCase().equals("false");
    }

    public static String getReportEngineEndpoint() {
        return REPORT_ENGINE_ENDPOINT;
    }


}
