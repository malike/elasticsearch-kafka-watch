package st.malike.elasticsearch.kafka.watch.service;

import org.elasticsearch.index.engine.Engine;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;

import java.util.List;
import java.util.Map;

/**
 * @author malike_st
 */
public class EventIndexOpsTriggerService {

    private static KafkaWatchService kafkaWatchService = new KafkaWatchService();


    public boolean evaluateRuleForEvent(String indexName, Engine.Index index,
             Engine.IndexResult indexResult,KafkaWatch kafkaWatch) {
        if (kafkaWatch == null) {
            return false;
        }
        if(!kafkaWatch.getIndexName().equalsIgnoreCase(indexName)){
            return false;
        }
        if(kafkaWatch.getExpectedHit()==0){
            return true;
        }
        Map response = kafkaWatchService.executeWatchQuery(kafkaWatch.getIndexOpsQuery());
        int compared = ((Integer)response.get("hits")).compareTo(kafkaWatch.getExpectedHit());
        switch (kafkaWatch.getQuerySymbol()){
            case EQUAL_TO:
            return compared ==0;
            case GREATER_THAN_OR_EQUAL_TO:
                return compared >=0;
            case LESS_THAN_OR_EQUAL_TO:
                return compared <=0;
        }


        return false;
    }

    public boolean evaluateRuleForEvent(String indexName, Engine.Delete delete,
              Engine.DeleteResult deleteResult,KafkaWatch kafkaWatch) {
        return true;
    }




}
