package com.example.demo.config;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Configuration class for setting up the SQLite database connection.
 *
 * This class provides a {@code DataSource} bean configured with properties
 * from the application environment. The properties include the database driver,
 * URL, username, and password.
 */
@Configuration
public class SqliteConfig {

    @Autowired
    private Environment env;

    /**
     * Creates and configures a {@code DataSource} bean.
     *
     * This method reads database connection properties from the environment and
     * configures a {@code DriverManagerDataSource} with these properties.
     *
     * @return A configured {@code DataSource} instance.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("driverClassName"));
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        return dataSource;
    }
}
