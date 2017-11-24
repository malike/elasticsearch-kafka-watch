package st.malike.elasticsearch.kafka.watch.service;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.util.TimerTask;

/**
 * @author malike_st
 */
public class TimeTriggerService {
    private static Logger log = Logger.getLogger(TimeTriggerService.class);

    public void schedule() throws Exception{

        SchedulerFactory schedulerFactory=new StdSchedulerFactory();

        Scheduler scheduler= schedulerFactory.getScheduler();

        JobDetailImpl jobDetail=new JobDetailImpl();
        jobDetail.setGroup("Test");
        jobDetail.setName("Test");
        jobDetail.setJobClass(SchedulerJob.class);

        CronTriggerImpl trigger=new CronTriggerImpl();
        trigger.setCronExpression("0 0/1 * * * ?");
        trigger.setName("Test");
        trigger.setGroup("Test");

        scheduler.scheduleJob(jobDetail,trigger);

        scheduler.start();
        

    }

    public void addJob(){

    }

    public void checkJob(){

    }

    public void deleteJob(){

    }

    public void preloadJobs(){

    }


    class SchedulerJob implements Job {

        public SchedulerJob(){
            //Some stuffs
        }

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("Hi see you after 10 seconds");
        }
    }
}
