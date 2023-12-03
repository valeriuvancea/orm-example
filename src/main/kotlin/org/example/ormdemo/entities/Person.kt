package org.example.ormdemo.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.example.ormdemo.controllers.contracts.requests.CreatePersonRequest
import org.example.ormdemo.controllers.contracts.requests.UpdatePersonRequest

@Entity
data class Person(
    override val id: Long?,
    @Column(name = "name", nullable = false) var name: String,
    @Column(name = "age") var age: Int
) : BaseEntity(id) {
    constructor(request: CreatePersonRequest) : this(null, request.name, request.age)

    fun update(request: UpdatePersonRequest) {
        name = request.name
        age = request.age
    }
}