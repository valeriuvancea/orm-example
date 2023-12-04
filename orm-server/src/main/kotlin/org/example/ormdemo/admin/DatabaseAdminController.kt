package org.example.ormdemo.admin

import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.stereotype.Component

@Component
@Endpoint(id="database")
class DatabaseAdminController {

    @ReadOperation
    fun test(): String {
        return "test"
    }
}