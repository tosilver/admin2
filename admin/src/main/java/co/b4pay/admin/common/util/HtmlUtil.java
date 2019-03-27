package co.b4pay.admin.common.util;

import co.b4pay.admin.common.core.constants.Constants;
import com.alibaba.druid.support.json.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HtmlUtil {
    public static void renderText(HttpServletResponse response, String text) {
        render(response, "html/text", text);
    }

    public static void renderJson(HttpServletResponse response, String text) {
        render(response, "application/json", text);
    }

    public static void renderJson(HttpServletResponse response, Object object) {
        render(response, "application/json", JSONUtils.toJSONString(object));
    }

    public static void render(HttpServletResponse response, String contentType, String text) {
        try {
            response.setHeader("Pragma", "no-cache"); // HTTP/1.0 caches might not implement Cache-Control and might
            // only implement Pragma: no-cache
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            response.setContentType(contentType);
            response.setCharacterEncoding(Constants.CHARSET_UTF8);

            response.getWriter().write(text);
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
