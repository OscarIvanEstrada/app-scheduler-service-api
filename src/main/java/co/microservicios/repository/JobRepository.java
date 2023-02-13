package co.microservicios.repository;



import co.microservicios.model.Job;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface JobRepository extends CrudRepository<Job, Long>{
    @Query("Select j From Job j LEFT JOIN FETCH j.triggers t where j.groupName = :groupName and j.jobName = :jobName and j.status = TRUE")
    public Job findJobByGroupAndJobName(@Param("groupName") String groupName,@Param("jobName") String jobName);

    @Query("Select j From Job j LEFT JOIN FETCH j.triggers t where j.groupName = :groupName and j.jobName = :jobName and j.status = TRUE and t.viewed = FALSE")
    public Job findPendingJobByGroupAndJobName(@Param("groupName") String groupName,@Param("jobName") String jobName);

} 