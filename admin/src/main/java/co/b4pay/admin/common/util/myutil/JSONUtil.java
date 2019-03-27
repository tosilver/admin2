package co.b4pay.admin.common.util.myutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * json工具类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2018-02-08
 * Time: 9:25
 */
public class JSONUtil {

    /**
     * map集合转json字符串
     *
     * @param map
     * @return
     * @throws JsonProcessingException
     */
    public static String map2Json(Map map) throws JsonProcessingException {
        // 定义jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }
}
