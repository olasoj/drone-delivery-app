package com.example.dronedeliveryapp.config.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.SharedCacheMode;
import org.flywaydb.core.Flyway;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware", setDates = false, modifyOnCreate = false)
@EnableJpaRepositories(basePackages = "com.example.dronedeliveryapp.*", transactionManagerRef = "txManager", queryLookupStrategy = QueryLookupStrategy.Key.USE_DECLARED_QUERY)
public class PersistenceConfig {

    @Primary
    @Bean(name = "entityManagerFactory")
    @DependsOn("droneDeliveryAppSqlDataSource")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        var emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);

        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        emf.setPackagesToScan("com.example.dronedeliveryapp.*");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaProperties(hibernateProperties());
        emf.setJpaDialect(new HibernateJpaDialect());

        emf.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        return emf;
    }

    @Primary
    @Bean(name = "droneDeliveryAppSqlDataSource", destroyMethod = "close")
    @ConfigurationProperties(prefix = "app.datasource.main")
    public HikariDataSource sqlDataSource() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://postgres:5432/drone_delivery_app?useEncoding=true&amp;characterEncoding=UTF-8;stringtype=unspecified");
        config.setUsername("olasoj");
        config.setPassword("P@ssw0rd");

        config.setAutoCommit(false);
        config.setConnectionTimeout(Duration.ofSeconds(5).toMillis());
        config.setMaximumPoolSize(Runtime.getRuntime().availableProcessors());
        config.setTransactionIsolation("TRANSACTION_READ_COMMITTED");

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");

        return new HikariDataSource(config);
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("jakarta.persistence.validation.mode", "none");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        properties.setProperty("hibernate.format_sql", "true");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.jdbc.batch_size", "30");
        properties.setProperty("hibernate.connection.provider_disables_autocommit", "true");
        return properties;
    }

    @Bean(name = "txManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @FlywayDataSource
    @Bean(initMethod = "migrate")
    public Flyway flyway(@Qualifier("droneDeliveryAppSqlDataSource") HikariDataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration") // this path is default
                .load();
    }

}
