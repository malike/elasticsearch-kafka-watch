package st.malike.elasticsearch.kafka.watch;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.*;
import st.malike.elasticsearch.kafka.watch.util.Enums;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

import java.io.IOException;

import static org.elasticsearch.rest.RestRequest.Method.POST;

/**
 * @author malike_st
 */
public class ViewWatchersRestAction extends BaseRestHandler {

    @Inject
    public ViewWatchersRestAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(POST, "/_listkafkawatch", this);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) throws IOException {
        JSONResponse response = new JSONResponse();
        return channel -> {
            response.setStatus(true);
            response.setCount(0L);
            response.setMessage(Enums.JSONResponseMessage.SUCCESS.toString());
            XContentBuilder builder = channel.newBuilder();
            builder.startObject();
            response.toXContent(builder, restRequest);
            builder.endObject();
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
        };
    }

}
