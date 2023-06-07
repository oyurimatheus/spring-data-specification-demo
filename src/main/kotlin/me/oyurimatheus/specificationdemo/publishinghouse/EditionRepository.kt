package me.oyurimatheus.specificationdemo.publishinghouse

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface EditionRepository : JpaRepository<Edition, Long>, JpaSpecificationExecutor<Edition> {

    object Specs {
        fun byAuthor(author: String): Specification<Edition> {
            return Specification<Edition> { root, query, builder ->
                val a = root.get<Set<Author>>("authors")
                    .get<String>("authorSlug")

                builder.equal(a, author)
            }
        }

        fun byBook(book: String): Specification<Edition> {
            return Specification<Edition> { root, query, builder ->
                val editionBook = root.join<Edition, Book>("book")
                builder.equal(editionBook.get<String>("bookSlug"), book)
            }
        }

        fun byEdition(edition: String): Specification<Edition> {
            return Specification<Edition> { root, query, builder ->
                builder.equal(root.get<String>("editionSlug"), edition)
            }
        }

        fun orderByEdition(spec: Specification<Edition>): Specification<Edition> {
            return Specification<Edition> { root, query, builder ->
                query.orderBy(builder.asc(root.get<String>("editionSlug")))
                spec.toPredicate(root, query, builder)
            }
        }
    }

}