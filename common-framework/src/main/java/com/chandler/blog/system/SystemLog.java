package com.chandler.blog.system;

import com.alibaba.fastjson.JSON;
import com.chandler.blog.annotation.LogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class SystemLog {

	// 切入点
	@Pointcut("@annotation(com.chandler.blog.annotation.LogAnnotation)")
	public void pt() {

	}

	@Around(value = "pt()")
	public void printLog(ProceedingJoinPoint pjp) throws Throwable {
		try {
			beforeProceed(pjp);
			Object result = pjp.proceed();
			afterProceed(result);
		} finally {
			endProceed();
		}

	}

	private void beforeProceed(ProceedingJoinPoint pjp) throws NoSuchMethodException {
		// 获取request对象
		HttpServletRequest reuqest =
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		MethodSignature signature = (MethodSignature) (pjp.getSignature());
		// 获取被增加的方法上的注解
		Method enhanceMethod = pjp.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
		LogAnnotation annotation = enhanceMethod.getAnnotation(LogAnnotation.class);

		log.info("=======Start=======");
		// 打印请求 URL
		log.info("URL            : {}", reuqest.getRequestURL());
		// 打印描述信息
		log.info("BusinessName   : {}", annotation.businessName());
		// 打印 Http method
		log.info("HTTP Method    : {}", reuqest.getMethod());
		// 打印调用 controller 的全路径以及执行方法
		log.info("Class Method   : {}.{}" ,signature.getDeclaringTypeName(),enhanceMethod.getName());
		// 打印请求的 IP
		log.info("IP             : {}", reuqest.getRemoteAddr());
		// 打印请求入参
		log.info("Request Args   : {}", JSON.toJSONString(signature.getParameterNames()));
	}

	private void afterProceed(Object result) {
		// 打印出参
		log.info("Response       : {}", JSON.toJSONString(result));
	}

	private void endProceed() {
		// 结束后换行
		log.info("=======End=======" + System.lineSeparator());
	}
}
