package me.oyurimatheus.specificationdemo.publishinghouse

import jakarta.persistence.*

@Table(name = "books")
@Entity
class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Long? = null,

        @Column(name = "name")
        val name: String,

        @Column(name = "book_slug")
        val bookSlug: String,


        ) {}
