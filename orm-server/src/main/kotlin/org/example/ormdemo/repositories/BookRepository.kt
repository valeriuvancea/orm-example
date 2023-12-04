package org.example.ormdemo.repositories

import org.example.ormdemo.entities.Book
import org.jooq.generated.tables.Book.BOOK
import org.jooq.generated.tables.records.BookRecord
import org.springframework.stereotype.Repository

@Repository
class BookRepository: BaseRepository<Book, BookRecord>(BOOK)