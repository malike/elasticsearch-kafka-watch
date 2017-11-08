package st.malike.elasticsearch.kafka.watch;

import org.elasticsearch.index.Index;
import org.elasticsearch.index.IndexService;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.shard.IndexEventListener;
import org.elasticsearch.indices.cluster.IndicesClusterStateService;

/**
 * malike_st.
 */
public class IndexWatcherListener implements IndexEventListener {

    @Override
    public void afterIndexCreated(IndexService indexService) {

    }

    @Override
    public void afterIndexRemoved(Index index, IndexSettings indexSettings,
                                  IndicesClusterStateService.AllocatedIndices.IndexRemovalReason reason) {

    }
}
