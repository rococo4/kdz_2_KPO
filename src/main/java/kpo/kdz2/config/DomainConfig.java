package kpo.kdz2.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("kpo.kdz2.domain")
@EnableJpaRepositories("kpo.kdz2.repos")
@EnableTransactionManagement
public class DomainConfig {
}
