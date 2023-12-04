package org.example.ormdemo.services

import org.example.ormdemo.controllers.contracts.requests.CreatePersonRequest
import org.example.ormdemo.controllers.contracts.requests.UpdatePersonRequest
import org.example.ormdemo.entities.Person
import org.example.ormdemo.repositories.PersonBookRepository
import org.example.ormdemo.services.exceptions.PersonNotFoundException
import org.example.ormdemo.repositories.PersonRepository
import org.example.ormdemo.services.exceptions.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class PersonService(
    private val personRepository: PersonRepository,
    private val personBookRepository: PersonBookRepository,
): BaseService<PersonRepository, Person, CreatePersonRequest, UpdatePersonRequest>(personRepository) {

    override fun entityCreator(createRequest: CreatePersonRequest): Person {
        return Person(createRequest.name, createRequest.age)
    }

    override fun updateEntity(entity: Person, updateRequest: UpdatePersonRequest) {
        entity.name = updateRequest.name
        entity.age = updateRequest.age
    }

    override fun getById(id: Long): Person {
        return personRepository.getByIdWithBooks(id) ?: throw EntityNotFoundException(id, Person::class.java)
    }

    fun getByName(name: String): Person {
        return personRepository.getByName(name) ?: throw PersonNotFoundException(name = name)
    }

    fun getPersonsWithNumberOfBooks(): Map<Person, Long?> {
        return personRepository.getPersonsWithNumberOfBooks() ?: emptyMap()
    }

    override fun delete(id: Long) {
        super.delete(id)
        personBookRepository.deleteAllRelationshipsWithPersonId(id)
    }
}