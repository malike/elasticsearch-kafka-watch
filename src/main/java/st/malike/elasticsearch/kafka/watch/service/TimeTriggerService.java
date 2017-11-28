package st.malike.elasticsearch.kafka.watch.service;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;

import java.util.List;

/**
 * @author malike_st
 */
public class TimeTriggerService {
    private static Logger log = Logger.getLogger(TimeTriggerService.class);
    private KafkaWatchService kafkaWatchService;
    private Scheduler scheduler;
    private JobDetailImpl jobDetail;


    public void schedule() throws Exception {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();

        jobDetail = new JobDetailImpl();
        jobDetail.setGroup("Kafka-Elasticsearch");
        jobDetail.setName("Kafka-Elasticsearch");
        jobDetail.setJobClass(SchedulerJob.class);


        List<KafkaWatch> watches = kafkaWatchService.findAllWatch();
        if (!watches.isEmpty()) {
            for (KafkaWatch watch : watches) {
                addJob(watch);
            }
        }

        scheduler.start();
    }

    public JobDetail addJob(KafkaWatch kafkaWatch) throws Exception {

        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setCronExpression(kafkaWatch.getCron());
        cronTrigger.setName(kafkaWatch.getId());
        cronTrigger.setGroup(kafkaWatch.getId());

        if (scheduler == null) {
            schedule();
        }

        scheduler.scheduleJob(jobDetail, cronTrigger);
        if (!scheduler.isStarted()) {
            scheduler.start();
        }

        return scheduler.getJobDetail(new JobKey(kafkaWatch.getId()));
    }


    public void deleteJob(KafkaWatch kafkaWatch) throws Exception {
        if (kafkaWatch == null) {
            return;
        }
        JobDetail jobDetail = scheduler.getJobDetail(new JobKey(kafkaWatch.getId()));
        if (jobDetail != null) {
            scheduler.deleteJob(new JobKey(kafkaWatch.getId()));
        }
    }


    class SchedulerJob implements Job {


        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        }
    }
}
