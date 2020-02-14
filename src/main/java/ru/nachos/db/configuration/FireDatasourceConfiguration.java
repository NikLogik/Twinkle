package ru.nachos.db.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "ru.nachos.db.repository.fire",
entityManagerFactoryRef = "firesEntityManagerFactory",
transactionManagerRef = "firesTransactionManager")
public class FireDatasourceConfiguration {

    @Bean
    @ConfigurationProperties("app.datasource.fires")
    public DataSourceProperties firesDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.fires.configuration")
    public DataSource firesDataSource(){
        return firesDataSourceProperties().initializeDataSourceBuilder()
                    .type(HikariDataSource.class).build();
    }

    @Bean(name = "firesEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean firesEntityManagerFactory(EntityManagerFactoryBuilder builder){
        return builder.dataSource(firesDataSource())
                    .packages("ru.nachos.db.entities.fire")
                    .build();
    }

    @Bean
    public PlatformTransactionManager firesTransactionManager (
            final @Qualifier("firesEntityManagerFactory") LocalContainerEntityManagerFactoryBean
                firesEntityManagerFactory){
        return new JpaTransactionManager(firesEntityManagerFactory.getObject());
    }
}
