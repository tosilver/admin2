package co.b4pay.admin.common.web;

import co.b4pay.admin.common.util.HtmlUtil;
import co.b4pay.admin.common.util.JsonMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基础控制器类
 *
 * @author cc
 */
public abstract class BaseController {
    protected static final String MSG = "msg";
    protected static final String MSG_ERR = "errMsg";

    /**
     * 添加Model消息
     *
     * @param model
     * @param messages
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute(MSG, sb.toString());
    }

    /**
     * 添加Model消息
     *
     * @param model
     * @param messages
     */
    protected void addErrorMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute(MSG, sb.toString());
    }

    /**
     * 添加Flash消息
     *
     * @param redirectAttributes
     * @param messages
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute(MSG, sb.toString());
    }

    /**
     * 添加Flash消息
     *
     * @param redirectAttributes
     * @param messages
     */
    protected void addErrorMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute(MSG_ERR, sb.toString());
    }

    /**
     * 客户端返回字符串
     *
     * @param response
     * @param string
     * @return
     */
    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(string);
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 客户端返回JSON字符串
     *
     * @param response
     * @param object
     * @return
     */
    protected void renderJson(HttpServletResponse response, Object object) {
        HtmlUtil.render(response, "application/json", JsonMapper.toJsonString(object));
    }

    /**
     * 客户端返回TEXT字符串
     *
     * @param response
     * @param text
     * @return
     */
    protected void renderJson(HttpServletResponse response, String text) {
        HtmlUtil.render(response, "html/text", text);
    }

    /**
     * 获得用户远程地址
     */
    protected String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

}
