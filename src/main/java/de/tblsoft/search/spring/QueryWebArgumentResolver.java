package de.tblsoft.search.spring;

import de.tblsoft.search.query.Query;
import de.tblsoft.search.query.SaqlParser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by tblsoft on 12.11.16.
 */
public class QueryWebArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return Query.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        SaqlParser saqlParser = new SaqlParser(nativeWebRequest.getParameterMap());
        Query query = saqlParser.getQuery();
        return query;
    }
}
