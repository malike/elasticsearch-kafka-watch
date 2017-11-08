package st.malike.elasticsearch.kafka.watch;

import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.shard.IndexingOperationListener;
import org.elasticsearch.index.shard.ShardId;

/**
 * @author malike_st
 */
public class DocumentWatcherListener implements IndexingOperationListener {

    @Override
    public void postDelete(ShardId shardId, Engine.Delete delete, Engine.DeleteResult result) {

    }

    @Override
    public void postIndex(ShardId shardId, Engine.Index index, Exception ex) {

    }
}
