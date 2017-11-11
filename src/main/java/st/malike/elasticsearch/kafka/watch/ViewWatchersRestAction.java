package st.malike.elasticsearch.kafka.watch;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.search.sort.SortOrder;
import st.malike.elasticsearch.kafka.watch.listener.ViewWatchersListener;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.rest.RestRequest.Method.POST;

/**
 * @author malike_st
 */
public class ViewWatchersRestAction extends BaseRestHandler {

    private static Logger log = Logger.getLogger(ViewWatchersRestAction.class);

    @Inject
    public ViewWatchersRestAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(POST, "/_listkafkawatch", this);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) throws IOException {
        //limit and offset
        Integer from = 0;
        Integer size = 50;
        if (restRequest.content().length() > 0) {
            Map<String, Object> map = XContentHelper.convertToMap(restRequest.content(), false, null).v2();
            if (!map.isEmpty()) {
                if (map.containsKey("from")) {
                    from = (Integer) map.get("from");
                }
                if (map.containsKey("limit")) {
                    from = (Integer) map.get("limit");
                }
            }
        }
        SearchRequestBuilder prepareSearch = client.prepareSearch(
                ElasticKafkaWatchPlugin.getKafkaWatchElasticsearchIndex());
        prepareSearch.setFrom(from);
        prepareSearch.setSize(size);
        prepareSearch.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        prepareSearch.setTypes(ElasticKafkaWatchPlugin.getKafkaWatchElasticsearchType());
        prepareSearch.setQuery(QueryBuilders.matchAllQuery());
        return channel -> prepareSearch.execute(new ViewWatchersListener(channel, restRequest));
    }


}
