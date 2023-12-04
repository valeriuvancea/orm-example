package org.example.ormdemo.controllers

import org.example.ormdemo.controllers.contracts.requests.CreateBookRequest
import org.example.ormdemo.controllers.contracts.requests.UpdateBookRequest
import org.example.ormdemo.entities.Book
import org.example.ormdemo.services.BookService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/book")
class BookController(private val bookService: BookService) :
    BaseController<BookService, Book, CreateBookRequest, UpdateBookRequest>(bookService)