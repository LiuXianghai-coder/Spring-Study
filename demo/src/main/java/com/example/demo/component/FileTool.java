package com.example.demo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author xhliu
 **/
@Scope("prototype")
@Component("fileTool")
public class FileTool{

    private final static Logger log = LoggerFactory.getLogger(FileTool.class);
}
