package me.xhy.java.lang.materials.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuhuaiyu on 2017/2/7.
 */
public class Data {

    public static List<Object> getSomeData() {

        // <> 菱形符号 java7 new case , 自动类型判断
        List<Object> list = new ArrayList<Object>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(new Integer(100));
        list.add(new Integer(200));

        return list;
    }
}
