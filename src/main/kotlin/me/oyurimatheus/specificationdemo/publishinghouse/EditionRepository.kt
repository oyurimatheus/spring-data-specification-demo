package me.oyurimatheus.specificationdemo.publishinghouse

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface EditionRepository : JpaRepository<Edition, Long>, JpaSpecificationExecutor<Edition> {

    object Specs {
        fun byAuthor(author: String): Specification<Edition> {
            return Specification<Edition> { root, _, builder ->
                val authors = root.join(Edition_.authors)
                builder.equal(authors.get(Author_.authorSlug), author)
            }
        }

        fun byBook(book: String): Specification<Edition> {
            return Specification<Edition> { root, _, builder ->
                val editionBook = root.join(Edition_.book)
                builder.equal(editionBook.get(Book_.bookSlug), book)
            }
        }

        fun byEdition(edition: String): Specification<Edition> {
            return Specification<Edition> { root, _, builder ->
                builder.equal(root.get(Edition_.editionSlug), edition)
            }
        }

        fun orderByEdition(spec: Specification<Edition>): Specification<Edition> {
            return Specification<Edition> { root, query, builder ->
                query.orderBy(builder.asc(root.get(Edition_.editionSlug)))
                spec.toPredicate(root, query, builder)
            }
        }
    }

}