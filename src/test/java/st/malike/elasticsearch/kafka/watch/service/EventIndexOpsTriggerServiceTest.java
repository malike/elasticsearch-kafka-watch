package st.malike.elasticsearch.kafka.watch.service;

import org.apache.commons.lang.RandomStringUtils;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.search.SearchHits;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;
import st.malike.elasticsearch.kafka.watch.util.Enums;

import java.util.Arrays;
import java.util.Date;

/**
 * @autor malike_st
 */
@RunWith(MockitoJUnitRunner.class)
public class EventIndexOpsTriggerServiceTest {

    @Mock
    private SearchHits searchHits;
    @Spy
    @InjectMocks
    private EventIndexOpsTriggerService eventIndexOpsTriggerService;
    @Mock
    private Engine.Index index;
    @Mock
    private Engine.IndexResult indexResult;
    @Mock
    private Engine.Delete delete;
    @Mock
    private Engine.DeleteResult deleteResult;
    @Mock
    private KafkaWatchService kafkaWatchService;
    private KafkaWatch kafkaWatch;
    private String INDEX_NAME = "TEST";


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
        kafkaWatch.setExpectedHit(2L);
        kafkaWatch.setReportFormat("HTML");
        kafkaWatch.setRecipient(Arrays.asList("233201234567", "st.malike@gmail.com"));


    }


    @Test
    public void testEvaluateRuleForIndexCreateWithNoWatch() {

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(INDEX_NAME,
                index, indexResult, null);
        Assert.assertFalse(rule);
    }

    @Test
    public void testEvaluateRuleForIndexCreateWithUnmatchingIndexName() {

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(INDEX_NAME + "TEST",
                index, indexResult, kafkaWatch);
        Assert.assertFalse(rule);
    }

    @Test
    public void testEvaluateRuleForIndexCreateGreaterOrEqualTo() {

        Mockito.when(kafkaWatchService.executeWatchQuery(kafkaWatch.getIndexOpsQuery()))
                .thenReturn(searchHits);
        Mockito.when(searchHits.getTotalHits()).thenReturn(kafkaWatch.getExpectedHit());
        Mockito.when(indexResult.isCreated()).thenReturn(true);

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(kafkaWatch.getIndexName(),
                index, indexResult, kafkaWatch);
        Assert.assertTrue(rule);
    }

    @Test
    public void testEvaluateRuleForIndexCreateEqualTo() {
        kafkaWatch.setQuerySymbol(Enums.QuerySymbol.EQUAL_TO);

        Mockito.when(kafkaWatchService.executeWatchQuery(kafkaWatch.getIndexOpsQuery()))
                .thenReturn(searchHits);
        Mockito.when(searchHits.getTotalHits()).thenReturn(kafkaWatch.getExpectedHit());
        Mockito.when(indexResult.isCreated()).thenReturn(true);

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(kafkaWatch.getIndexName(),
                index, indexResult, kafkaWatch);
        Assert.assertTrue(rule);
    }

    @Test
    public void testEvaluateRuleForIndexCreateLessOrEqualTo() {
        kafkaWatch.setQuerySymbol(Enums.QuerySymbol.LESS_THAN_OR_EQUAL_TO);

        Mockito.when(kafkaWatchService.executeWatchQuery(kafkaWatch.getIndexOpsQuery()))
                .thenReturn(searchHits);
        Mockito.when(searchHits.getTotalHits()).thenReturn(kafkaWatch.getExpectedHit());
        Mockito.when(indexResult.isCreated()).thenReturn(true);

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(kafkaWatch.getIndexName(),
                index, indexResult, kafkaWatch);
        Assert.assertTrue(rule);
    }

    @Test
    public void testEvaluateRuleForIndexCreateNotEqualTOHit() {
        kafkaWatch.setQuerySymbol(Enums.QuerySymbol.EQUAL_TO);

        Mockito.when(kafkaWatchService.executeWatchQuery(kafkaWatch.getIndexOpsQuery()))
                .thenReturn(searchHits);
        Mockito.when(searchHits.getTotalHits()).thenReturn(15L);

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(kafkaWatch.getIndexName(),
                index, indexResult, kafkaWatch);
        Assert.assertFalse(rule);
    }

    @Test
    public void testEvaluateRuleForDeleteCreateWithNoWatch() {

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(INDEX_NAME,
                delete, deleteResult, null);
        Assert.assertFalse(rule);
    }

    @Test
    public void testEvaluateRuleForDeleteCreateWithUnmatchingIndexName() {

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(INDEX_NAME + "T",
                delete, deleteResult, kafkaWatch);
        Assert.assertFalse(rule);
    }

    @Test
    public void testEvaluateRuleForIndexDeletedGreaterOrEqualTo() {

        Mockito.when(kafkaWatchService.executeWatchQuery(kafkaWatch.getIndexOpsQuery()))
                .thenReturn(searchHits);
        Mockito.when(searchHits.getTotalHits()).thenReturn(kafkaWatch.getExpectedHit());
        Mockito.when(deleteResult.isFound()).thenReturn(true);

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(kafkaWatch.getIndexName(),
                delete, deleteResult, kafkaWatch);
        Assert.assertTrue(rule);
    }

    @Test
    public void testEvaluateRuleForIndexDeleteEqualTo() {

        kafkaWatch.setQuerySymbol(Enums.QuerySymbol.EQUAL_TO);

        Mockito.when(kafkaWatchService.executeWatchQuery(kafkaWatch.getIndexOpsQuery()))
                .thenReturn(searchHits);
        Mockito.when(searchHits.getTotalHits()).thenReturn(kafkaWatch.getExpectedHit());
        Mockito.when(deleteResult.isFound()).thenReturn(true);

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(kafkaWatch.getIndexName(),
                delete, deleteResult, kafkaWatch);
        Assert.assertTrue(rule);
    }

    @Test
    public void testEvaluateRuleForIndexDeleteLessOrEqualTo() {
        kafkaWatch.setQuerySymbol(Enums.QuerySymbol.LESS_THAN_OR_EQUAL_TO);

        Mockito.when(kafkaWatchService.executeWatchQuery(kafkaWatch.getIndexOpsQuery()))
                .thenReturn(searchHits);
        Mockito.when(searchHits.getTotalHits()).thenReturn(kafkaWatch.getExpectedHit());
        Mockito.when(deleteResult.isFound()).thenReturn(true);

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(kafkaWatch.getIndexName(),
                delete, deleteResult, kafkaWatch);
        Assert.assertTrue(rule);
    }



}
