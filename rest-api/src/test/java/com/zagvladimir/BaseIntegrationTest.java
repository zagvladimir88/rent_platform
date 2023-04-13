package com.zagvladimir;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class BaseIntegrationTest {

  private static final PostgreSQLContainer<?> container =
      new PostgreSQLContainer<>("postgres:14.1");

  @BeforeAll
  static void ruContainer() {
    container.start();
  }

  @DynamicPropertySource
  static void postgresProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
  }
}
