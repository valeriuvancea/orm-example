package org.example.ormdemo.services.exceptions

import org.example.ormdemo.entities.BaseEntity

class EntityNotFoundException(id: Long, entityClass: Class<out BaseEntity?>) :
    RuntimeException("Entity `${entityClass.simpleName}` with id `$id` not found!")