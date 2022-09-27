package com.zagvladimir.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
public class StartupApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Context Is UP");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Context Is DOWN");
    }
}
