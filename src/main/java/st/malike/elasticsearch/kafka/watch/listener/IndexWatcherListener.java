package st.malike.elasticsearch.kafka.watch.listener;

import org.apache.log4j.Logger;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.IndexService;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.shard.IndexEventListener;
import org.elasticsearch.indices.cluster.IndicesClusterStateService;

/**
 * @author malike_st
 */
public class IndexWatcherListener implements IndexEventListener {

    private static Logger log = Logger.getLogger(DocumentWatcherListener.class);

    @Override
    public void afterIndexCreated(IndexService indexService) {
        log.info("New trigger : Index Created " + indexService.getMetaData().getIndex().getName());
    }

    @Override
    public void afterIndexRemoved(Index index, IndexSettings indexSettings,
                                  IndicesClusterStateService.AllocatedIndices.IndexRemovalReason reason) {
        log.info("New trigger : Index deleted " + index.getName());
    }
}
