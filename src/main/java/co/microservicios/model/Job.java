package co.microservicios.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SCHEDULER_JOB")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job implements Serializable {

    private static final long serialVersionUID = -3009157732242241609L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_ID", length = 30)
    private long id;

    @Column(name = "JOB_NAME", length = 100)
    private String jobName;

    @Column(name = "GROUP_NAME", length = 100)
    private String groupName;

    @Column(name = "SERVICE", length = 500)
    private String service;

    /* Nombre del parametro */
    @Column(name = "CRON_EXPRESSION", length = 50)
    private String cronExpression;

    /* Valor del parametro */
    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    /* Valor del parametro */
    @Column(name = "STATUS")
    private Boolean status = Boolean.TRUE;

    @OneToMany(mappedBy = "job")
    private List<Trigger> triggers;

    
}