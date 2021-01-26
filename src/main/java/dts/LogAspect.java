package dts;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
	private Log log = LogFactory.getLog(LogAspect.class);

	// weave this advice for every method with the annotation @LogMe
//	@Before("@annotation(demo.LogMe)")
	public void logAdvice (JoinPoint joinPoint) {
		// join point meta data - name of invoked method
		String methodName = joinPoint.getSignature().getName();
		
		// join point meta data - class name of invoked method
		Object objectWeavedWithAspect = joinPoint.getTarget();
		String className = objectWeavedWithAspect.getClass().getName();
		

		// using Java Reflection to obtain LogMe annotation data 
		Signature methodSignature = joinPoint.getSignature();
		MethodSignature specificMethodSignature = (MethodSignature) methodSignature;
		// java.lang.reflect
		Method method = specificMethodSignature.getMethod();
		LogMe logMeAnnotation = method.getAnnotation(LogMe.class);
		
		// LogMe specific metadata
		String prefix = logMeAnnotation.prefix();
		boolean doLog = logMeAnnotation.doLog();
		
//		System.err.println("***** " + methodName + "() invoked");

		if (doLog) {
			// log message contains a prefix and then the method and classname
			this.log.debug("***** " + prefix + className + "::" + methodName + "() invoked");
		}
	}

	// weave this advice for every method with the annotation @LogMe
	// advice proxy
	@Around("@annotation(dts.LogMe)")
	public Object enhancedLogAdvice (ProceedingJoinPoint joinPoint) throws Throwable{
		
		String methodName = joinPoint.getSignature().getName();
		Object objectWeavedWithAspect = joinPoint.getTarget();
		String className = objectWeavedWithAspect.getClass().getName();
		Signature methodSignature = joinPoint.getSignature();
		MethodSignature specificMethodSignature = (MethodSignature) methodSignature;
		Method method = specificMethodSignature.getMethod();
		LogMe logMeAnnotation = method.getAnnotation(LogMe.class);
		String prefix = logMeAnnotation.prefix();
		boolean doLog = logMeAnnotation.doLog();
		
		// Pre Processing
		if (doLog) {
			// log message contains a prefix and then the method and classname
			this.log.trace(prefix + className + "::" + methodName + "() - begin");
		}
	
		// invoke actual method
		try {
			Object rv = joinPoint.proceed();
			
			// Success Post Processing
			if (doLog) {
				// log message contains a prefix and then the method and classname
				this.log.debug(prefix + className + "::" + methodName + "() - successfully invoked");
			}
			
			return rv;
			
		} catch (Throwable e) {
			// Failure Post Processing
			if (doLog) {
				// log message contains a prefix and then the method and classname
				this.log.error(prefix + className + "::" + methodName + "() - invoked with errors");
			}
			
			throw e;
		}
	
	}
}
