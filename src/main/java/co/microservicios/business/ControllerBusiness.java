package co.microservicios.business;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.microservicios.model.Job;
import co.microservicios.model.Trigger;
import co.microservicios.repository.JobRepository;
import co.microservicios.repository.TriggerRepository;
import co.microservicios.util.LoggerUtil;

/**
 * ControllerBusiness: This class provides business logic for managing jobs and triggers.
 * It includes methods for finding pending jobs and updating trigger statuses.
 */
@Component
public class ControllerBusiness {

	@Autowired
	LoggerUtil log;

	@Autowired
	JobRepository jobRepository;

	@Autowired
	TriggerRepository tRepository;



	 /**
     * Finds a pending job by its group name and job name.
     * A pending job is a job that has associated triggers that have not been viewed yet.
     * This method also marks the triggers associated with the job as viewed.
     *
     * @param groupName The group name of the job.
     * @param jobName   The name of the job.
     * @return The job if found, otherwise null.
     */
	public Job findPendingJobByGroupAndJobName( String groupName, String jobName){

		Job job = jobRepository.findPendingJobByGroupAndJobName(groupName, jobName); 
		if(job != null){
			for(Trigger t: job.getTriggers()){
				t.setViewed(true);
				tRepository.save(t);
			}
		}
		
		return job;
	}


}
