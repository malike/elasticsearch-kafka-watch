package st.malike.elasticsearch.kafka.watch;

import com.google.gson.Gson;
import org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import st.malike.elasticsearch.kafka.watch.util.Enums;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

/**
 * @author malike_st
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ElasticKafkaWatchPluginTest {


    private static final String CLUSTER_NAME = "DUMMY_CLUSTER";
    private static final String CLUSTER_HOST_ADDRESS = "localhost:9201-9210";
    private static Node node;
    private static ElasticsearchClusterRunner runner;
    private Map param;

    @BeforeClass
    public static void setUp() throws IOException {

        runner = new ElasticsearchClusterRunner();

        runner.onBuild(new ElasticsearchClusterRunner.Builder() {
            @Override
            public void build(final int number, final Settings.Builder settingsBuilder) {
                settingsBuilder.put("http.cors.allow-origin", "*");
                settingsBuilder.put("http.cors.enabled", true);
                settingsBuilder.putArray("discovery.zen.ping.unicast.hosts", CLUSTER_HOST_ADDRESS);
            }
        }).build(ElasticsearchClusterRunner.newConfigs().clusterName(CLUSTER_NAME).numOfNode(1)
                .pluginTypes("st.malike.elasticsearch.kafka.watch.ElasticKafkaWatchPlugin"));

        runner.ensureYellow();

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
    }

    @Test
    public void addNewWatcher() {
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
    public void removeWatcher() {
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
    public void viewWatchers() {
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


}
