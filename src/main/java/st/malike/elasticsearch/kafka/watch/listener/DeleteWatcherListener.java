package st.malike.elasticsearch.kafka.watch.listener;

import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;
import st.malike.elasticsearch.kafka.watch.service.TimeTriggerService;
import st.malike.elasticsearch.kafka.watch.util.Enums;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

import java.io.IOException;


/**
 * @autor malike_st
 */
public class DeleteWatcherListener implements ActionListener<DeleteResponse> {

    private static Logger log = Logger.getLogger(DeleteWatcherListener.class);
    private static TimeTriggerService timeTriggerService = new TimeTriggerService();

    private final RestChannel restChannel;
    private final RestRequest restRequest;

    public DeleteWatcherListener(RestChannel restChannel, RestRequest restRequest) {
        this.restChannel = restChannel;
        this.restRequest = restRequest;
    }

    @Override
    public void onResponse(DeleteResponse deleteResponse) {
        JSONResponse message = new JSONResponse();
        try {
            XContentBuilder builder = restChannel.newBuilder();
            if (deleteResponse.getResult().getLowercase().equals("deleted")) {
                message.setStatus(true);
                message.setCount(1L);
                message.setMessage(Enums.JSONResponseMessage.SUCCESS.toString());
                builder.startObject();
                message.toXContent(builder, restRequest);
                builder.endObject();
            } else {
                message.setStatus(false);
                message.setCount(0L);
                message.setData(deleteResponse);
                if (deleteResponse.getResult().getLowercase().equals("not_found")) {
                    message.setMessage(Enums.JSONResponseMessage.DATA_NOT_FOUND.toString());
                } else {
                    message.setMessage(Enums.JSONResponseMessage.ERROR.toString());
                }
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
                throw new ElasticsearchException("Exception :", e.getLocalizedMessage());
            }
        }
    }

    @Override
    public void onFailure(Exception e) {
        log.error("Error deleting watcher " + e.getLocalizedMessage());
        throw new ElasticsearchException("Exception :", e.getLocalizedMessage());
    }
}
