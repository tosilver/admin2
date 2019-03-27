package co.b4pay.admin.common.web;

import co.b4pay.admin.entity.base.Page;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author YK
 * @version $Id v 0.1 2017年03月29日 11:00 Exp $
 */
public class PageHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
    private static final String PAGE_KEY = "page";

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        boolean containsAttribute = mavContainer.containsAttribute(PAGE_KEY);
        if (!containsAttribute) {
            return;
        }
        Page page = (Page) mavContainer.getModel().get(PAGE_KEY);
        mavContainer.addAttribute("params", page.getParams());
    }
}
