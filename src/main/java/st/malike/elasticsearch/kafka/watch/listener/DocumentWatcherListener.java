package st.malike.elasticsearch.kafka.watch.listener;

import org.apache.log4j.Logger;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.shard.IndexingOperationListener;
import org.elasticsearch.index.shard.ShardId;
import st.malike.elasticsearch.kafka.watch.ElasticKafkaWatchPlugin;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;
import st.malike.elasticsearch.kafka.watch.service.EventIndexOpsTriggerService;
import st.malike.elasticsearch.kafka.watch.service.KafkaEventGeneratorService;
import st.malike.elasticsearch.kafka.watch.service.KafkaProducerService;
import st.malike.elasticsearch.kafka.watch.service.KafkaWatchService;

import java.util.List;

/**
 * @author malike_st
 */
public class DocumentWatcherListener implements IndexingOperationListener {

    private static Logger log = Logger.getLogger(DocumentWatcherListener.class);
    EventIndexOpsTriggerService eventIndexOpsTriggerService = new EventIndexOpsTriggerService();
    KafkaWatchService kafkaWatchService = new KafkaWatchService();
    KafkaEventGeneratorService kafkaEventGeneratorService = new KafkaEventGeneratorService();
    KafkaProducerService kafkaProducerService = new KafkaProducerService();

    @Override
    public void postIndex(ShardId shardId, Engine.Index index, Engine.IndexResult result) {
        if ((!shardId.getIndexName().equals(ElasticKafkaWatchPlugin.getKafkaWatchElasticsearchIndex())
                && (!ElasticKafkaWatchPlugin.getKafkaWatchDisable()))) {

            List<KafkaWatch> kafkaWatchList = kafkaWatchService.searchWatchByIndex(shardId.getIndexName());
            if (kafkaWatchList != null && kafkaWatchList.isEmpty()) {
                for (KafkaWatch kafkaWatch : kafkaWatchList) {
                    if (eventIndexOpsTriggerService.evaluateRuleForEvent(shardId.getIndexName(), index, result, kafkaWatch)) {
                        kafkaProducerService.send(kafkaEventGeneratorService.generate(kafkaWatch));
                        log.info("New trigger : Document Created " + index.source().utf8ToString());
                    } else {
                        log.info("Trigger did not meet requirements to be pushed to Apache Kafka" + index.source().utf8ToString());
                    }
                }
            }
        }
    }


    @Override
    public void postDelete(ShardId shardId, Engine.Delete delete, Engine.DeleteResult result) {
        if ((!shardId.getIndexName().equals(ElasticKafkaWatchPlugin.getKafkaWatchElasticsearchIndex())
                && (!ElasticKafkaWatchPlugin.getKafkaWatchDisable()))) {

            List<KafkaWatch> kafkaWatchList = kafkaWatchService.searchWatchByIndex(shardId.getIndexName());
            if (kafkaWatchList != null && kafkaWatchList.isEmpty()) {
                for (KafkaWatch kafkaWatch : kafkaWatchList) {
                    if (eventIndexOpsTriggerService.evaluateRuleForEvent(shardId.getIndexName(), delete, result, kafkaWatch)) {
                        kafkaProducerService.send(kafkaEventGeneratorService.generate(kafkaWatch));
                        log.info("New trigger : Document deleted " + delete.id());
                    } else {
                        log.info("Trigger did not meet requirements to be pushed to Apache Kafka" + delete.id()
                                + " Index Name : " + shardId.getIndexName());
                    }
                }
            }
        }
    }


}
