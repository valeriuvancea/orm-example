package org.example.ormdemo.configuration

import org.example.ormdemo.repositories.PersonRepository
import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
import org.hibernate.dialect.PostgreSQL95Dialect
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.Properties
import javax.sql.DataSource


@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
class ApplicationConfiguration {
    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.postgresql.Driver")
        dataSource.username = "admin"
        dataSource.password = "admin"
        dataSource.url = "jdbc:postgresql://localhost:5432/db?createDatabaseIfNotExist=true"
        return dataSource
    }

    @Bean
    fun entityManagerFactory(
        dataSource: DataSource,
        @Qualifier("hibernateProperties") hibernateProperties: Properties
    ): LocalContainerEntityManagerFactoryBean {
        val localContainerEntityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        localContainerEntityManagerFactoryBean.dataSource = dataSource
        localContainerEntityManagerFactoryBean.setPackagesToScan("org.example.ormdemo.entities")
        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        localContainerEntityManagerFactoryBean.jpaVendorAdapter = vendorAdapter
        localContainerEntityManagerFactoryBean.setJpaProperties(hibernateProperties)
        return localContainerEntityManagerFactoryBean
    }

    @Bean
    fun transactionManager(entityManagerFactory: LocalContainerEntityManagerFactoryBean): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory.getObject()
        return transactionManager
    }

    @Bean
    fun exceptionTranslation(): PersistenceExceptionTranslationPostProcessor {
        return PersistenceExceptionTranslationPostProcessor()
    }

    @Bean("hibernateProperties")
    fun hibernateProperties(): Properties {
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "update")
        properties.setProperty("hibernate.dialect", PostgreSQL95Dialect::class.java.name)
        properties.setProperty("hibernate.show_sql", "true")
        properties.setProperty("hibernate.format_sql", "true")
        properties.setProperty("hibernate.naming.physical-strategy", PhysicalNamingStrategyStandardImpl::class.java.name)
        return properties
    }

    @Bean
    fun personRepository(): PersonRepository {
        return PersonRepository()
    }
}