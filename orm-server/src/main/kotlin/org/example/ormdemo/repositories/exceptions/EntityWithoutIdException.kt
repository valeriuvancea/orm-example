package org.example.ormdemo.repositories.exceptions

import org.example.ormdemo.entities.BaseEntity

class EntityWithoutIdException(clazz: Class<BaseEntity>) :
    RuntimeException("Entity of type ${clazz.simpleName} does not have an id. Maybe it has not been saved yet")