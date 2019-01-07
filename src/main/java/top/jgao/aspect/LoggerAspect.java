package top.jgao.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.jgao.annotation.MyLogger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 注解日志切面
 *
 * @author jiangao.xia.o
 */
@Aspect
@Component
@Slf4j
public class LoggerAspect {

    @Pointcut("@annotation(top.jgao.annotation.MyLogger)")
    public void monitorLog() {
    }

    @Around("monitorLog()")
    public Object log(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        long startTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();// 获取连接点的方法签名对象；
        Method method = signature.getMethod();
        String methodName = signature.getName();
        Object controller = joinPoint.getTarget();// 获取连接点所在的目标对象；
        String controllerName = controller.getClass().getName();
        String args = getArgs(joinPoint, signature, method, controller);// 参数值
        MyLogger myLogger = method.getAnnotation(MyLogger.class);// 获取注解
        String message = myLogger.value();// 注解上的值
        log.info(controllerName + "-" + methodName + message + "参数" + args);
        try {
            obj = joinPoint.proceed();
            if (myLogger.isInsertTable()) {
                // 插入表
                if ("ADD".equals(String.valueOf(myLogger.type()))) {
                    //新增存表
                } else if ("UPDATE".equals(String.valueOf(myLogger.type()))) {
                    //更新存表
                } else if ("DELETE".equals(String.valueOf(myLogger.type()))) {
                    //删除存表
                }
            }
            long endTime = System.currentTimeMillis();
            log.info(controllerName + "-" + methodName + message + "成功,耗时time={" + (endTime - startTime)
                    + "毫秒},返回值result=" + JSON.toJSONString(obj));
        } catch (RuntimeException e) {
            log.info(controllerName + "-" + methodName + message + "失败,错误信息error={" + e.getMessage() + "}");
            insertErrorLog(methodName, controllerName, args, message, e);
            throw e;
        } catch (Throwable throwable) {
            log.error("日志切面抓取到Throwable，throwable={}", throwable.getMessage());
            throwable.printStackTrace();
        }
        return obj;
    }

    @Async
    public void insertErrorLog(String methodName, String controllerName, String args, String message, RuntimeException e) {
        log.info("插入错误日志表");
    }


    /**
     * 获取方法上的参数与值
     */
    private String getArgs(ProceedingJoinPoint joinPoint, MethodSignature signature, Method method, Object action) {
        String json = null;
        try {
            Object[] args = joinPoint.getArgs();//参数
            String[] parameterNames = signature.getParameterNames();
            Map<String, Object> map = new HashMap<>();
            if (args != null && parameterNames != null && args.length == parameterNames.length) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof HttpServletRequest) {
                        HttpServletRequest request = (HttpServletRequest) args[i];
                        if ("application/json".equals(request.getHeader("Content-Type"))) {
                            map.put(parameterNames[i] + "=", getRequestPayload(request));
                        } else {
                            Map<String, String> argMap = new HashMap<>();
                            Enumeration<String> enu = request.getParameterNames();
                            while (enu.hasMoreElements()) {
                                String paramName = enu.nextElement();
                                String value = request.getParameter(paramName);
                                if (value != null) {
                                    try {
                                        value = URLDecoder.decode(value, StandardCharsets.UTF_8.name());
                                    } catch (UnsupportedEncodingException e) {
                                    }
                                }
                                argMap.put(paramName, value);
                            }
                            map.put(parameterNames[i] + "=", argMap);
                        }
                    } else {
                        map.put(parameterNames[i] + "=", args[i]);
                    }
                }
                // 将特殊字符过滤
                json = JSON.toJSONString(map);
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(json);
                json = m.replaceAll("");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    private String getRequestPayload(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
