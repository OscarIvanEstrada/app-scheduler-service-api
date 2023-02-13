package co.microservicios.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description = "Job")
public class JobRequestDTO {
  
    @ApiModelProperty(value = "Data Exmaple", required = true, example = "job")
    private String jobName;
    @ApiModelProperty(value = "Data Exmaple", required = true, example = "group")
    private String groupName;
    @ApiModelProperty(value = "Data Exmaple", required = true, example = "myService")
    private String service;
    @ApiModelProperty(value = "Data Exmaple", required = true, example = "0 0/1 * 1/1 * ? *")
    private String cronExpression;
    @ApiModelProperty(value = "Data Exmaple", required = true, example = "At 12:00:00pm every day")
    private String description;
}
