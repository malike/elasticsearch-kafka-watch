package st.malike.elasticsearch.kafka.watch.service;

import com.google.gson.Gson;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.runners.MockitoJUnitRunner;
import st.malike.elasticsearch.kafka.watch.model.KafkaEvent;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;
import st.malike.elasticsearch.kafka.watch.util.Enums;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * @autor malike_st
 */
@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;
    @Mock
    private HttpClient httpClient;
    private Gson gson=new Gson();
    private KafkaWatch kafkaWatch;
    private HttpResponse httpResponse;
    JSONResponse jsonResponse ;
    private String HTML ="<html><title>Test</title><body>Sample </body></html>";

    @Before
    public void setUp() throws Exception {
        kafkaWatch =new KafkaWatch();
        kafkaWatch.setId(RandomStringUtils.randomAlphanumeric(5));
        kafkaWatch.setQuerySymbol(Enums.QuerySymbol.GREATER_THAN_OR_EQUAL_TO);
        kafkaWatch.setSubject("Random Kafka Watch");
        kafkaWatch.setDateCreated(new Date());
        kafkaWatch.setTriggerType(Enums.TriggerType.INDEX_OPS);
        kafkaWatch.setChannel(Arrays.asList("SMS","EMAIL"));
        kafkaWatch.setDescription("Random Kafka Watch To Test");
        kafkaWatch.setEventType("SUBSCRIPTION");
        kafkaWatch.setReportTemplatePath("/home/malike/devfiles/report.jrxml");
        kafkaWatch.setGenerateReport(true);
        kafkaWatch.setIndexName("Test");
        kafkaWatch.setExpectedHit(0);
        kafkaWatch.setReportFormat("HTML");
        kafkaWatch.setRecipient(Arrays.asList("233201234567","st.malike@gmail.com"));

        httpResponse = new BasicHttpResponse(new ProtocolVersion("Test",0,100),200,"Test");
       jsonResponse= new JSONResponse();
        jsonResponse.setData(HTML);
        jsonResponse.setCount(1L);
        jsonResponse.setStatus(true);
        jsonResponse.setMessage("SUCCESS");
        HttpEntity httpEntity =new StringEntity(new Gson().toJson(jsonResponse));
        httpResponse.setStatusCode(200);
        httpResponse.setEntity(httpEntity);
    }

    @Test
    public void testGenerateReport() throws Exception {

        Mockito.when(gson.fromJson(Mockito.any(String.class),Mockito.any())).thenReturn(jsonResponse);
        Mockito.when(httpClient.execute(Mockito.any())).thenReturn(httpResponse);


        String resp =reportService.getReport(kafkaWatch);

        Assert.assertEquals(resp,HTML);


    }
}
