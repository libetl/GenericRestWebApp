package org.toilelibre.libe.restwebapp.ioc.webapp.argresolver;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RequestBodyPathAnnotationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument (final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory) throws Exception {
        final String body = RequestBodyReader.getRequestBody (webRequest);
        final String pathValue = parameter.getParameterAnnotation (RequestBodyPath.class).value ();

        final MediaType mediaType = MediaType.parseMediaType (webRequest.getHeader (HttpHeaders.CONTENT_TYPE));
        return ArgumentResolverAction.run (mediaType, body, pathValue, parameter.getParameterType ());
    }

    @Override
    public boolean supportsParameter (final MethodParameter parameter) {
        return parameter.hasParameterAnnotation (RequestBodyPath.class);
    }
}