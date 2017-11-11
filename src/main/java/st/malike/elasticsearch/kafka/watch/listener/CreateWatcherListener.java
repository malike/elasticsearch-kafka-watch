package st.malike.elasticsearch.kafka.watch.listener;

import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;
import st.malike.elasticsearch.kafka.watch.util.Enums;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

import java.io.IOException;

/**
 * @autor malike_st
 */
public class CreateWatcherListener implements ActionListener<IndexResponse> {

    private static Logger log = Logger.getLogger(CreateWatcherListener.class);

    private final RestChannel restChannel;
    private final RestRequest restRequest;

    public CreateWatcherListener(RestChannel restChannel, RestRequest restRequest) {
        this.restChannel = restChannel;
        this.restRequest = restRequest;
    }

    @Override
    public void onResponse(IndexResponse indexResponse) {
        JSONResponse message = new JSONResponse();
        try {
            XContentBuilder builder = restChannel.newBuilder();
            if (indexResponse.getResult().getLowercase().equals("created")) {
                message.setStatus(true);
                message.setCount(1L);
                message.setData(indexResponse);
                message.setMessage(Enums.JSONResponseMessage.SUCCESS.toString());
                builder.startObject();
                message.toXContent(builder, restRequest);
                builder.endObject();
            } else {
                message.setStatus(false);
                message.setCount(0L);
                message.setData(indexResponse);
                message.setMessage(Enums.JSONResponseMessage.ERROR.toString());
                builder.startObject();
                message.toXContent(builder, restRequest);
                builder.endObject();
            }
            restChannel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
        } catch (IOException e) {
            try {
                XContentBuilder builder = restChannel.newBuilder();
                builder.startObject();
                message.setData(e.getLocalizedMessage());
                message.toXContent(builder, restRequest);
                builder.endObject();
                restChannel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
            } catch (IOException ex) {
                onFailure(e);
            }
        }
    }

    @Override
    public void onFailure(Exception e) {
        log.error("Error creating new watcher " + e.getLocalizedMessage());
        throw new ElasticsearchException("Failed to create a response.", e.getLocalizedMessage());
    }
}
