package com.rslakra.appsuite.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rohtash Lakra
 * @created 1/27/23 19:13 PM
 */
public enum AopUtils {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(AopUtils.class);
    private static final String LOCAL_HOST_IPV4 = "127.0.0.1";
    private static final String LOCAL_HOST_IPV6 = "0:0:0:0:0:0:0:1";

    /**
     * @param joinPoint
     * @return
     */
    public static Method findMethod(final JoinPoint joinPoint) {
        LOGGER.debug("+findMethod({})", joinPoint);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final Method method = methodSignature.getMethod();
        LOGGER.debug("-findMethod(), method: {}", method);
        return method;
    }

    /**
     * Returns the annotation of the method.
     *
     * @param joinPoint
     * @param annotationClassType
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getAnnotationByClass(final JoinPoint joinPoint,
                                                                final Class<T> annotationClassType) {
        LOGGER.debug("+getAnnotationByClass({}, {})", joinPoint, annotationClassType);
        // check method has annotation or not
        final Method method = findMethod(joinPoint);
        LOGGER.info("Checking @%s annotation applied to method=%s", annotationClassType.getSimpleName(),
                    method.getName());
        Annotation annotation = method.getAnnotation(annotationClassType);
        // If an interface method doesn't have an annotation, then check an  implementation class
        if (Objects.isNull(annotation)) {
            // filter all methods of the same name
            final List<Method>
                declaredMethods =
                Arrays.stream(joinPoint.getTarget().getClass().getDeclaredMethods())
                    .filter(m -> m.getName().equals(method.getName()))
                    .collect(Collectors.toList());
            if (Objects.nonNull(declaredMethods)) {
                // then find the method that has annotation
                final Optional<Method> methodOptional = declaredMethods.stream()
                    .filter(declaredMethod -> declaredMethod.getAnnotation(annotationClassType) != null)
                    .findFirst();
                annotation =
                    methodOptional.isPresent() ? methodOptional.get().getAnnotation(annotationClassType) : null;
            }
        }

        LOGGER.debug("-getAnnotationByClass(), annotation: {}", annotation);
        return (T) annotation;
    }

    /**
     * @param servletRequest
     * @return
     */
    public static boolean isLocalHostRequest(HttpServletRequest servletRequest) {
        return (Objects.nonNull(servletRequest)
                && (LOCAL_HOST_IPV4.equals(servletRequest.getRemoteAddr())
                    || LOCAL_HOST_IPV6.equals(servletRequest.getRemoteAddr())));
    }

}
