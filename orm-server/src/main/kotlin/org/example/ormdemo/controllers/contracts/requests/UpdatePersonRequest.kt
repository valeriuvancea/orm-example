package org.example.ormdemo.controllers.contracts.requests

import jakarta.validation.constraints.Min

data class UpdatePersonRequest(override val id: Long, val name: String, @Min(0) val age: Int):
    UpdateRequest(id)