package org.example.ormdemo.services

import org.example.ormdemo.entities.BaseEntity
import org.example.ormdemo.services.exceptions.MigrationFolderNotFoundException
import org.flywaydb.core.Flyway
import org.hibernate.boot.Metadata
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.AvailableSettings
import org.hibernate.tool.hbm2ddl.SchemaExport
import org.hibernate.tool.hbm2ddl.SchemaUpdate
import org.hibernate.tool.schema.TargetType
import org.reflections.Reflections
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Paths
import java.util.*
import javax.sql.DataSource


@Service
class DatabaseManagementService(
    @Qualifier("databaseDriverName") private val databaseDriverName: String,
    @Qualifier("hibernateProperties") private val hibernateProperties: Properties,
    @Qualifier("packageToScaForEntities") private val packageToScaForEntities: Array<String>,
    private val outputCaptureService: OutputCaptureService,
    private val dataSource: DataSource,
    private val flyway: Flyway,
) {
    private val metadata = getMetadata()

    fun getUpdateSchema(): String {
        val schemaUpdate = SchemaUpdate()
        schemaUpdate.setFormat(true)
        schemaUpdate.setDelimiter(";")
        return getSchemaOutput {
            schemaUpdate.execute(
                EnumSet.of(TargetType.STDOUT),
                metadata
            )
        }
    }

    fun writeUpdateSchema(name: String, version: String): String {
        val migrationsFolderUrl = javaClass.getClassLoader().getResource("db/migration")

        if (migrationsFolderUrl != null) {
            val migrationFolderPath = Paths.get(migrationsFolderUrl.toURI())
            var filename =
                "$migrationFolderPath/V$version" + "__${name.lowercase().replace(" ", "_")}.sql"
            filename =  filename.replace ("target/classes", "src/main/resources")
            val schemaUpdate = SchemaUpdate()
            schemaUpdate.setFormat(true)
            schemaUpdate.setDelimiter(";")
            schemaUpdate.setOutputFile(filename)
            return outputCaptureService.captureOutput {
                schemaUpdate.execute(
                    EnumSet.of(TargetType.SCRIPT),
                    metadata
                )
            }
        } else {
            throw MigrationFolderNotFoundException()
        }

    }

    fun getCreateSchema(): String {
        val schemaUpdate = SchemaExport()
        schemaUpdate.setFormat(true)
        schemaUpdate.setDelimiter(";")
        return getSchemaOutput {
            schemaUpdate.execute(
                EnumSet.of(TargetType.STDOUT),
                SchemaExport.Action.CREATE,
                metadata
            )
        }
    }

    private fun getSchemaOutput(schemaGeneratorMethod: () -> Unit): String {
        val result = outputCaptureService.captureOutput(schemaGeneratorMethod)
        val doubleNewLineCharacterIndex = result.indexOf(System.lineSeparator() + System.lineSeparator())
        if (doubleNewLineCharacterIndex != -1) {
            return result.substring(doubleNewLineCharacterIndex)
        }
        return ""

    }

    private fun getMetadata(): Metadata {
        val settings = hibernateProperties
        val driverManagerDataSource = dataSource as DriverManagerDataSource
        settings.setProperty(AvailableSettings.DRIVER, databaseDriverName)
        settings.setProperty(AvailableSettings.URL, driverManagerDataSource.url)
        settings.setProperty(AvailableSettings.USER, driverManagerDataSource.username)
        settings.setProperty(AvailableSettings.PASS, driverManagerDataSource.password)

        val metadata = MetadataSources(
            StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build()
        )

        val reflections = Reflections(packageToScaForEntities)
        val entitiesClasses = reflections.getSubTypesOf(BaseEntity::class.java)
        entitiesClasses.forEach { metadata.addAnnotatedClass(it) }

        return metadata.buildMetadata()
    }
}