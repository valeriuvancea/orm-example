package org.example.ormdemo.services.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class PersonNotFoundException(id: Long? = null, name: String? = null) : RuntimeException(
    if (id != null) "Person with id `$id` not found!" else "Person with name `$name` not found"
)