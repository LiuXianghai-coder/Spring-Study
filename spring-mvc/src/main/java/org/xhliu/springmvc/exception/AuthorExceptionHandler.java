package org.xhliu.springmvc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xhliu
 * @create 2022-07-08-14:29
 **/
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AuthorExceptionHandler
        extends ResponseEntityExceptionHandler
        implements HandlerExceptionResolver {

    private final static Logger log = LoggerFactory.getLogger(AuthorExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        logger.info("receive runtime exception " + e.getMessage());
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        return null;
    }
}
