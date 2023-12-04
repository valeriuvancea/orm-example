package org.example.ormdemo.services

import org.example.ormdemo.contracts.requests.CreateBookRequest
import org.example.ormdemo.entities.Book
import org.example.ormdemo.exceptions.EntityNotFoundException
import org.example.ormdemo.repositories.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {
    fun create(createBookRequest: CreateBookRequest): Book {
        return bookRepository.save(Book(createBookRequest))
    }

    fun getById(id: Long): Book {
        return bookRepository.getById(id) ?: throw EntityNotFoundException()
    }
}