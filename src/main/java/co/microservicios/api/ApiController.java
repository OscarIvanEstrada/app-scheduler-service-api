package co.microservicios.api;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.microservicios.business.ControllerBusiness;
import co.microservicios.dto.JobRequestDTO;
import co.microservicios.dto.JobResponseDTO;
import co.microservicios.dto.ServerResponse;
import co.microservicios.job.CronJob;
import co.microservicios.model.Job;
import co.microservicios.repository.JobRepository;
import co.microservicios.services.JobService;
import co.microservicios.util.GSonUtils;
import co.microservicios.util.ServerResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Accion exitosa"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@Api(value = "app-scheduler-service-api ApiController", description = "scheduler",  tags = {"",""})
//DONE: Add documentations for class and methods	
/**
 *  ApiController: This controller exposes the API endpoints for managing scheduled jobs.
 *  It allows for adding, retrieving, starting, pausing, resuming, deleting, and updating jobs.
 */
public class ApiController {

    @Autowired
    ControllerBusiness controllerBusiness;

	@Value("${spring.application.version}")
	private String version;

	@GetMapping("version")
    /**
     * Returns the version of the application.
     * @return ResponseEntity<String> - The version of the application.
     */
    public ResponseEntity<String> version() {
        return new ResponseEntity(version, HttpStatus.OK);
    }

	@Autowired
    @Lazy
    JobService jobService;

    @Autowired
    JobRepository jobRepository;


    @RequestMapping(value= "/scheduler/addJob", method = RequestMethod.POST)
    /**
     * Adds a new job to the scheduler.
     * @param request JobRequestDTO - The job request containing job details.
     * @return JobResponseDTO - The response containing job details.
     */
    public JobResponseDTO addJob(@RequestBody JobRequestDTO request) {
      
        Job job = new Job();
        job.setCronExpression(request.getCronExpression());
        job.setGroupName(request.getGroupName());
        job.setService(request.getService());
        job.setJobName(request.getJobName());

        //0 0/1 * 1/1 * ? * cada minuto
        //Job Name is mandatory
        if(request.getJobName() == null || request.getJobName().trim().equals("")){
            return GSonUtils.toObject(request, JobResponseDTO.class);
        }

        //Job Name is mandatory
        if(request.getCronExpression() == null || request.getCronExpression().trim().equals("")){
            return GSonUtils.toObject(request, JobResponseDTO.class);
        }

        //Check if job Name is unique;
        if(!jobService.isJobWithNamePresent(request.getGroupName(), request.getJobName())) {

            boolean status =jobService.scheduleCronJob(request.getGroupName(),request.getJobName(), CronJob.class, new Date(), request.getCronExpression(),request.getDescription());

            if(status){
                jobRepository.save(job);
            }

            return GSonUtils.toObject(request, JobResponseDTO.class);

        }
        return GSonUtils.toObject(request, JobResponseDTO.class);
    }

    @RequestMapping(value= "/scheduler/getJobs", method = RequestMethod.GET)
    /**
     * Retrieves all scheduled jobs.
     * @return List<Map<String, Object>> - A list of maps containing job details.
     */
    public List<Map<String, Object>> getJobs(){
        List<Map<String, Object>> list = jobService.getAllJobs();
        return list;
    }

    @RequestMapping(value= "/scheduler/getJobsToFire", method = RequestMethod.GET)
    /**
     * Retrieves jobs that are pending to be fired.
     * @param groupName String - The group name of the job.
     * @param jobName String - The name of the job.
     * @return JobResponseDTO - The response containing job details.
     */
    public JobResponseDTO getJobsToFire(@RequestParam("groupName") String groupName, @RequestParam("jobName") String jobName){
        Job job =controllerBusiness.findPendingJobByGroupAndJobName(groupName, jobName);
        return GSonUtils.toObject(job, JobResponseDTO.class);
    }



    /* To refactor */


    @RequestMapping(value= "/scheduler/startJob", method = RequestMethod.GET)
    /**
     * Starts a job.
     * @param groupName String - The group name of the job.
     * @param jobName String - The name of the job.
     * @return ServerResponse - The server response indicating the status of the operation.
     */
    public ServerResponse startJob(@RequestParam("groupName") String groupName, @RequestParam("jobName") String jobName) {

        //Job Name is mandatory
        if(jobName == null || jobName.trim().equals("")){
            return getServerResponse(ServerResponseCode.ERROR, false);
        }
        return getServerResponse(ServerResponseCode.SUCCESS, jobService.startJobNow(groupName,jobName));
    }

    @RequestMapping(value= "/scheduler/pauseJob", method = RequestMethod.GET)
    /**
     * Pauses a job.
     * @param groupName String - The group name of the job.
     * @param jobName String - The name of the job.
     * @return ServerResponse - The server response indicating the status of the operation.
     */
    public ServerResponse pauseJob(@RequestParam("groupName") String groupName, @RequestParam("jobName") String jobName) {

        //Job Name is mandatory
        if(jobName == null || jobName.trim().equals("")){
            return getServerResponse(ServerResponseCode.ERROR, false);
        }
        return getServerResponse(ServerResponseCode.SUCCESS, jobService.pauseJob(groupName,jobName));
    }

    @RequestMapping(value= "/scheduler/resumeJob", method = RequestMethod.GET)
    /**
     * Resumes a paused job.
     * @param groupName String - The group name of the job.
     * @param jobName String - The name of the job.
     * @return ServerResponse - The server response indicating the status of the operation.
     */
    public ServerResponse resumeJob(@RequestParam("groupName") String groupName, @RequestParam("jobName") String jobName) {
        //Job Name is mandatory
        if(jobName == null || jobName.trim().equals("")){
            return getServerResponse(ServerResponseCode.ERROR, false);
        }
        return getServerResponse(ServerResponseCode.SUCCESS, jobService.resumeJob(groupName,jobName));
    }


