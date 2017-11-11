package st.malike.elasticsearch.kafka.watch.listener;

import org.apache.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestRequest;


/**
 * @autor malike_st
 */
public class DeleteWatcherListener implements ActionListener<IndexResponse> {

    private static Logger log = Logger.getLogger(DeleteWatcherListener.class);

    private final RestChannel restChannel;
    private final RestRequest restRequest;

    public DeleteWatcherListener(RestChannel restChannel, RestRequest restRequest) {
        this.restChannel = restChannel;
        this.restRequest = restRequest;
    }

    @Override
    public void onResponse(IndexResponse indexResponse) {

    }

    @Override
    public void onFailure(Exception e) {

    }
}
