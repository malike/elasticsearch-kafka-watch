package st.malike.elasticsearch.kafka.watch.listener;

import org.apache.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestRequest;

/**
 * @autor malike_st
 */
public class ViewWatchersListener implements ActionListener<SearchResponse> {

    private static Logger log = Logger.getLogger(ViewWatchersListener.class);

    private final RestChannel restChannel;
    private final RestRequest restRequest;

    public ViewWatchersListener(RestChannel restChannel, RestRequest restRequest) {
        this.restChannel = restChannel;
        this.restRequest = restRequest;
    }

    @Override
    public void onResponse(SearchResponse searchResponse) {

    }

    @Override
    public void onFailure(Exception e) {

    }
}

