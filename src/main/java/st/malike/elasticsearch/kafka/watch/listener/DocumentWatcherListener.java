package st.malike.elasticsearch.kafka.watch.listener;

import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.shard.IndexingOperationListener;
import org.elasticsearch.index.shard.ShardId;

/**
 * @author malike_st
 */
public class DocumentWatcherListener implements IndexingOperationListener {

    @Override
    public void postIndex(ShardId shardId, Engine.Index index, Engine.IndexResult result) {
        System.out.println("New trigger : Document Created " + index.source().utf8ToString());
    }


    @Override
    public void postDelete(ShardId shardId, Engine.Delete delete, Engine.DeleteResult result) {
        System.out.println("New trigger : Document deleted " + delete.id());
    }


}
