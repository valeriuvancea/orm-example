package org.example.ormdemo.contracts.requests

import jakarta.validation.constraints.Min

data class CreatePersonRequest(val name: String, @Min(0) val age: Int)
