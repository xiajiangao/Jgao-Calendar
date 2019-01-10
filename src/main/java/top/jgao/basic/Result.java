package top.jgao.basic;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = -5809782578272943999L;

    private String requestId;
    private String resultCode;
    private String resultDesc;
    private Long resultServerTime;
    private T resultData;

    public static Result successResult() {
        return result(ResultCode.RESULT_SUCCESS);
    }

    public static Result successResult(Object data) {
        Result result = successResult();
        result.setResultData(data);
        return result;
    }

    public static Result result(String resultCode) {
        return result(resultCode, ResultCode.getValueByKey(resultCode));
    }

    public static Result result(String resultCode, String desc) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setResultDesc(desc);
        return result;
    }

//    public static Result result(String resultCode, BindingResult bindingResult) {
//        List<ObjectError> allErrors = bindingResult.getAllErrors();
//        StringBuilder sb = new StringBuilder();
//        if (CollectionUtils.isNotEmpty(allErrors)) {
//            for (ObjectError oe : allErrors) {
//                sb.append(oe.getDefaultMessage()).append(";");
//            }
//        }
//        return result(resultCode, sb.substring(0, sb.length() - 1));
//    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public Long getResultServerTime() {
        return resultServerTime;
    }

    public void setResultServerTime(Long resultServerTime) {
        this.resultServerTime = resultServerTime;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}
