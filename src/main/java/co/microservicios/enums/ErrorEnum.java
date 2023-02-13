package co.microservicios.enums;

import org.springframework.http.HttpStatus;

public enum ErrorEnum {
    TECHNICAL("001","Ocurrio un error inesperado",HttpStatus.INTERNAL_SERVER_ERROR),
    ;
	
	private String code;
	private String description;
	private HttpStatus httpCode;
	
	private ErrorEnum(String code, String description,HttpStatus httpCode) {
		this.code=code;
		this.description = description;
		this.httpCode = httpCode;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public HttpStatus getHttpCode() {
		return httpCode;
	}
	
	
	
}

