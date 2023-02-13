package co.microservicios.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.microservicios.anotation.LogRequestResponseApi;
import co.microservicios.util.LoggerUtil;

@Aspect
@Component
public class ApiAspect {

	@Autowired
	LoggerUtil log;

	/**
	 * Usala sobre el metodo de la siguiente forma "@LogExecutionTimeApi"
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(co.microservicios.anotation.LogExecutionTimeApi)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object proceed = joinPoint.proceed();
		
		long executionTime = System.currentTimeMillis() - start;

		log.info(joinPoint.getSignature().getName(), "tiempo de ejecución " + executionTime);
		return proceed;
	}
	
	
	/**
	 * Usala sobre el metodo de la siguiente forma 	"@LogRequestResponseApi(index = "getIntegrationdService")"
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(co.microservicios.anotation.LogRequestResponseApi)")
	public Object logRequestResponseApi(ProceedingJoinPoint joinPoint) throws Throwable {
		Object args = joinPoint.getArgs();
		Object proceed = joinPoint.proceed();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	    Method method = signature.getMethod();
	    LogRequestResponseApi myAnnotation = method.getAnnotation(LogRequestResponseApi.class);
		log.infoService(myAnnotation.index(), args,proceed, method.getName());
		return proceed;
	}
	
}

