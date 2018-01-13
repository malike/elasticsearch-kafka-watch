package st.malike.elasticsearch.kafka.watch.config;

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

    public PluginConfig() {

    }

    public  String getKafkaWatchElasticsearchIndex() {
        
        return KAFKA_WATCH_ELASTICSEARCH_INDEX;
    }

    public  String getKafkaWatchElasticsearchType() {
        return KAFKA_WATCH_ELASTICSEARCH_TYPE;
    }

    public  String getKafkaWatchBootstrapServers() {
        return KAFKA_WATCH_BOOTSTRAP_SERVERS;
    }

    public  String getKafkaWatchTopic() {
        return KAFKA_WATCH_TOPIC;
    }

    public  Boolean getKafkaWatchDisable() {
        return KAFKA_WATCH_DISABLE.toLowerCase().equals("false");
    }

    public  Boolean getReportEngineDisable() {
        return REPORT_ENGINE_DISABLE.toLowerCase().equals("false");
    }

    public  String getReportEngineEndpoint() {

        return REPORT_ENGINE_ENDPOINT;
    }


}
