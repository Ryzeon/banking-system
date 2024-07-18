package me.ryzeon.bankingsystem.shared.infrastructure.persistence.jpa.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class for enabling JPA Auditing.
 * <p>
 * This class is annotated with {@code @Configuration} to indicate that it is a source of bean definitions.
 * The {@code @EnableJpaAuditing} annotation is used to enable JPA Auditing in the Spring application,
 * which allows for the automatic management of audit-related fields like createdOn and modifiedOn.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfiguration {
}