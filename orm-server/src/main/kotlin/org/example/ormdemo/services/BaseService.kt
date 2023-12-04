package org.example.ormdemo.services

import jakarta.persistence.Entity
import org.example.ormdemo.controllers.contracts.requests.CreateRequest
import org.example.ormdemo.controllers.contracts.requests.UpdateRequest
import org.example.ormdemo.entities.BaseEntity
import org.example.ormdemo.repositories.BaseRepository
import org.example.ormdemo.services.exceptions.EntityNotFoundException
import java.lang.reflect.ParameterizedType


abstract class BaseService<
        R : BaseRepository<E, *>,
        E : BaseEntity,
        C : CreateRequest,
        U : UpdateRequest
        >(
    private val repository: R
) {

    protected abstract fun entityCreator(createRequest: C): E
    protected abstract fun updateEntity(entity: E, updateRequest: U)

    private fun getEntityClass(): Class<E> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<E>
    }

    open fun create(request: C): E {
        return repository.save(entityCreator(request))
    }

    open fun getById(id:Long): E {
        return repository.getById(id) ?: throw EntityNotFoundException(id, getEntityClass())
    }

    open fun update(request: U): E {
        val entity = getById(request.id)
        updateEntity(entity, request)
        return repository.save(entity)
    }

    open fun delete(id: Long) {
        val entity = getById(id)
        repository.delete(entity)
    }
}