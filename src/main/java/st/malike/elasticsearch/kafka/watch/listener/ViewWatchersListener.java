package st.malike.elasticsearch.kafka.watch.listener;

import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import st.malike.elasticsearch.kafka.watch.util.Enums;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        JSONResponse message = new JSONResponse();
        try {
            XContentBuilder builder = restChannel.newBuilder();
            List dataList = extractData(searchResponse);
            message.setStatus(true);
            message.setCount(Long.valueOf(searchResponse.getHits().getTotalHits()));
            message.setData(dataList);
            message.setMessage(Enums.JSONResponseMessage.SUCCESS.toString());
            builder.startObject();
            message.toXContent(builder, restRequest);
            builder.endObject();
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
        log.error("Error loading watchers " + e.getLocalizedMessage());
        try {
            if (e instanceof IndexNotFoundException) {
                JSONResponse message = new JSONResponse();
                XContentBuilder builder = restChannel.newBuilder();
                builder.startObject();
                message.setStatus(true);
                message.setCount(0L);
                message.setMessage(Enums.JSONResponseMessage.SUCCESS.toString());
                message.toXContent(builder, restRequest);
                builder.endObject();
                restChannel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
            }
        } catch (Exception ex) {
        }
        throw new ElasticsearchException("Exception :", e.getLocalizedMessage());
    }

    public List<Map> extractData(SearchResponse response) {
        List<Map> data = new LinkedList<>();
        SearchHits hits = response.getHits();
        try {
            for (SearchHit hit : hits) {
                Map<String, Object> sourceMap = hit.getSourceAsMap();
                data.add(sourceMap);
            }
        } catch (Exception e) {
        }
        return data;
    }

}

