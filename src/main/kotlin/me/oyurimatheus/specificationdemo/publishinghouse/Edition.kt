package me.oyurimatheus.specificationdemo.publishinghouse

import jakarta.persistence.*

@Table(name = "editions")
@Entity
class Edition(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Long? = null,


        @Column(name = "edition_slug")
        val editionSlug: String,

        @ManyToOne
        @JoinColumn(name = "book_id")
        val book: Book,

        @ManyToMany
        @JoinTable(name = "editions_authors")
        val authors: Set<Author>
) {

        constructor(book: Book, editionNumber: Int, authors: Set<Author>) : this(
                editionSlug = book.bookSlug + "-" + editionNumber,
                book = book,
                authors = authors
        )
}

