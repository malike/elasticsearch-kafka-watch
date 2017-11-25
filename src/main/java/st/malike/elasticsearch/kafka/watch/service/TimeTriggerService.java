package st.malike.elasticsearch.kafka.watch.service;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;

/**
 * @author malike_st
 */
public class TimeTriggerService {
    private static Logger log = Logger.getLogger(TimeTriggerService.class);

    public void schedule(KafkaWatch kafkaWatch) throws Exception {

    }

    public void addJob(KafkaWatch kafkaWatch) {

    }

    public void checkJobState(KafkaWatch kafkaWatch) {

    }

    public void deleteJob(KafkaWatch kafkaWatch) {

    }

    public void preloadJobs() {

    }


    class SchedulerJob implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        }
    }
}
