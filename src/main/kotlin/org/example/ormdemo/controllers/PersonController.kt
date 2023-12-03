package org.example.ormdemo.controllers

import jakarta.validation.Valid
import org.example.ormdemo.controllers.contracts.requests.CreatePersonRequest
import org.example.ormdemo.controllers.contracts.requests.UpdatePersonRequest
import org.example.ormdemo.entities.Person
import org.example.ormdemo.repositories.PersonRepository
import org.example.ormdemo.services.PersonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController(private val personService: PersonService) {

    @GetMapping("/{id}")
    fun getPersonById(@PathVariable id: Long): Person {
        return personService.getById(id)
    }

    @GetMapping("/name/{name}")
    fun getPersonByName(@PathVariable name: String): Person {
        return personService.getByName(name)
    }

    @PutMapping
    fun createPerson(@Valid @RequestBody request: CreatePersonRequest): Person {
        return personService.create(request)
    }

    @PostMapping
    fun updatePerson(@Valid @RequestBody request: UpdatePersonRequest): Person {
        return personService.update(request)
    }

    @GetMapping
    fun getPersonsWithBooks(): Map<Person, Long?> {
        return personService.getPersonsWithNumberOfBooks()
    }
}