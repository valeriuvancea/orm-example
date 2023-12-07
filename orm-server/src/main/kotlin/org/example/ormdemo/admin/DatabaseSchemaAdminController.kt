package org.example.ormdemo.admin

import org.example.ormdemo.admin.contracts.SchemaInformationType
import org.example.ormdemo.services.DatabaseManagementService
import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.boot.actuate.endpoint.annotation.Selector
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation
import org.springframework.stereotype.Component

@Component
@Endpoint(id = "database-schema")
class DatabaseSchemaAdminController(private val databaseManagementService: DatabaseManagementService) {

    @ReadOperation
    fun getDatabaseSchema(@Selector schemaInformationType: SchemaInformationType): String {
        return when(schemaInformationType) {
            SchemaInformationType.CREATE -> databaseManagementService.getCreateSchema()
            SchemaInformationType.UPDATE -> databaseManagementService.getUpdateSchema()
            SchemaInformationType.VALIDATE -> databaseManagementService.getValidateSchema()
        }
    }

    @WriteOperation
    fun writeUpdateDatabaseSchema(name: String, version: String): String {
        return databaseManagementService.writeUpdateSchema(name, version)
    }
}