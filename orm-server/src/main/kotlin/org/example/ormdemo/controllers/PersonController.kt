package org.example.ormdemo.controllers

import org.example.ormdemo.controllers.contracts.requests.CreatePersonRequest
import org.example.ormdemo.controllers.contracts.requests.UpdatePersonRequest
import org.example.ormdemo.entities.Person
import org.example.ormdemo.services.PersonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController(private val personService: PersonService) :
    BaseController<PersonService, Person, CreatePersonRequest, UpdatePersonRequest>(personService) {

    override fun getById(@PathVariable id: Long): Person {
        return personService.getByIdWithBooks(id)
    }

    @GetMapping("/byName/{name}")
    fun getPersonByName(@PathVariable name: String): Person {
        return personService.getByName(name)
    }

    @GetMapping
    fun getPersonsWithNumberOfBooks(): Map<Person, Long?> {
        return personService.getPersonsWithNumberOfBooks()
    }
}