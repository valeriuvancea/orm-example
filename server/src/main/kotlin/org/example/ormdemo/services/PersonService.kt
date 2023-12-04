package org.example.ormdemo.services

import org.example.ormdemo.contracts.requests.CreatePersonRequest
import org.example.ormdemo.contracts.requests.UpdatePersonRequest
import org.example.ormdemo.entities.Person
import org.example.ormdemo.exceptions.EntityNotFoundException
import org.example.ormdemo.repositories.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {

    fun getById(id: Long): Person {
        return personRepository.getById(id) ?: throw EntityNotFoundException()
    }

    fun getByName(name: String): Person {
        return personRepository.getByName(name) ?: throw EntityNotFoundException()
    }

    fun create(createPersonRequest: CreatePersonRequest): Person {
        return personRepository.save(Person(createPersonRequest))
    }

    fun update(updatePersonRequest: UpdatePersonRequest): Person {
        val entity = getById(updatePersonRequest.id)
        entity.update(updatePersonRequest)
        return personRepository.save(entity)
    }

    fun getPersonsWithNumberOfBooks(): Map<Person, Long?> {
        return personRepository.getPersonsWithNumberOfBooks() ?: emptyMap()
    }
}