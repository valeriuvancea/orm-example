package org.example.ormdemo.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Transient

@Entity
data class Person(
    @Column(name = "name", nullable = false) var name: String,
    @Column(name = "age", nullable = false) var age: Int,
    @Transient var books: List<Book>? = null,
    override val id: Long? = null,
) : BaseEntity(id)