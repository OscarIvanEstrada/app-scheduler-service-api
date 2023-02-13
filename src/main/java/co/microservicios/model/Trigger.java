package co.microservicios.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SCHEDULER_TRIGGER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trigger implements Serializable {

    private static final long serialVersionUID = -3009157722242241609L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRIGGER_ID", length = 30)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EVENT_DATE")
    private java.util.Date eventDate = new Date();

    @JoinColumn(name="JOB_ID")
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    private Job job;

    private Boolean viewed = Boolean.FALSE;
   
}