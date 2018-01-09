package st.malike.elasticsearch.kafka.watch;

import com.google.gson.Gson;
import com.jayway.restassured.response.ValidatableResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import st.malike.elasticsearch.kafka.watch.util.Enums;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

/**
 * @author malike_st
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ElasticKafkaWatchPluginTest {


    private  static final String KAFKA_WATCH_ELASTICSEARCH_TYPE = "kafka.watch.elasticsearch.type";
    private  static final String KAFKA_WATCH_BOOTSTRAP_SERVERS = "localhost:9092";
    private  static final String KAFKA_WATCH_TOPIC = "kafka.watch.topic";
    private  static final String KAFKA_WATCH_DISABLE = "kafka.watch.disable";
    private  static final String REPORT_ENGINE_ENDPOINT = "report.engine.endpoint";
    private  static final String REPORT_ENGINE_DISABLE = "report.engine.disable";
    private static final String INDEX_NAME = "kafka_watch";
    private static final String INDEX_TYPE = "kafka_topic";
    private static final String CLUSTER_NAME = "DUMMY_CLUSTER";
    private static final String CLUSTER_HOST_ADDRESS = "localhost:9201-9210";
    private static Node node;
    private static ElasticsearchClusterRunner runner;
    String queryString = "{"
            + "    \"match\": {"
            + "      \"description\": \"SUBSCRIPTION\""
            + "    }"
            + "}";
    private Map param;



    @BeforeClass
    public static void setUp() throws IOException {

        runner = new ElasticsearchClusterRunner();

        runner.onBuild(new ElasticsearchClusterRunner.Builder() {
            @Override
            public void build(final int number, final Settings.Builder settingsBuilder) {
                settingsBuilder.put("http.cors.allow-origin", "*");
                settingsBuilder.put("http.cors.enabled", true);
                settingsBuilder.put(KAFKA_WATCH_ELASTICSEARCH_TYPE, "test");
                settingsBuilder.put(KAFKA_WATCH_BOOTSTRAP_SERVERS, "localhost:9092");
                settingsBuilder.put(KAFKA_WATCH_TOPIC, "kafka-watch");
                settingsBuilder.put(KAFKA_WATCH_DISABLE, false);
                settingsBuilder.put(REPORT_ENGINE_ENDPOINT, "http://localhost-dummy");
                settingsBuilder.put(REPORT_ENGINE_DISABLE, false);
                settingsBuilder.putArray("discovery.zen.ping.unicast.hosts", CLUSTER_HOST_ADDRESS);
            }
        }).build(ElasticsearchClusterRunner.newConfigs().clusterName(CLUSTER_NAME).numOfNode(1)
                .pluginTypes("st.malike.elasticsearch.kafka.watch.ElasticKafkaWatchPlugin"));

        runner.ensureYellow();

        // create an index
        runner.createIndex(INDEX_NAME, (Settings) null);


        runner.refresh();

        node = runner.node();
    }

    @AfterClass
    public static void tearDown() throws IOException {
        runner.close();
        runner.clean();
    }


    @Before
    public void setUpTest() {
        param = new HashMap();
        runner.deleteIndex(INDEX_NAME);
        runner.createIndex(INDEX_NAME, (Settings) null);

    }

    @Test
    public void addDummyData() {
        given()
                .log().all().contentType("application/json")
                .body("{" +
                        "    \"user\" : \"kimchy\",\n" +
                        "    \"post_date\" : \"2009-11-15T14:12:12\",\n" +
                        "    \"message\" : \"trying out Elasticsearch\"\n" +
                        "}")
                .when()
                .post("http://localhost:9201/" + INDEX_NAME + "/" + INDEX_TYPE + "/")
                .then()
                .statusCode(201);
    }

    @Test
    public void addNewWatcherEmptyParams() {
        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(false))
                .body("message", Matchers.is(Enums.JSONResponseMessage.MISSING_PARAM.toString()));
    }

    @Test
    public void addNewWatcherMissingParam() {

        param.put("querySymbol", "");
        param.put("expectedHit", "5");

        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(false))
                .body("message", Matchers.is(Enums.JSONResponseMessage.INVALID_DATA.toString()));
    }

    @Test
    public void addNewWatcherForReportWithoutTemplate() {


        param.put("eventType", "SUBSCRIPTION_REPORT");
        param.put("description", "Send reports for subscription daily");
        param.put("channel", "EMAIL");
        param.put("cron", "0 0 9 * * MON-FRI");
        param.put("query", queryString);
        param.put("generateReport", true);
        param.put("trigger", Enums.TriggerType.TIME.toString());
        param.put("indexName", INDEX_NAME);


        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(false))
                .body("message", Matchers.is(Enums.JSONResponseMessage.NOT_CONFIGURED_FOR_REPORTS.toString()));
    }

    @Test
    public void addNewWatcherForReportWithoutTemplateOrQuery() {


        param.put("eventType", "SUBSCRIPTION_REPORT");
        param.put("description", "Send reports for subscription daily");
        param.put("channel", "EMAIL");
        param.put("cron", "0 0 9 * * MON-FRI");
        param.put("generateReport", true);
        param.put("trigger", Enums.TriggerType.TIME.toString());
        param.put("indexName", INDEX_NAME);


        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(false))
                .body("message", Matchers.is(Enums.JSONResponseMessage.NOT_CONFIGURED_FOR_REPORTS.toString()));
    }

    @Test
    public void addNewWatcherReportKafkaWatch() {

        param.put("eventType", "SUBSCRIPTION_REPORT");
        param.put("description", "Send reports for subscription daily");
        param.put("channel", "EMAIL");
        param.put("cron", "0 0 9 * * MON-FRI");
        param.put("query", queryString);
        param.put("generateReport", true);
        param.put("reportTemplatePath", "/home/malike/dev/report.jrxml");
        param.put("trigger", Enums.TriggerType.TIME.toString());
        param.put("indexName", INDEX_NAME);


        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(true))
                .body("message", Matchers.is(Enums.JSONResponseMessage.SUCCESS.toString()));
    }

    @Test
    public void addNewWatcher() {

        param.put("eventType", "SUBSCRIPTION");
        param.put("description", "Send welcome notification for every subscription created");
        param.put("channel", "SMS");
        param.put("trigger", "INDEX_OPS");
        param.put("indexName", INDEX_NAME);


        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(true))
                .body("message", Matchers.is(Enums.JSONResponseMessage.SUCCESS.toString()));
    }

    @Test
    public void removeWatcherNoParams() {
        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_removekafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(false))
                .body("message", Matchers.is(Enums.JSONResponseMessage.MISSING_PARAM.toString()));
    }

    @Test
    public void removeWatcherUnknownWatcherId() {

        param.put("id", RandomStringUtils.randomAlphanumeric(5));

        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_removekafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(false))
                .body("message", Matchers.is(Enums.JSONResponseMessage.DATA_NOT_FOUND.toString()));
    }

    @Test
    public void removeWatcher() {

        param.put("eventType", "SUBSCRIPTION");
        param.put("description", "Send welcome notification for every subscription created");
        param.put("channel", "SMS");
        param.put("trigger", "INDEX_OPS");
        param.put("indexName", INDEX_NAME);


        ValidatableResponse validatableResponse = given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200);

        Map response = new Gson().fromJson((String) validatableResponse.extract().body()
                .jsonPath().get("data"), Map.class);
        param.put("id", response.get("id"));

        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_removekafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(true))
                .body("message", Matchers.is(Enums.JSONResponseMessage.SUCCESS.toString()));
    }

    @Test
    public void viewWatchersWithNoWatchersCreated() {
        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_listkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(true))
                .body("message", Matchers.is(Enums.JSONResponseMessage.SUCCESS.toString()));
    }

    @Test
    public void viewWatchers() {


        param.put("eventType", "SUBSCRIPTION");
        param.put("description", "Send welcome notification for every subscription created");
        param.put("channel", "SMS");
        param.put("trigger", "INDEX_OPS");
        param.put("indexName", INDEX_NAME);


        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(true));

        //to refresh data... for fetch
        runner.flush();
        runner.refresh();

        param = new HashMap<>();

        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_listkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(true))
                .body("data[0].eventType", Matchers.is("SUBSCRIPTION"))
                .body("message", Matchers.is(Enums.JSONResponseMessage.SUCCESS.toString()));
    }

    @Test
    public void searchWatchersNoQuery() {
        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_searchkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(false))
                .body("message", Matchers.is(Enums.JSONResponseMessage.MISSING_PARAM.toString()));
    }

    @Test
    public void searchWatchers() {


        param.put("eventType", "SUBSCRIPTION");
        param.put("description", "Send welcome notification for every subscription created");
        param.put("channel", "SMS");
        param.put("trigger", "INDEX_OPS");
        param.put("indexName", INDEX_NAME);


        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(true));


        String queryString = "{"
                + "    \"match\": {"
                + "      \"eventType\": \"SUBSCRIPTION\""
                + "    }"
                + "}";

        param.put("param", queryString);


        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_searchkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(true))
                .body("data[0].eventType", Matchers.is("SUBSCRIPTION"))
                .body("message", Matchers.is(Enums.JSONResponseMessage.SUCCESS.toString()));
    }


    @Test
    public void searchWatchersByIndex() {


        param.put("eventType", "SUBSCRIPTION");
        param.put("description", "Send welcome notification for every subscription created");
        param.put("channel", "SMS");
        param.put("trigger", "INDEX_OPS");
        param.put("indexName", INDEX_NAME);


        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(true));

        //to refresh data... for fetch
        runner.flush();
        runner.refresh();


        String queryString = "{"
                + "    \"match\": {"
                + "      \"indexName\": \"" + INDEX_NAME + "\""
                + "    }"
                + "}";

        param.put("param", queryString);


        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_searchkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(true))
                .body("data[0].eventType", Matchers.is("SUBSCRIPTION"))
                .body("message", Matchers.is(Enums.JSONResponseMessage.SUCCESS.toString()));
    }


    @Test
    public void testEventTriggerAfterDocumentIndexed() {

        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(false))
                .body("message", Matchers.is(Enums.JSONResponseMessage.MISSING_PARAM.toString()));
    }

    @Test
    public void testEventNotTriggerAfterDocumentIndexed() {

        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(false))
                .body("message", Matchers.is(Enums.JSONResponseMessage.MISSING_PARAM.toString()));
    }

    @Test
    public void testEventTriggerBySchedule() {

        given()
                .log().all().contentType("application/json")
                .body(new Gson().toJson(param))
                .when()
                .post("http://localhost:9201/_newkafkawatch")
                .then()
                .statusCode(200)
                .body("status", Matchers.is(false))
                .body("message", Matchers.is(Enums.JSONResponseMessage.MISSING_PARAM.toString()));
    }


}
