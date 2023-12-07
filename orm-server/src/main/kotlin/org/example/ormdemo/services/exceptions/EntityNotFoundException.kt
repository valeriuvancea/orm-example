package org.example.ormdemo.services.exceptions

import org.example.ormdemo.entities.BaseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class EntityNotFoundException(id: Long, entityClass: Class<out BaseEntity?>) :
    RuntimeException("Entity `${entityClass.simpleName}` with id `$id` not found!")