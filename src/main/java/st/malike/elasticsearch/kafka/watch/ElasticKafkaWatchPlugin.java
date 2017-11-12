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
import st.malike.elasticsearch.kafka.watch.listener.DocumentWatcherListener;
import st.malike.elasticsearch.kafka.watch.listener.IndexWatcherListener;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author malike_st
 */
public class ElasticKafkaWatchPlugin extends Plugin implements ActionPlugin {

    private static final String KAFKA_WATCH_ELASTICSEARCH_INDEX = "kafka.watch.elasticsearch.index";
    private static final String KAFKA_WATCH_ELASTICSEARCH_TYPE = "kafka.watch.elasticsearch.type";
    private static final String KAFKA_WATCH_BINDERS = "kafka.watch.binders";
    private static final String KAFKA_WATCH_TOPIC = "kafka.watch.topic";
    private static final String KAFKA_WATCH_DISABLE = "kafka.watch.disable";
    private static final String REPORT_ENGINE_DISABLE = "report.engine.disable";

    public static String getKafkaWatchElasticsearchIndex() {
        return KAFKA_WATCH_ELASTICSEARCH_INDEX;
    }

    public static String getKafkaWatchElasticsearchType() {
        return KAFKA_WATCH_ELASTICSEARCH_TYPE;
    }

    public static String getKafkaWatchBinders() {
        return KAFKA_WATCH_BINDERS;
    }

    public static String getKafkaWatchTopic() {
        return KAFKA_WATCH_TOPIC;
    }

    public static Boolean getKafkaWatchDisable() {
        return KAFKA_WATCH_DISABLE.toLowerCase().equals("false");
    }

    public static String getReportEngineDisable() {
        return REPORT_ENGINE_DISABLE;
    }

    @Override
    public List<RestHandler> getRestHandlers(Settings settings,
                                             RestController restController, ClusterSettings clusterSettings,
                                             IndexScopedSettings indexScopedSettings,
                                             SettingsFilter settingsFilter,
                                             IndexNameExpressionResolver indexNameExpressionResolver,
                                             Supplier<DiscoveryNodes> nodesInCluster) {
        return Arrays.asList(new AddWatcherRestAction(settings, restController),
                new RemoveWatcherRestAction(settings, restController),
                new ViewWatchersRestAction(settings, restController),
                new SearchWatchersRestAction(settings, restController));
    }

    @Override
    public void onIndexModule(IndexModule indexModule) {
        indexModule.addIndexEventListener(new IndexWatcherListener());
        indexModule.addIndexOperationListener(new DocumentWatcherListener());
    }
}
