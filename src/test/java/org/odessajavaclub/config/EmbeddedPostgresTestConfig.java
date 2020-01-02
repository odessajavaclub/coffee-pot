package org.odessajavaclub.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;

@TestConfiguration
public class EmbeddedPostgresTestConfig {

  @Bean
  @Primary
  public DataSource dataSource() throws IOException {
    String jdbcUrl = embeddedPostgres().getJdbcUrl("postgres", "postgres");
    jdbcUrl = jdbcUrl + "&stringtype=unspecified";
    return new DriverManagerDataSource(jdbcUrl);
  }

  @Bean(destroyMethod = "close")
  public EmbeddedPostgres embeddedPostgres() throws IOException {
    return EmbeddedPostgres.start();
  }
}
