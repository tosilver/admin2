package co.b4pay.admin.common.web;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Iterator;

/**
 * @author YK
 * @version $Id v 0.1 2017年03月28日 14:58 Exp $
 */
public class PageArgumentResolver implements HandlerMethodArgumentResolver {
    // private static final String PARAMS_PREFIX = "params.";
    //private static final String[] PAGE_PARAM_KEYS = {"pageIndex", "pageSize", "orderBy"};
    private static final String PAGE_INDEX = "pageIndex";
    private static final String PAGE_SIZE = "pageSize";
    private static final String ORDER_BY = "orderBy";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PageAttribute.class) && parameter.getParameterType().isAssignableFrom(Page.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        Type type = parameter.getGenericParameterType();
//        if (type instanceof ParameterizedType) {
//            ParameterizedType parameterizedType = (ParameterizedType) type;
//            Type[] typeArguments = parameterizedType.getActualTypeArguments();
//            if (typeArguments.length == 0) {
//                throw new RuntimeException("注解@PageAttribute 缺少泛型参数");
//            }
//            Type typeArgument = typeArguments[0];
//            typeArgument.
//        }

        Page page = new Page();
        Iterator<String> parameterNames = webRequest.getParameterNames();
        while (parameterNames.hasNext()) {
            String parameterName = parameterNames.next();
            String parameterValue = webRequest.getParameter(parameterName);
            if (StringUtil.isBlank(parameterValue)) {
                continue;
            }
            if (PAGE_INDEX.equals(parameterName)) {
                page.setPageIndex(Integer.valueOf(parameterValue));
                continue;
            }
            if (PAGE_SIZE.equals(parameterName)) {
                page.setPageSize(Integer.valueOf(parameterValue));
                continue;
            }
            if (ORDER_BY.equals(parameterName)) {
                page.setOrderBy(parameterValue);
                continue;
            }
//            if (!parameterName.startsWith(PARAMS_PREFIX)) {
//                continue;
//            }
//            parameterName = parameterName.substring(PARAMS_PREFIX.length());
            if (page.getParams() == null) {
                page.setParams(Params.create());
            }
            page.getParams().put(parameterName, parameterValue);
        }
        return page;
    }
}
