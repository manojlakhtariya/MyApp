package com.myapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myapp.AbstractContainerTest.ContainerResourceConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;

@ActiveProfiles("integration_test")
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ContainerResourceConfiguration.class)
@AutoConfigureMockMvc
public abstract class AbstractContainerTest {

  public static ObjectMapper objectMapper;
  private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7")
      .withDatabaseName("spring_app_db")
      .withExposedPorts(3306);


  static {
    mysql.start();
    System.setProperty("spring.datasource.username", mysql.getUsername());
    System.setProperty("spring.datasource.password", mysql.getPassword());
    System.setProperty("spring.datasource.url", mysql.getJdbcUrl());
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

  }


  @TestConfiguration
  static class ContainerResourceConfiguration {

//    @Bean
//    @Primary
//    public RedisConnectionFactory RedisConnectionFactory() {
//      return new LettuceConnectionFactory(redis.getContainerIpAddress(), redis.getFirstMappedPort());
//    }

  }

  public static String asJsonString(final Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
