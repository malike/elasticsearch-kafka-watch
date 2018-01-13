package st.malike.elasticsearch.kafka.watch;

import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.rest.*;
import st.malike.elasticsearch.kafka.watch.config.PluginConfig;
import st.malike.elasticsearch.kafka.watch.listener.DeleteWatcherListener;
import st.malike.elasticsearch.kafka.watch.util.Enums;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.rest.RestRequest.Method.POST;

/**
 * @author malike_st
 */
public class RemoveWatcherRestAction extends BaseRestHandler {

    private static Logger log = Logger.getLogger(RemoveWatcherRestAction.class);
    private static PluginConfig pluginConfig;

    @Inject
    public RemoveWatcherRestAction(Settings settings, RestController controller) {
        super(settings);
        pluginConfig = new PluginConfig();
        controller.registerHandler(POST, "/_removekafkawatch", this);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) throws IOException {
        JSONResponse message = new JSONResponse();
        String id = null;
        if (restRequest.content().length() > 0) {
            Map<String, Object> map = XContentHelper.convertToMap(restRequest.content(), false, null).v2();
            if (!map.isEmpty()) {
                if (map.containsKey("id")) {
                    id = (String) map.get("id");
                }
            } else {
                return channel -> {
                    message.setStatus(false);
                    message.setCount(0L);
                    message.setData("Required Watcher ID not found");
                    message.setMessage(Enums.JSONResponseMessage.MISSING_PARAM.toString());
                    XContentBuilder builder = channel.newBuilder();
                    builder.startObject();
                    message.toXContent(builder, restRequest);
                    builder.endObject();
                    channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
                };
            }
        }
        DeleteRequestBuilder prepareDelete = client.prepareDelete(pluginConfig.getKafkaWatchElasticsearchIndex(),
                pluginConfig.getKafkaWatchElasticsearchType(), id);
        return channel -> prepareDelete.execute(new DeleteWatcherListener(channel, restRequest));
    }
}
