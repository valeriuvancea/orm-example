package org.example.ormdemo.controllers.contracts.requests

data class CreateBookRequest(val personId: Long, val title: String): CreateRequest