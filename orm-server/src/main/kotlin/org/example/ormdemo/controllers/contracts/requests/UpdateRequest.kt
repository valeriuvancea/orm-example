package org.example.ormdemo.controllers.contracts.requests

import jakarta.validation.constraints.Min

abstract class UpdateRequest(@Min(1) open val id: Long)
