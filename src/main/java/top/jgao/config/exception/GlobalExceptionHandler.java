package top.jgao.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.jgao.basic.Result;
import top.jgao.basic.ResultCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Object illegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        recording(request, exception);
        return Result.result(ResultCode.RESULT__ERROR_2, exception.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public Object sqlException(SQLException exception, HttpServletRequest request) {
        recording(request, exception);
        return Result.result(ResultCode.RESULT__ERROR_1, "数据库错误");
    }

    @ExceptionHandler(NullPointerException.class)
    public Object nullPointerException(NullPointerException exception, HttpServletRequest request) {
        recording(request, exception);
        return Result.result(ResultCode.RESULT__ERROR_1, "空指针异常");
    }

    @ExceptionHandler(Throwable.class)
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Throwable exception) {
        recording(request, exception);
        return Result.result(ResultCode.RESULT__ERROR_1);
    }

    private void recording(HttpServletRequest request, Throwable exception) {
        String uri = request.getRequestURI();
        String addr = request.getRemoteAddr();
        log.error("IP地址:{},访问URI={},错误ERROR={}", addr, uri, exception);
    }
}