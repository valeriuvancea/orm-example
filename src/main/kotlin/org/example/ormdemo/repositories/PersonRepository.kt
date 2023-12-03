package org.example.ormdemo.repositories

import org.example.ormdemo.entities.Person
import org.jooq.generated.tables.Book.BOOK
import org.jooq.generated.tables.Person.PERSON
import org.jooq.generated.tables.records.PersonRecord
import org.jooq.impl.DSL.sum
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class PersonRepository : BaseRepository<Person, PersonRecord>(PERSON) {

    fun getByName(name: String): Person? {
        return query {
            it.select()
                .from(PERSON)
                .where(PERSON.NAME.eq(name))
                .fetch()
                .first()
                ?.into(Person::class.java)
        }
    }

    fun getPersonsWithNumberOfBooks(): Map<Person, Long?>? {
        return query {
            it.select(PERSON, sum(BOOK.ID))
                .from(PERSON)
                .leftJoin(BOOK)
                .on(PERSON.ID.eq(BOOK.PERSON_ID))
                .groupBy(PERSON.ID)
                .map {
                    it.component1().into(Person::class.java) to
                            it.component2()?.longValueExact() }
                .toMap()
        }
    }
}
