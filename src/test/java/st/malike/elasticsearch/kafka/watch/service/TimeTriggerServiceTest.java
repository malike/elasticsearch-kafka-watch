package st.malike.elasticsearch.kafka.watch.service;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;
import st.malike.elasticsearch.kafka.watch.util.Enums;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

/**
 * @autor malike_st
 */
@RunWith(MockitoJUnitRunner.class)
public class TimeTriggerServiceTest {

    @InjectMocks
    @Spy
    private TimeTriggerService timeTriggerService;
    @Mock
    private KafkaWatchService kafkaWatchService;
    @Mock
    private Scheduler scheduler;
    @Mock
    private SchedulerFactory schedulerFactory;
    private KafkaWatch kafkaWatch;


    @Before
    public void setUp() throws Exception {
        kafkaWatch = new KafkaWatch();
        kafkaWatch.setCron("0/20 * * * * ?");
        kafkaWatch.setId(RandomStringUtils.randomAlphanumeric(5));
        kafkaWatch.setQuerySymbol(Enums.QuerySymbol.GREATER_THAN_OR_EQUAL_TO);
        kafkaWatch.setSubject("Random Kafka Watch");
        kafkaWatch.setDateCreated(new Date());
        kafkaWatch.setTriggerType(Enums.TriggerType.INDEX_OPS);
        kafkaWatch.setChannel(Arrays.asList("SMS", "EMAIL"));
        kafkaWatch.setDescription("Random Kafka Watch To Test");
        kafkaWatch.setEventType("SUBSCRIPTION");
        kafkaWatch.setIndexName("Test");
        kafkaWatch.setExpectedHit(1L);
        kafkaWatch.setRecipient(Arrays.asList("233201234567", "st.malike@gmail.com"));

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void schedule() throws Exception {

        Mockito.when(scheduler.isStarted()).thenReturn(true);
        Mockito.when(kafkaWatchService.findAllWatch()).thenReturn(new LinkedList<KafkaWatch>());
        timeTriggerService.schedule();

        Assert.assertTrue(scheduler.isStarted());

    }

    @Test
    public void addJob() throws Exception {

        Mockito.when(scheduler.getJobDetail(new JobKey(kafkaWatch.getId()))).thenReturn(new JobDetailImpl());
        Mockito.when(scheduler.scheduleJob(Mockito.any(JobDetailImpl.class), Mockito.any(CronTriggerImpl.class)))
                .thenReturn(new Date());

        Mockito.when(scheduler.getJobDetail(new JobKey(kafkaWatch.getId()))).thenReturn(new JobDetailImpl());

        timeTriggerService.addJob(kafkaWatch);

    }


    @Test
    public void deleteJob() throws Exception {

        Mockito.when(scheduler.getJobDetail(new JobKey(kafkaWatch.getId()))).thenReturn(new JobDetailImpl());

        timeTriggerService.deleteJob(kafkaWatch);

    }


}