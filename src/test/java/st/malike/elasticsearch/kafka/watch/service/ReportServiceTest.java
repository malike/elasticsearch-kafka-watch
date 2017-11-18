package st.malike.elasticsearch.kafka.watch.service;

import com.google.gson.Gson;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.runners.MockitoJUnitRunner;
import st.malike.elasticsearch.kafka.watch.exception.TemplateFileNotFoundException;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;
import st.malike.elasticsearch.kafka.watch.util.Enums;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

import java.util.Arrays;
import java.util.Date;

/**
 * @autor malike_st
 */
@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    HttpResponse httpResponse;
    JSONResponse jsonResponse;
    @InjectMocks
    @Spy
    private ReportService reportService;
    @Mock
    private HttpClient httpClient;
    @Mock
    private StatusLine statusLine;
    private KafkaWatch kafkaWatch;
    private String HTML = "<html><title>Test</title><body>Sample </body></html>";

    @Before
    public void setUp() throws Exception {
        kafkaWatch = new KafkaWatch();
        kafkaWatch.setId(RandomStringUtils.randomAlphanumeric(5));
        kafkaWatch.setQuerySymbol(Enums.QuerySymbol.GREATER_THAN_OR_EQUAL_TO);
        kafkaWatch.setSubject("Random Kafka Watch");
        kafkaWatch.setDateCreated(new Date());
        kafkaWatch.setTriggerType(Enums.TriggerType.INDEX_OPS);
        kafkaWatch.setChannel(Arrays.asList("SMS", "EMAIL"));
        kafkaWatch.setDescription("Random Kafka Watch To Test");
        kafkaWatch.setEventType("SUBSCRIPTION");
        kafkaWatch.setReportTemplatePath("/home/malike/devfiles/report.jrxml");
        kafkaWatch.setGenerateReport(true);
        kafkaWatch.setIndexName("Test");
        kafkaWatch.setExpectedHit(0);
        kafkaWatch.setReportFormat("HTML");
        kafkaWatch.setRecipient(Arrays.asList("233201234567", "st.malike@gmail.com"));


        jsonResponse = new JSONResponse();
        jsonResponse.setData(HTML);
        jsonResponse.setCount(1L);
        jsonResponse.setStatus(true);
        jsonResponse.setMessage("SUCCESS");

        String response = new Gson().toJson(jsonResponse);
        StringEntity httpEntity = new StringEntity(response);
        httpEntity.setContentType("application/json");
        httpEntity.setContentEncoding(Charsets.UTF_8.name());
        httpEntity.setChunked(false);
        httpResponse = new BasicHttpResponse(new ProtocolVersion("HTTP", 1, 1), 200, "Test");

        httpResponse.setStatusCode(200);
        httpResponse.setEntity(httpEntity);
    }

    @Test
    public void testGenerateReport() throws Exception {

        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any())).thenReturn(httpResponse);
        Mockito.doReturn(true).when(reportService).validateReportFile(kafkaWatch);


        Assert.assertTrue(reportService.getReport(kafkaWatch).equals(HTML));
        Mockito.verify(httpClient, VerificationModeFactory.times(1)).execute(Mockito.any());

    }

    @Test(expected = TemplateFileNotFoundException.class)
    public void testGenerateReportTemplateFileNotFound() throws Exception {

        Mockito.when(httpClient.execute(Mockito.any())).thenReturn(httpResponse);

        reportService.getReport(kafkaWatch);
    }


}
