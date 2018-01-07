package st.malike.elasticsearch.kafka.watch;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.*;
import st.malike.elasticsearch.kafka.watch.config.PluginConfig;
import st.malike.elasticsearch.kafka.watch.listener.ViewWatchersListener;
import st.malike.elasticsearch.kafka.watch.util.Enums;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.rest.RestRequest.Method.POST;

/**
 * @author malike_st
 */
public class SearchWatchersRestAction extends BaseRestHandler {

    private static Logger log = Logger.getLogger(SearchWatchersRestAction.class);
    private static PluginConfig pluginConfig ;

    @Inject
    public SearchWatchersRestAction(Settings settings, RestController controller) {
        super(settings);
        pluginConfig = new PluginConfig(settings);
        controller.registerHandler(POST, "/_searchkafkawatch", this);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) throws IOException {
        JSONResponse message = new JSONResponse();
        Integer from = 0;
        Integer size = 50;
        String query = null;
        if (restRequest.content().length() > 0) {
            Map<String, Object> map = XContentHelper.convertToMap(restRequest.content(), false, null).v2();
            if (!map.isEmpty()) {
                if (map.containsKey("from")) {
                    from = (Integer) map.get("from");
                }
                if (map.containsKey("limit")) {
                    size = (Integer) map.get("limit");
                }
                if (map.containsKey("param")) {
                    query = (String) map.get("param");
                }
            }
        }
        if (query == null) {
            return channel -> {
                message.setStatus(false);
                message.setCount(0L);
                message.setMessage(Enums.JSONResponseMessage.MISSING_PARAM.toString());
                XContentBuilder builder = channel.newBuilder();
                builder.startObject();
                message.toXContent(builder, restRequest);
                builder.endObject();
                channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
            };
        }
        SearchRequestBuilder prepareSearch = client.prepareSearch(
                pluginConfig.getKafkaWatchElasticsearchIndex());
        prepareSearch.setFrom(from);
        prepareSearch.setSize(size);
        prepareSearch.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        prepareSearch.setTypes(pluginConfig.getKafkaWatchElasticsearchType());
        prepareSearch.setQuery(QueryBuilders.wrapperQuery(query));
        return channel -> prepareSearch.execute(new ViewWatchersListener(channel, restRequest));
    }

}
