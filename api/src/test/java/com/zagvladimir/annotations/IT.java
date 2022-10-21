package com.zagvladimir.annotations;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Sql(
        value = {"classpath:sql/data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(
        value = {"classpath:sql/data_afterMethod.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public @interface IT {
}
