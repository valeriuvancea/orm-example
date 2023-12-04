package org.example.ormdemo.controllers

import jakarta.validation.Valid
import org.example.ormdemo.contracts.requests.CreateBookRequest
import org.example.ormdemo.entities.Book
import org.example.ormdemo.services.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/book")
class BookController(private val bookService: BookService) {

    @GetMapping("/{id}")
    fun getPersonById(@PathVariable id: Long): Book {
        return bookService.getById(id)
    }

    @PutMapping
    fun createPerson(@Valid @RequestBody request: CreateBookRequest): Book {
        return bookService.create(request)
    }
}