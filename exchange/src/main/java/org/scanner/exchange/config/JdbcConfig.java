package org.scanner.exchange.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcConfig {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String name;
    @Value("${spring.datasource.password}")
    private String pass;

    @Bean
    public HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        try {
            config.setJdbcUrl(url);
            config.setUsername(name);
            config.setPassword(pass);
            config.setMaximumPoolSize(4);
            config.setMinimumIdle(4);
            config.addDataSourceProperty("oracle.jdbc.defaultConnectionValidation", "LOCAL");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new HikariDataSource(config);
    }
    @Bean
    public JdbcTemplate jdbc(HikariDataSource ds) {
        return new JdbcTemplate(ds);
    }

}
