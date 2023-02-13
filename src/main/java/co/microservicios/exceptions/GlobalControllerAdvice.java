package co.microservicios.exceptions;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import co.microservicios.enums.ErrorEnum;
import co.microservicios.util.LoggerUtil;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GlobalControllerAdvice //extends ResponseEntityExceptionHandler
{


	@Autowired
	LoggerUtil log;
	
    @ExceptionHandler(Throwable.class) 
    public ResponseEntity<ApiException> problem(final Throwable e) {
    	
    	String message = "Ocurrio un error procesando su solicitud, contacte al administrador del sistema. Error causado por: "+e.getMessage()+" -> ";
    	if(e.getCause() != null) {
    		message += e.getCause().getMessage()+" -> ";
    	}
    	if(e.getStackTrace() != null && e.getStackTrace().length > 0) {
    		
    		for(StackTraceElement element : e.getStackTrace()) {
    			message += element +":"+ element+ " -> ";	
    		}
    	}
    	
    	List<Error> r = new ArrayList<>();		
		r.add(new Error(String.valueOf(ErrorEnum.TECHNICAL.getCode()), message));
		this.log.error("problem", r,"Error general");

		return new ResponseEntity(r, ErrorEnum.TECHNICAL.getHttpCode());
    }
    
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiException> handleApiException(ApiException ex) {
		String message = ex.getMessage();
		
		List<Error> r = new ArrayList<>();		
		r.add(new Error(ex.getError().getCode(), ex.getError().getDescription()));
		r.add(new Error(ex.getError().getCode(), message));
		this.log.error("handleApiException", ex.getMessage(),"Error general");

		return new ResponseEntity(r, ex.getError().getHttpCode());
    }
   
}


