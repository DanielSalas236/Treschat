package com.uniajc.treschat.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource


@Configuration
class DataSourceConfig {
    @Bean(name = ["DSBaseObject"])
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    fun primaryDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean("jdbcBaseObject")
    fun createJdbcTemplateLogin(@Autowired @Qualifier("DSBaseObject") dataSource: DataSource?): JdbcTemplate {
        return JdbcTemplate(dataSource!!)
    }
}