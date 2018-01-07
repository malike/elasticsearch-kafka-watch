package st.malike.elasticsearch.kafka.watch;

import com.google.gson.Gson;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.rest.*;
import st.malike.elasticsearch.kafka.watch.config.PluginConfig;
import st.malike.elasticsearch.kafka.watch.listener.CreateWatcherListener;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;
import st.malike.elasticsearch.kafka.watch.util.Enums;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.rest.RestRequest.Method.POST;

/**
 * @author malike_st
 */
public class AddWatcherRestAction extends BaseRestHandler {

    private static Logger log = Logger.getLogger(AddWatcherRestAction.class);
    private static PluginConfig pluginConfig ;

    @Inject
    public AddWatcherRestAction(Settings settings, RestController controller) {
        super(settings);
        pluginConfig = new PluginConfig(settings);
        controller.registerHandler(POST, "/_newkafkawatch", this);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) throws IOException {
        JSONResponse message = new JSONResponse();
        IndexRequestBuilder prepareIndex = client.prepareIndex(pluginConfig.getKafkaWatchElasticsearchIndex(),
                pluginConfig.getKafkaWatchElasticsearchType());
        KafkaWatch kafkaWatch = new KafkaWatch();
        if (restRequest.content().length() > 0) {
            Map<String, Object> map = XContentHelper.convertToMap(restRequest.content(), false, null).v2();
            if (!map.isEmpty()) {
                if (map.containsKey("cron")) {
                    kafkaWatch.setCron((String) map.get("cron"));
                }
                if (map.containsKey("channel")) {
                    List<String> channels = (map.get("channel") instanceof List) ? (List<String>) map.get("channel")
                            : new LinkedList<>();
                    if (map.get("channel") instanceof String) {
                        channels.add((String) map.get("channel"));
                    }
                    kafkaWatch.setChannel(channels);
                }
                if (map.containsKey("description")) {
                    kafkaWatch.setDescription((String) map.get("description"));
                }
                if (map.containsKey("eventType")) {
                    kafkaWatch.setEventType((String) map.get("eventType"));
                }
                if (map.containsKey("expectedHit")) {
                    kafkaWatch.setExpectedHit(Long.parseLong((String) map.get("expectedHit")));
                }
                if (map.containsKey("generateReport")) {
                    kafkaWatch.setGenerateReport((Boolean) map.get("generateReport"));
                }
                if (map.containsKey("indexName")) {
                    kafkaWatch.setIndexName((String) map.get("indexName"));
                }
                if (map.containsKey("query")) {
                    kafkaWatch.setIndexOpsQuery((String) map.get("query"));
                }
                if (map.containsKey("reportTemplatePath")) {
                    kafkaWatch.setReportTemplatePath((String) map.get("reportTemplatePath"));
                }
                if (map.containsKey("miscData")) {
                    kafkaWatch.setMiscData((Map<String, String>) map.get("miscData"));
                }
                if (map.containsKey("recipient")) {
                    List<String> recipients = (map.get("recipient") instanceof List) ? (List<String>) map.get("recipient")
                            : new LinkedList<>();
                    if (map.get("recipient") instanceof String) {
                        recipients.add((String) map.get("recipient"));
                    }
                    kafkaWatch.setRecipient(recipients);
                }
                if (map.containsKey("subject")) {
                    kafkaWatch.setSubject((String) map.get("subject"));
                }
                if (map.containsKey("querySymbol")) {
                    try {
                        kafkaWatch.setQuerySymbol(Enums.QuerySymbol.valueOf(
                                ((String) map.get("querySymbol")).toUpperCase()
                        ));
                    } catch (Exception e) {

                    }
                }
                if (map.containsKey("trigger")) {
                    try {
                        kafkaWatch.setTriggerType(Enums.TriggerType.valueOf(
                                ((String) map.get("trigger")).toUpperCase()
                        ));
                    } catch (Exception e) {

                    }
                }
                kafkaWatch.setId(RandomStringUtils.randomAlphabetic(5));
                kafkaWatch.setDateCreated(new Date());
            } else {
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
        }
        if (kafkaWatch.getTriggerType() == null || kafkaWatch.getIndexName() == null) {
            return channel -> {
                message.setStatus(false);
                message.setCount(0L);
                message.setData("Trigger Type or Index not specified");
                message.setMessage(Enums.JSONResponseMessage.INVALID_DATA.toString());
                XContentBuilder builder = channel.newBuilder();
                builder.startObject();
                message.toXContent(builder, restRequest);
                builder.endObject();
                channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
            };
        }
        if (kafkaWatch.isGenerateReport() && kafkaWatch.getQuerySymbol() == null
                && kafkaWatch.getReportTemplatePath() == null) {
            return channel -> {
                message.setStatus(false);
                message.setCount(0L);
                message.setData("Watch not configured properly for report");
                message.setMessage(Enums.JSONResponseMessage.NOT_CONFIGURED_FOR_REPORTS.toString());
                XContentBuilder builder = channel.newBuilder();
                builder.startObject();
                message.toXContent(builder, restRequest);
                builder.endObject();
                channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
            };
        }
        Gson gson = new Gson();
        prepareIndex.setOpType(DocWriteRequest.OpType.CREATE);
        prepareIndex.setId(kafkaWatch.getId());
        Map m = gson.fromJson(gson.toJson(kafkaWatch), Map.class);
        prepareIndex.setSource(m);
        return channel -> prepareIndex.execute(new CreateWatcherListener(channel, restRequest, kafkaWatch));
    }
}
