package org.example.ormdemo.services.exceptions

class BookNotFoundException(id: Long) : RuntimeException("Book with id `$id` not found!")