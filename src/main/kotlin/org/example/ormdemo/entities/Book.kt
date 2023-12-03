package org.example.ormdemo.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.example.ormdemo.controllers.contracts.requests.CreateBookRequest

@Entity
data class Book(
    override val id: Long?,
    @Column(name = "person_id", nullable = false) var personId: Long,
    @Column(name = "title", nullable = false) var title: String
) : BaseEntity(id) {
    constructor(request: CreateBookRequest) : this(null, request.personId, request.title)
}