    @RequestMapping(value= "/scheduler/deleteJob", method = RequestMethod.GET)
    /**
     * Deletes a job.
     * @param groupName String - The group name of the job.
     * @param jobName String - The name of the job.
     * @return ServerResponse - The server response indicating the status of the operation.
     */
    public ServerResponse deleteJob(@RequestParam("groupName") String groupName, @RequestParam("jobName") String jobName) {
        //Job Name is mandatory
        if(jobName == null || jobName.trim().equals("")){
            return getServerResponse(ServerResponseCode.ERROR, false);
        }
        boolean status = jobService.deleteJob(groupName,jobName);
        if(status) {
            Job job = jobRepository.findJobByGroupAndJobName(groupName,jobName);
            job.setStatus(false);
            jobRepository.save(job);
        }else{
            return getServerResponse(ServerResponseCode.ERROR, status);
        }

        return getServerResponse(ServerResponseCode.SUCCESS, status);
    }

    @RequestMapping(value= "/scheduler/updateJob", method = RequestMethod.GET)
    /**
     * Updates a job.
     * @param groupName String - The group name of the job.
     * @param jobName String - The name of the job.
     * @param cronExpression String - The new cron expression for the job.
     * @param service String - The service associated with the job.
     * @return ServerResponse - The server response indicating the status of the operation.
     */
    public ServerResponse updateJob(@RequestParam("groupName") String groupName, @RequestParam("jobName") String jobName, @RequestParam("cronExpression") String cronExpression, @RequestParam("service") String service) {

        //Job Name is mandatory
        if(jobName == null || jobName.trim().equals("")){
            return getServerResponse(ServerResponseCode.ERROR, false);
        }

        //Job Name is mandatory
        if(cronExpression == null || cronExpression.trim().equals("")){
            return getServerResponse(ServerResponseCode.ERROR, false);
        }

        boolean status = jobService.updateCronJob(groupName,jobName, new Date(), cronExpression," ");

        if(status){
            Job job = jobRepository.findJobByGroupAndJobName(groupName,jobName);
            job.setCronExpression(cronExpression);
            job.setService(service);
            jobRepository.save(job);

        }else {
            return getServerResponse(ServerResponseCode.ERROR, status);
        }
        return getServerResponse(ServerResponseCode.SUCCESS, status);
    }



    @RequestMapping(value= "/scheduler/jobState", method = RequestMethod.GET)
    /**
     * Retrieves the state of a job.
     * @param groupName String - The group name of the job.
     * @param jobName String - The name of the job.
     * @return ServerResponse - The server response containing the job state.
     */
    public ServerResponse jobState(@RequestParam("groupName") String groupName, @RequestParam("jobName") String jobName) {

        //Job Name is mandatory
        if(jobName == null || jobName.trim().equals("")){
            return getServerResponse(ServerResponseCode.ERROR, "nombre requerido");
        }
        return getServerResponse(ServerResponseCode.SUCCESS, jobService.getJobState(groupName,jobName));
    }

    @RequestMapping(value= "/scheduler/checkJobName", method = RequestMethod.GET)
    /**
     * Checks if a job with the given name exists.
     * @param groupName String - The group name of the job.
     * @param jobName String - The name of the job.
     * @return ServerResponse - The server response indicating whether the job exists.
     */
    public ServerResponse checkJobName(@RequestParam("groupName") String groupName, @RequestParam("jobName") String jobName){
        //Job Name is mandatory
        if(jobName == null || jobName.trim().equals("")){
            return getServerResponse(ServerResponseCode.JOB_NAME_NOT_PRESENT, false);
        }
        boolean status = jobService.isJobWithNamePresent(groupName,jobName);
        return getServerResponse(ServerResponseCode.SUCCESS, status);
    }

    @RequestMapping(value= "/scheduler/isJobRunning", method = RequestMethod.GET)
    /**
     * Checks if a job is currently running.
     * @param groupName String - The group name of the job.
     * @param jobName String - The name of the job.
     * @return ServerResponse - The server response indicating whether the job is running.
     */
    public ServerResponse isJobRunning(@RequestParam("groupName") String groupName, @RequestParam("jobName") String jobName) {
        boolean status = jobService.isJobRunning(groupName,jobName);
        return getServerResponse(ServerResponseCode.SUCCESS, status);
    }



    @RequestMapping(value= "/scheduler/stopJob", method = RequestMethod.GET)
    /**
     * Stops a running job.
     * @param groupName String - The group name of the job.
     * @param jobName String - The name of the job.
     * @return ServerResponse - The server response indicating the status of the operation.
     */
    public ServerResponse stopJob(@RequestParam("groupName") String groupName, @RequestParam("jobName") String jobName) {
        //Job Name is mandatory
        if(jobName == null || jobName.trim().equals("")){
            return getServerResponse(ServerResponseCode.ERROR, false);
        }
        return getServerResponse(ServerResponseCode.SUCCESS, jobService.stopJob(groupName,jobName));
    }

   
    /**
     * Creates a server response with the given response code and data.
     * @param responseCode int - The response code.
     * @param data Object - The data to be included in the response.
     * @return ServerResponse - The server response object.
     */
    public ServerResponse getServerResponse(int responseCode, Object data){
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setStatusCode(responseCode);
        serverResponse.setData(data);
        return serverResponse;
    }



}
