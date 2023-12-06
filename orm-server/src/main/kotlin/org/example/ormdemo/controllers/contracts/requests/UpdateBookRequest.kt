package org.example.ormdemo.controllers.contracts.requests

data class UpdateBookRequest(override val id: Long, val personId: Long, val title: String, val releaseYear: Int) : UpdateRequest(id)