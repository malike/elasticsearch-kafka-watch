package st.malike.elasticsearch.kafka.watch.service;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import st.malike.elasticsearch.kafka.watch.model.KafkaEvent;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;
import st.malike.elasticsearch.kafka.watch.util.Enums;

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
    private KafkaWatch kafkaWatch;
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
        kafkaWatch.setGenerateReport(true);
        kafkaWatch.setIndexName("Test");
        kafkaWatch.setExpectedHit(0);
        kafkaWatch.setReportFormat("HTML");
        kafkaWatch.setRecipient(Arrays.asList("233201234567","st.malike@gmail.com"));
    }

    @Test
    public void testGenerateReport() {
        Mockito.when(reportService.executeService()).thenReturn(HTML);

        Mockito.verify()
    }
}
