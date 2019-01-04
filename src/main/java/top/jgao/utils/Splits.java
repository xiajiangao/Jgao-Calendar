package top.jgao.utils;

import org.apache.commons.lang3.StringUtils;

public class Splits {

    public static String getStr(String s) {
        String[] strAttr = s.split("\\d");//  \d 为正则表达式表示[0-9]数字
        // 返回单个String
        StringBuffer buffer = new StringBuffer();
        for (String string : strAttr) {
            if (StringUtils.isNotBlank(string)) {
                buffer.append(string);
            }
        }
        return buffer.toString();
    }

    public static int getNum(String s) {
        String[] strAttr = s.split("\\D");  // \D 为正则表达式表示非数字
//        String a = "";
//        for (String m : num_string) {
//            a += m;
//        }
//        String[] num = a.split("");  //将分离出的重新保存在新数组num中不要直接用num_string，  因为在正则表达式对字符串进行选择时若前面的几个字符不符合要求但num_string数组中仍会存有其位置 是空格
//        int[] inte = new int[num.length];
//        for (int i = 0; i < num.length; i++) {
//            inte[i] = Integer.parseInt(num[i]); //将该数组中的数字存入int数组
//        }
        // 返回单个数字
        StringBuffer buffer = new StringBuffer();
        for (String string : strAttr) {
            if (StringUtils.isNotBlank(string)) {
                buffer.append(string);
            }
        }
        return Integer.parseInt(buffer.toString());
    }

}
