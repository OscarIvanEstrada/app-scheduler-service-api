package co.microservicios.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import co.microservicios.job.SimpleJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

public interface JobService {
	boolean scheduleOneTimeJob(String groupName, String jobName, Class<SimpleJob> jobClass, Date date);
	boolean scheduleCronJob(String groupName, String jobName, Class<? extends QuartzJobBean> jobClass, Date date, String cronExpression);
	

	boolean updateCronJob(String groupName, String jobName, Date date, String cronExpression);
	
	boolean unScheduleJob(String groupName, String jobName);
	boolean deleteJob(String groupName, String jobName);
	boolean pauseJob(String groupName, String jobName);
	boolean resumeJob(String groupName, String jobName);
	boolean startJobNow(String groupName, String jobName);
	boolean isJobRunning(String groupName, String jobName);
	List<Map<String, Object>> getAllJobs();
	List<Map<String, Object>> getAllJobsToFire();
	boolean isJobWithNamePresent(String groupName, String jobName);
	String getJobState(String groupName, String jobName);
	boolean stopJob(String groupName, String jobName);
}
