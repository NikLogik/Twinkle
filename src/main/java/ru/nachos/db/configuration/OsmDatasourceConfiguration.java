package ru.nachos.db.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.nachos.db.entities.osm.PolygonOsmModel;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.nachos.db.repository.osm",
entityManagerFactoryRef = "osmEntityManagerFactory",
transactionManagerRef = "osmTransactionManager")
public class OsmDatasourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.osm")
    public DataSourceProperties osmDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.osm.configuration")
    public DataSource osmDataSource(){
        return osmDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "osmEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean osmEntityManagerFactory(EntityManagerFactoryBuilder builder){
        return builder.dataSource(osmDataSource())
                .packages(PolygonOsmModel.class)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager osmTransactionManager(
            final @Qualifier("osmEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean osmEntityManagerFactory){
        return new JpaTransactionManager(osmEntityManagerFactory.getObject());
    }
}
