package org.example.ormdemo.repositories

import org.example.ormdemo.entities.Book
import org.example.ormdemo.entities.Person
import org.jooq.generated.tables.Book.BOOK
import org.jooq.generated.tables.Person.PERSON
import org.jooq.generated.tables.PersonBook.PERSON_BOOK
import org.jooq.generated.tables.records.PersonRecord
import org.jooq.impl.DSL.list
import org.jooq.impl.DSL.sum
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class PersonRepository : BaseRepository<Person, PersonRecord>(PERSON) {

    fun getByName(name: String): Person? {
        return query {
            it.selectFrom(PERSON)
                .where(PERSON.NAME.eq(name))
                .fetch()
                .first()
                ?.into(Person::class.java)
        }
    }

    fun getPersonsWithNumberOfBooks(): Map<Person, Long?>? {
        return query {
            it.select(PERSON, sum(PERSON_BOOK.BOOK_ID))
                .from(PERSON)
                .leftJoin(PERSON_BOOK)
                .on(PERSON.ID.eq(PERSON_BOOK.PERSON_ID))
                .groupBy(PERSON.ID)
                .map {
                    it.component1().into(Person::class.java) to
                            it.component2()?.longValueExact()
                }
                .toMap()
        }
    }

    fun getByIdWithBooks(id: Long): Person? {
        return query {
            it.select()
                .from(PERSON)
                .join(PERSON_BOOK)
                .on(PERSON.ID.eq(PERSON_BOOK.PERSON_ID))
                .leftJoin(BOOK)
                .on(BOOK.ID.eq(PERSON_BOOK.BOOK_ID))
                .where(PERSON.ID.eq(id))
                .fetchGroups(PERSON, BOOK)
                ?.map {
                    val person = it.component1().into(Person::class.java)
                    person.books = it.component2().map { it.into(Book::class.java) }
                    return@map person
                }?.firstOrNull()
        }
    }
}
