package org.example.ormdemo.services

import org.example.ormdemo.controllers.contracts.requests.CreateBookRequest
import org.example.ormdemo.controllers.contracts.requests.UpdateBookRequest
import org.example.ormdemo.entities.Book
import org.example.ormdemo.entities.PersonBook
import org.example.ormdemo.repositories.BookRepository
import org.example.ormdemo.repositories.PersonBookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val personService: PersonService,
    private val personBookRepository: PersonBookRepository,
): BaseService<BookRepository, Book, CreateBookRequest, UpdateBookRequest>(bookRepository) {

    override fun entityCreator(createRequest: CreateBookRequest): Book {
        return Book(createRequest.title, createRequest.year)
    }

    override fun updateEntity(entity: Book, updateRequest: UpdateBookRequest) {
        entity.title = updateRequest.title
        entity.year = updateRequest.year
    }

    override fun create(createBookRequest: CreateBookRequest): Book {
        val person = personService.getById(createBookRequest.personId)
        val book = super.create(createBookRequest)
        personBookRepository.save(PersonBook(person.id, book.id))
        return book
    }

    override fun delete(id: Long) {
        super.delete(id)
        personBookRepository.deleteAllRelationshipsWithBookId(id)
    }
}