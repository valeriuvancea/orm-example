package org.example.ormdemo.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
data class Book(
    @Column(name = "title", nullable = false) var title: String,
    override val id: Long? = null,
) : BaseEntity(id)