package top.jgao.basic;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class ResultCode {

    @Sub(name = "Success")
    public static String RESULT_SUCCESS = "0000";

    @Sub(name = "System exception, please contact us")
    public static String RESULT__ERROR_1 = "0001";

    @Sub(name = "Illegal parameters")
    public static String RESULT__ERROR_2 = "0002";

    @Retention(RUNTIME)
    @Target({FIELD})
    public @interface Sub {
        public String name();
    }

    public static String getValueByKey(String key) {
        ResultCode mySub = new ResultCode();
        if (key == null || key.equals("")) {
            key = ResultCode.RESULT_SUCCESS;
        }
        String result = "Success";
        Field[] fields = mySub.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation ano = field.getAnnotation(Sub.class);
            try {
                if (key.equals(field.get(mySub).toString()) && ano != null) {
                    Sub sub = (Sub) ano;
                    result = (String) sub.name();
                    break;
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
            }
        }
        return result;
    }

}
