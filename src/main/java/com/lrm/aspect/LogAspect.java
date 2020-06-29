package com.lrm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by limi on 2017/10/13.
 */
@Aspect
@Component
public class LogAspect {

    // 日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 第一个*表示返回值为任意，第二个*表示web包下的所有类
    // 第三个*表示类下的所有方法，(..)表示参数类型任意
    @Pointcut("execution(* com.lrm.web.*.*(..))")
    public void log() {}


    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        // 通过 RequestContextHolder 获取请求域 ServletRequestAttributes，再获取 request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取 url 和 ip
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();

        // JoinPoint 包含了调用的方法信息
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);

        logger.info("Request : {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
//        logger.info("--------doAfter--------");
    }

    // 定义切面返回值
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(Object result) {
        // result 即为 Controller 的 mapping 函数请求返回的结果
        logger.info("Result : {}", result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
