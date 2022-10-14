package com.example.luck_project.common.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiSupport {

    /*
     * logger.info() : info 로그(info 파일에 write)
     * logger.error() : error 로그(info / error 파일에 write)
     */
    protected final Logger logger  = LogManager.getLogger("INFO");
    protected final Logger RLOGGER = LogManager.getLogger("RESULT");

}