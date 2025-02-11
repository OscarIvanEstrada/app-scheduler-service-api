package co.microservicios.business;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.microservicios.model.Job;
import co.microservicios.model.Trigger;
import co.microservicios.repository.JobRepository;
import co.microservicios.repository.TriggerRepository;
import co.microservicios.util.LoggerUtil;

//TODO: Add documentation to class and methods
@Component
public class ControllerBusiness {

	@Autowired
	LoggerUtil log;

	@Autowired
	JobRepository jobRepository;

	@Autowired
	TriggerRepository tRepository;



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
