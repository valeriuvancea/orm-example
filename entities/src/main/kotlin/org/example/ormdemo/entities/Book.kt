package org.example.ormdemo.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.example.ormdemo.contracts.requests.CreateBookRequest

@Entity
data class Book(
    override val id: Long?,
    @Column(name = "title", nullable = false) var title: String
) : BaseEntity(id) {
    constructor(request: CreateBookRequest) : this(null, request.title)
}