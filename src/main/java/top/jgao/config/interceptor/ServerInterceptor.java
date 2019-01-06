package top.jgao.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.jgao.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ServerInterceptor implements HandlerInterceptor {

    public static final String REQUEST_ID_KEY = "requestId";
    public static ThreadLocal<String> requestIdThreadLocal = new ThreadLocal<String>();
    public static ThreadLocal<Long> timeThreadLocal = new ThreadLocal<Long>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        String requestId = getRequestId(request);
        MDC.put(REQUEST_ID_KEY, requestId);
        log.info("ip地址{},请求方式{},访问地址{},requestId= {}", ip, request.getMethod(), uri, requestId);
        timeThreadLocal.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long endTime = System.currentTimeMillis();
        long startTime = timeThreadLocal.get();
        long time = endTime - startTime;
        log.warn("method to detect timeout for {}, and the execution time is {} ms", request.getRequestURI(), time);
        if (time > 200) {
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // System.out.println(">>>MyInterceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet
        // 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

    public static String getRequestId(HttpServletRequest request) {
        String requestId = null;
        String parameterRequestId = request.getParameter(REQUEST_ID_KEY);
        String headerRequestId = request.getHeader(REQUEST_ID_KEY);
        if (parameterRequestId == null && headerRequestId == null) {
            log.debug("request parameter 和header 都没有requestId入参");
            requestId = RequestUtils.resolveReqId(request.getRemoteAddr());
        } else {
            requestId = parameterRequestId != null ? parameterRequestId : headerRequestId;
        }
        requestIdThreadLocal.set(requestId);
        return requestId;
    }
}