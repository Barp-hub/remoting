package io.github.riwcwt.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    @Bean(value = "rss-datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.sqlite.JDBC")
                .url("jdbc:sqlite:rss.db")
                .build();
    }

    @Bean(value = "rss-jdbctemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier(value = "rss-datasource") DataSource dataSource) {
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);
        return template;
    }

}
