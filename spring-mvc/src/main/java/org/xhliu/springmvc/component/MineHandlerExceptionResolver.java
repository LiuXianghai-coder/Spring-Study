package org.xhliu.springmvc.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MineHandlerExceptionResolver implements HandlerExceptionResolver {
    private final static Logger log = LoggerFactory.getLogger(MineHandlerExceptionResolver.class);

    @Override
    public ModelAndView resolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        log.debug("request={}", request.toString());
        log.debug("response={}", response.toString());

        response.setStatus(HttpStatus.ACCEPTED.value());
        try (PrintWriter writer = response.getWriter()) {
            writer.println("This is Handler Exception....");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
