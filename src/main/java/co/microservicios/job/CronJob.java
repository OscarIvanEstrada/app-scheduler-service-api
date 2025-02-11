package co.microservicios.job;

import java.util.Date;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestTemplate;

import co.microservicios.model.Job;
import co.microservicios.model.Trigger;
import co.microservicios.repository.JobRepository;
import co.microservicios.repository.TriggerRepository;
import co.microservicios.services.JobService;

//DONE: Add documentation for class and methods
/**
 * CronJob: This class represents a scheduled job that extends QuartzJobBean and implements InterruptableJob.
 * It is responsible for executing the job logic when triggered by the scheduler.
 */
public class CronJob extends QuartzJobBean implements InterruptableJob{
	

	@Autowired
	JobService jobService;

	@Autowired
    JobRepository jobRepository;

	@Autowired
    TriggerRepository triggerRepository;

	private static RestTemplate restTemplate ;


	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobKey key = jobExecutionContext.getJobDetail().getKey();
		
		if(restTemplate == null){
			restTemplate = new RestTemplate();
		}

		Job job = jobRepository.findJobByGroupAndJobName(key.getGroup(),key.getName());
	
		Trigger t = new Trigger();
		t.setEventDate(new Date());
		t.setJob(job);
		t.setViewed(false);
		this.triggerRepository.save(t);

	}

	

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		System.out.println("Stopping thread... ");
	}

}