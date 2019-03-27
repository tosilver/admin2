package co.b4pay.admin.common.core.cache;

import java.util.HashMap;
import java.util.Map;

public class DataCache {

    // 还款方式
    public static Map<String, Object> MAP_REPAYTYPE = new HashMap<String, Object>();
    // 省:key=id,value=name
    public static Map<String, String> MAP_AREA_PROVINCE = new HashMap<String, String>();
    // 市:key=pid,value=map
    public static Map<String, Map<String, String>> MAP_AREA_CITY = new HashMap<String, Map<String, String>>();
}
