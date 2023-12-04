package org.example.ormdemo.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity(name="person_book")
@Table(
    name="person_book",
    uniqueConstraints = [
        UniqueConstraint(name = "UniquePersonIdBookId", columnNames = ["person_id", "book_id"] )
    ]
)
data class PersonBook(
    override val id: Long?,
    @Column(name = "person_id", nullable = false) val personId: Long,
    @Column (name = "book_id", nullable = false) val bookId: Long
) : BaseEntity(id)
