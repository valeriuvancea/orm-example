package org.example.ormdemo.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Transient
import org.example.ormdemo.contracts.requests.CreatePersonRequest
import org.example.ormdemo.contracts.requests.UpdatePersonRequest

@Entity
data class Person(
    override val id: Long?,
    @Column(name = "name", nullable = false) var name: String,
    @Column(name = "age") var age: Int,
    @Transient var books: List<Book>? = null
) : BaseEntity(id) {
    constructor(request: CreatePersonRequest) : this(null, request.name, request.age)

    fun update(request: UpdatePersonRequest) {
        name = request.name
        age = request.age
    }
}