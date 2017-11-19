package st.malike.elasticsearch.kafka.watch.listener;

import org.apache.log4j.Logger;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.shard.IndexingOperationListener;
import org.elasticsearch.index.shard.ShardId;
import st.malike.elasticsearch.kafka.watch.ElasticKafkaWatchPlugin;
import st.malike.elasticsearch.kafka.watch.service.EventIndexOpsTriggerService;

/**
 * @author malike_st
 */
public class DocumentWatcherListener implements IndexingOperationListener {

    private static Logger log = Logger.getLogger(DocumentWatcherListener.class);
    EventIndexOpsTriggerService eventIndexOpsTriggerService = new EventIndexOpsTriggerService();

    @Override
    public void postIndex(ShardId shardId, Engine.Index index, Engine.IndexResult result) {
        if ((!shardId.getIndexName().equals(ElasticKafkaWatchPlugin.getKafkaWatchElasticsearchIndex())
                && (!ElasticKafkaWatchPlugin.getKafkaWatchDisable()))) {

            if (eventIndexOpsTriggerService.evaluateRuleForEvent(shardId.getIndexName(), index, result,null)) {
                log.info("New trigger : Document Created " + index.source().utf8ToString());
            } else {
                log.info("Trigger did not meet requirements to be pushed to Apache Kafka" + index.source().utf8ToString());
            }
        }
    }


    @Override
    public void postDelete(ShardId shardId, Engine.Delete delete, Engine.DeleteResult result) {
        if ((!shardId.getIndexName().equals(ElasticKafkaWatchPlugin.getKafkaWatchElasticsearchIndex())
                && (!ElasticKafkaWatchPlugin.getKafkaWatchDisable()))) {


            if (eventIndexOpsTriggerService.evaluateRuleForEvent(shardId.getIndexName(), delete, result,null)) {
                log.info("New trigger : Document deleted " + delete.id());
            } else {
                log.info("Trigger did not meet requirements to be pushed to Apache Kafka" + delete.id()
                        + " Index Name : " + shardId.getIndexName());
            }
        }
    }


}
