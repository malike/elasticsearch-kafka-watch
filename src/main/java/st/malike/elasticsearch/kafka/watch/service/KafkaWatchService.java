package st.malike.elasticsearch.kafka.watch.service;

import org.apache.log4j.Logger;
import org.elasticsearch.search.SearchHits;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;

import java.util.List;

/**
 * @autor malike_st
 */
public class KafkaWatchService {

    private static Logger log = Logger.getLogger(KafkaWatchService.class);

    public SearchHits executeWatchQuery(String query) {
        return null;
    }

    public List<KafkaWatch> searchWatchByIndex(String index) {
        return null;
    }

    public List<KafkaWatch> findAllWatch() {
        return null;
    }

    public KafkaWatch findById(String key) {
        return null;
    }
}
