package org.example.ormdemo.controllers

import jakarta.validation.Valid
import org.example.ormdemo.controllers.contracts.requests.CreateRequest
import org.example.ormdemo.controllers.contracts.requests.UpdateRequest
import org.example.ormdemo.entities.BaseEntity
import org.example.ormdemo.services.BaseService
import org.springframework.web.bind.annotation.*

abstract class BaseController<
        S : BaseService<*, E, C, U>,
        E : BaseEntity,
        C : CreateRequest,
        U : UpdateRequest>(private val service: S) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): E {
        return service.getById(id)
    }

    @PutMapping
    fun create(@Valid @RequestBody request: C): E {
        return service.create(request)
    }

    @PostMapping
    fun update(@Valid @RequestBody request: U): E {
        return service.update(request)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }
}