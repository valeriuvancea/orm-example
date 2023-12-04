package org.example.ormdemo.entities

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) open val id: Long?)