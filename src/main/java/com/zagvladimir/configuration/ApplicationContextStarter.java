package com.zagvladimir.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Import({ConnectionPoolConfig.class, DatabaseProperties.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebMvc
@ComponentScan("com.zagvladimir")
public class ApplicationContextStarter {
}
