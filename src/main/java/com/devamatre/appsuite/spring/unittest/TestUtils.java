package com.devamatre.appsuite.spring.unittest;


import com.devamatre.framework.core.BeanUtils;
import org.mockito.ArgumentMatcher;
import org.mockito.internal.matchers.InstanceOf;
import org.mockito.internal.progress.ThreadSafeMockingProgress;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Rohtash Lakra (rlakra)
 * @created 5/25/22 14:43 PM
 */
public enum TestUtils {
    INSTANCE;
    private static final String LOCAL_HOST = "http://localhost";

    /**
     * @param serverUrl
     * @param port
     * @param pathComponents
     * @return
     */
    public static String pathString(final String serverUrl, final int port, final String... pathComponents) {
        if (BeanUtils.isNotEmpty(pathComponents)) {
            return String.format("%s:%d/%s", serverUrl, port, BeanUtils.pathSegments(pathComponents));
        } else {
            return String.format("%s:%d", serverUrl, port);
        }
    }

    /**
     * @param port
     * @param pathComponents
     * @return
     */
    public static String pathString(final int port, final String... pathComponents) {
        return pathString(LOCAL_HOST, port, pathComponents);
    }

    /**
     * @param matcher
     */
    private static void reportMatcher(ArgumentMatcher<?> matcher) {
        ThreadSafeMockingProgress.mockingProgress().getArgumentMatcherStorage().reportMatcher(matcher);
    }

    /**
     * @return
     */
    public static BigDecimal anyBigDecimal() {
        reportMatcher(new InstanceOf(BigDecimal.class, "<any BigDecimal>"));
        return BigDecimal.valueOf(0.0);
    }

    /**
     * @return
     */
    public static BigInteger anyBigInteger() {
        reportMatcher(new InstanceOf(BigInteger.class, "<any BigInteger>"));
        return BigInteger.valueOf(0);
    }

}
