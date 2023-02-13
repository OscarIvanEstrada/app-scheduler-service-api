package co.microservicios.exceptions;

import co.microservicios.enums.ErrorEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiException extends RuntimeException{

	 private ErrorEnum error;
     private String message;


}

