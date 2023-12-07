package org.example.ormdemo.services.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class BookNotFoundException(id: Long) : RuntimeException("Book with id `$id` not found!")