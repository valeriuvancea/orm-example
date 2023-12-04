package org.example.ormdemo.repositories

import org.example.ormdemo.entities.PersonBook
import org.jooq.generated.Tables.PERSON_BOOK
import org.jooq.generated.tables.records.PersonBookRecord
import org.springframework.stereotype.Repository

@Repository
class PersonBookRepository: BaseRepository<PersonBook, PersonBookRecord>(PERSON_BOOK) {
    fun deleteAllRelationshipsWithBookId(id: Long) {
        query {
            it
                .deleteFrom(PERSON_BOOK)
                .where(PERSON_BOOK.BOOK_ID.eq(id))
                .execute()
        }
    }

    fun deleteAllRelationshipsWithPersonId(id: Long) {
        query {
            it
                .deleteFrom(PERSON_BOOK)
                .where(PERSON_BOOK.PERSON_ID.eq(id))
                .execute()
        }
    }
}