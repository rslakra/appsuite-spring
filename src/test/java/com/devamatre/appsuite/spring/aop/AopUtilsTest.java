package com.devamatre.appsuite.spring.aop;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rohtash Lakra
 * @created 1/27/23 19:13 PM
 */
@ExtendWith(SpringExtension.class)
public class AopUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopUtils.class);

    @Test
    void contextLoads() {
    }

    @Test
    public void testFindMethod() {
        LOGGER.debug("+testFindMethod()");
        JoinPoint joinPointMock = Mockito.mock(JoinPoint.class);
        Signature mockSignature = Mockito.mock(Signature.class);
        Mockito.when(mockSignature.getName()).thenReturn("getAll");
        Mockito.when(joinPointMock.getSignature()).thenReturn(mockSignature);
//        Method method = AopUtils.findMethod(joinPointMock);
//        assertNotNull(method);
        LOGGER.debug("-testFindMethod()");
    }

    @Test
    public void testGetAnnotationByClass() {
        LOGGER.debug("+testGetAnnotationByClass()");
        LOGGER.debug("-testGetAnnotationByClass()");
    }

    @Test
    public void testIsLocalHostRequest() {
        LOGGER.debug("+testIsLocalHostRequest()");
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
//        assertTrue(AopUtils.isLocalHostRequest(mockRequest));
        LOGGER.debug("-testIsLocalHostRequest()");
    }

}
