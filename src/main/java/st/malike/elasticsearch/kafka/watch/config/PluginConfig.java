package st.malike.elasticsearch.kafka.watch.config;

import org.elasticsearch.common.settings.Settings;

/**
 * @autor malike_st
 */
public class PluginConfig {

    private  final String KAFKA_WATCH_ELASTICSEARCH_INDEX = "kafka.watch.elasticsearch.index";
    private  final String KAFKA_WATCH_ELASTICSEARCH_TYPE = "kafka.watch.elasticsearch.type";
    private  final String KAFKA_WATCH_BOOTSTRAP_SERVERS = "localhost:9092";
    private  final String KAFKA_WATCH_TOPIC = "kafka.watch.topic";
    private  final String KAFKA_WATCH_DISABLE = "kafka.watch.disable";
    private  final String REPORT_ENGINE_ENDPOINT = "report.engine.endpoint";
    private  final String REPORT_ENGINE_DISABLE = "report.engine.disable";
    private final Settings settings;

    public PluginConfig(Settings settings) {
        this.settings = settings;
    }

    public  String getKafkaWatchElasticsearchIndex() {
        
        return this.settings.get(KAFKA_WATCH_ELASTICSEARCH_INDEX);
    }

    public  String getKafkaWatchElasticsearchType() {
        return this.settings.get(KAFKA_WATCH_ELASTICSEARCH_TYPE);
    }

    public  String getKafkaWatchBootstrapServers() {
        return this.settings.get(KAFKA_WATCH_BOOTSTRAP_SERVERS);
    }

    public  String getKafkaWatchTopic() {
        return this.settings.get(KAFKA_WATCH_TOPIC);
    }

    public  Boolean getKafkaWatchDisable() {
        return this.settings.get(KAFKA_WATCH_DISABLE).toLowerCase().equals("false");
    }

    public  Boolean getReportEngineDisable() {
        return this.settings.get(REPORT_ENGINE_DISABLE).toLowerCase().equals("false");
    }

    public  String getReportEngineEndpoint() {

        return this.settings.get(REPORT_ENGINE_ENDPOINT);
    }


}
