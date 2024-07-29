package com.airfranceklm.fasttrack.assignment.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
  @Bean
  public @NonNull JPAQueryFactory queryFactory(
    @NonNull EntityManager entityManager
  ) {
    return new JPAQueryFactory(entityManager);
  }
}