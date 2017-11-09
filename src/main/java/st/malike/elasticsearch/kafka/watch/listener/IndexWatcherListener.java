package st.malike.elasticsearch.kafka.watch.listener;

import org.elasticsearch.index.Index;
import org.elasticsearch.index.IndexService;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.shard.IndexEventListener;
import org.elasticsearch.indices.cluster.IndicesClusterStateService;

/**
 * @author malike_st
 */
public class IndexWatcherListener implements IndexEventListener {

    @Override
    public void afterIndexCreated(IndexService indexService) {
        System.out.println("New trigger : Index Created " + indexService.getMetaData().getIndex().getName());
    }

    @Override
    public void afterIndexRemoved(Index index, IndexSettings indexSettings,
                                  IndicesClusterStateService.AllocatedIndices.IndexRemovalReason reason) {
        System.out.println("New trigger : Index deleted " + index.getName());
    }
}
