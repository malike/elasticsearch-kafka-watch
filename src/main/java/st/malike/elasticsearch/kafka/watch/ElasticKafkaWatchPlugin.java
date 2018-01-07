package st.malike.elasticsearch.kafka.watch;

import org.apache.log4j.Logger;
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
import st.malike.elasticsearch.kafka.watch.config.PluginConfig;
import st.malike.elasticsearch.kafka.watch.listener.DocumentWatcherListener;
import st.malike.elasticsearch.kafka.watch.listener.IndexWatcherListener;
import st.malike.elasticsearch.kafka.watch.service.KafkaProducerService;
import st.malike.elasticsearch.kafka.watch.service.TimeTriggerService;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author malike_st
 */
public class ElasticKafkaWatchPlugin extends Plugin implements ActionPlugin {

    private static Logger log = Logger.getLogger(ElasticKafkaWatchPlugin.class);
    private final TimeTriggerService timeTriggerService;
    private final PluginConfig pluginConfig;
    private final KafkaProducerService kafkaProducerService;


    public ElasticKafkaWatchPlugin(Settings settings) {
        this.pluginConfig = new PluginConfig(settings);
        this.kafkaProducerService = new KafkaProducerService(this.pluginConfig,settings);
        this.timeTriggerService = new TimeTriggerService(pluginConfig,kafkaProducerService);
    }

    @Override
    public List<RestHandler> getRestHandlers(Settings settings,
                                             RestController restController, ClusterSettings clusterSettings,
                                             IndexScopedSettings indexScopedSettings,
                                             SettingsFilter settingsFilter,
                                             IndexNameExpressionResolver indexNameExpressionResolver,
                                             Supplier<DiscoveryNodes> nodesInCluster) {
        try {
            timeTriggerService.schedule();
        } catch (Exception e) {
            log.error("Error starting scheduler. Error " + e.getLocalizedMessage());
        }
        return Arrays.asList(new AddWatcherRestAction(settings, restController),
                new RemoveWatcherRestAction(settings, restController),
                new ViewWatchersRestAction(settings, restController),
                new SearchWatchersRestAction(settings, restController));
    }

    @Override
    public void onIndexModule(IndexModule indexModule) {
        indexModule.addIndexEventListener(new IndexWatcherListener());
        indexModule.addIndexOperationListener(new DocumentWatcherListener(pluginConfig,kafkaProducerService));
    }
}
