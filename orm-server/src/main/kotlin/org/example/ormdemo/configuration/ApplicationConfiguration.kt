package org.example.ormdemo.configuration

import org.example.ormdemo.entities.BaseEntity
import org.example.ormdemo.repositories.PersonRepository
import org.hibernate.cfg.AvailableSettings
import org.hibernate.dialect.PostgreSQL95Dialect
import org.postgresql.Driver
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
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
import java.util.*
import javax.sql.DataSource


@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
class ApplicationConfiguration {
    @Bean
    fun dataSource(
        @Qualifier("databaseDriverName") databaseDriverName: String
    ): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(databaseDriverName)
        dataSource.username = "admin"
        dataSource.password = "admin"
        dataSource.url = "jdbc:postgresql://localhost:5432/db?createDatabaseIfNotExist=true"
        return dataSource
    }

    @Bean
    fun entityManagerFactory(
        dataSource: DataSource,
        @Qualifier("hibernateProperties") hibernateProperties: Properties,
        @Qualifier("packageToScaForEntities") packageToScaForEntities: Array<String>,
    ): LocalContainerEntityManagerFactoryBean {
        val localContainerEntityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        localContainerEntityManagerFactoryBean.dataSource = dataSource
        localContainerEntityManagerFactoryBean.setPackagesToScan(*packageToScaForEntities)
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
        properties.setProperty(AvailableSettings.DIALECT, PostgreSQL95Dialect::class.java.name)
        return properties
    }

    @Bean("databaseDriverName")
    fun databaseDriverName(): String {
        return Driver::class.java.name
    }

    @Bean("packageToScaForEntities")
    fun packageToScaForEntities(): Array<String> {
        return arrayOf(BaseEntity::class.java.packageName)
    }

    @Bean
    fun personRepository(): PersonRepository {
        return PersonRepository()
    }

    // disable automatic flyway migration
    @Bean
    fun flywayMigrationStrategy(): FlywayMigrationStrategy {
        return FlywayMigrationStrategy { _ -> }
    }
}