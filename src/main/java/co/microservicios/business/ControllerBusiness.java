package co.microservicios.business;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.microservicios.model.Job;
import co.microservicios.repository.JobRepository;
import co.microservicios.util.LoggerUtil;


@Component
public class ControllerBusiness {

	@Autowired
	LoggerUtil log;

	@Autowired
	JobRepository jobRepository;


	public Job findPendingJobByGroupAndJobName( String groupName, String jobName){
		return jobRepository.findPendingJobByGroupAndJobName(groupName, jobName);
	}


}
