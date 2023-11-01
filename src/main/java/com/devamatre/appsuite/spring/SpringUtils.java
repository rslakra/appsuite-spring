package com.devamatre.appsuite.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.devamatre.appsuite.core.BeanUtils;
import com.devamatre.appsuite.spring.payload.dto.AbstractEntityDTO;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Rohtash Lakra
 * @created 4/19/23 6:33 PM
 */
public enum SpringUtils {
    INSTANCE;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Set<Integer> serverPorts = new HashSet<>();

    SpringUtils() {
        serverPorts.add(80);
        serverPorts.add(443);
    }

    public void addPort(int port) {
        INSTANCE.serverPorts.add(port);
    }

    /**
     * @param port
     * @return
     */
    public static boolean hasPort(int port) {
        return INSTANCE.serverPorts.contains(port);
    }

    /**
     * @param servletRequest
     * @return
     */
    public static String getRequestUrl(HttpServletRequest servletRequest) {
        return servletRequest.getScheme()
               + "://"
               + servletRequest.getServerName()
               + (hasPort(servletRequest.getServerPort()) ? BeanUtils.EMPTY_STR : ":" + servletRequest.getServerPort());
    }


    /**
     * @param principal
     * @param model
     * @param session
     * @return
     */
    public static boolean isLoggedIn(Principal principal, Model model, HttpSession session) {
        return (BeanUtils.isNotNull(principal) || BeanUtils.isNotNull(session.getAttribute("user")));
    }

    /**
     * Adds the session user to model.
     *
     * @param principal
     * @param model
     * @param session
     */
    public static void addUserToModel(Principal principal, Model model, HttpSession session) {
        final Object sessionUser = session.getAttribute("user");
        if (BeanUtils.isNotNull(sessionUser) && BeanUtils.isTypeOf(sessionUser, AbstractEntityDTO.class)) {
            AbstractEntityDTO user = (AbstractEntityDTO) sessionUser;
            model.addAttribute("message", "You have logged in successfully.");
            model.addAttribute("account", user);
        }
    }

    /**
     * @param object
     * @param id
     * @return
     */
    public static String editMessage(String object, Long id) {
        return String.format("Edit %s (#%d)", object, id);
    }

    private static class ErrorField {

        private String field;
        private String object;
        private String defaultMessage;
    }

    /**
     * @param allErrors
     * @return
     */
    public static final String toMessage(final List<ObjectError> allErrors) {
        String message = BeanUtils.EMPTY_STR;
        if (BeanUtils.isNotEmpty(allErrors)) {
            message = allErrors.stream()
                .map(error -> {
                    final ErrorField errorField = new ErrorField();
                    if (error instanceof FieldError) {
                        errorField.field = ((FieldError) error).getField();
                    } else {
                        errorField.object = error.getObjectName();
                    }
                    errorField.defaultMessage = error.getDefaultMessage();
                    try {
                        return MAPPER.writeValueAsString(errorField);
                    } catch (JsonProcessingException ex) {
                        return null;
                    }
                })
                .filter(entry -> entry != null)
                .collect(Collectors.joining(","));
        }

        return message;
    }
}
