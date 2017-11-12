package st.malike.elasticsearch.kafka.watch.service;

import org.elasticsearch.index.engine.Engine;

/**
 * @author malike_st
 */
public class EventIndexOpsTriggerService {

    public boolean evaluateRule(String indexName, Engine.Index index, Engine.IndexResult indexResult) {
        return true;
    }

    public boolean evaluateRule(String indexName, Engine.Delete delete, Engine.DeleteResult deleteResult) {
        return true;
    }

}
