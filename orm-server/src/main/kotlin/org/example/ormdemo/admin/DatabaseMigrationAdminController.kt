package org.example.ormdemo.admin

import org.example.ormdemo.services.OutputCaptureService
import org.flywaydb.core.Flyway
import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation
import org.springframework.stereotype.Component

@Component
@Endpoint(id = "database-migration")
class DatabaseMigrationAdminController(
    private val flyway: Flyway,
    private val outputCaptureService: OutputCaptureService,
) {

    @WriteOperation
    fun migrate(): String {
        return outputCaptureService.captureOutput { flyway.migrate() }
    }
}