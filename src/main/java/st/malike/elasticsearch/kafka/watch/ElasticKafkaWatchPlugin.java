package st.malike.elasticsearch.kafka.watch;

import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.cluster.node.DiscoveryNodes;
import org.elasticsearch.common.settings.ClusterSettings;
import org.elasticsearch.common.settings.IndexScopedSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsFilter;
import org.elasticsearch.index.IndexModule;
import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestHandler;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author malike_st
 */
public class ElasticKafkaWatchPlugin extends Plugin implements ActionPlugin {

    private static final String KAFKA_WATCH_BINDERS = "kafka.watch.binders";
    private static final String KAFKA_WATCH_TOPIC = "kafka.watch.topic";
    private static final String KAFKA_WATCH_DISABLE = "kafka.watch.disable";

    @Override
    public List<RestHandler> getRestHandlers(Settings settings,
                                             RestController restController, ClusterSettings clusterSettings,
                                             IndexScopedSettings indexScopedSettings,
                                             SettingsFilter settingsFilter,
                                             IndexNameExpressionResolver indexNameExpressionResolver,
                                             Supplier<DiscoveryNodes> nodesInCluster) {
        return Arrays.asList(new AddWatcherRestAction(settings, restController),
                new RemoveWatcherRestAction(settings, restController),
                new ViewWatchersRestAction(settings, restController));
    }

    @Override
    public void onIndexModule(IndexModule indexModule) {
        indexModule.addIndexOperationListener(new DocumentWatcherListener());
        indexModule.addIndexEventListener(new IndexWatcherListener());
    }


}
