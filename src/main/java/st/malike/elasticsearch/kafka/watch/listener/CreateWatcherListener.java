package st.malike.elasticsearch.kafka.watch.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestRequest;
import st.malike.elasticsearch.kafka.watch.util.Enums;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

/**
 * @autor malike_st
 */
public class CreateWatcherListener implements ActionListener<IndexResponse> {

    private final RestChannel restChannel;
    private final RestRequest restRequest;

    public CreateWatcherListener(RestChannel restChannel, RestRequest restRequest) {
        this.restChannel = restChannel;
        this.restRequest = restRequest;
    }

    @Override
    public void onResponse(IndexResponse indexResponse) {
        JSONResponse message = new JSONResponse();
        message.setStatus(false);
        message.setCount(0L);
        message.setMessage(Enums.JSONResponseMessage.SUCCESS.toString());
    }

    @Override
    public void onFailure(Exception e) {
        throw new
    }
}
