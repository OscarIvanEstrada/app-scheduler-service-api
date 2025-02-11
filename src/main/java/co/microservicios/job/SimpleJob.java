package co.microservicios.job;


import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import co.microservicios.repository.JobRepository;
import co.microservicios.services.JobService;
//DONE: Add documentation for class and methods
/**
 * SimpleJob: This class represents a simple scheduled job that extends QuartzJobBean and implements InterruptableJob.
 * It is responsible for executing the job logic when triggered by the scheduler.
 */
public class SimpleJob extends QuartzJobBean implements InterruptableJob{
	
	private volatile boolean toStopFlag = true;
	
	@Autowired
	JobService jobService;

	@Autowired
    JobRepository jobRepository;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobKey key = jobExecutionContext.getJobDetail().getKey();
		System.out.println("Simple Job started with key :" + key.getName() + ", Group :"+key.getGroup() + " , Thread Name :"+Thread.currentThread().getName());
		

		//*********** For retrieving stored key-value pairs ***********/
		/*JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
		String myValue = dataMap.getString("msisdn");
		System.out.println("Value:" + myValue);
		*/
		//Job job = jobRepository.findJobByGroupAndJobName(key.getGroup(),key.getName());
		//System.out.println(job);

	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		System.out.println("Stopping thread... ");
		toStopFlag = false;
	}

}
