package me.oyurimatheus.specificationdemo.publishinghouse

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/search")
class FindEditionController(
    private val editionRepository: EditionRepository

) {

    @GetMapping
    fun find(
        @RequestParam("author-slug", defaultValue = "") authorSlugReqParam: String,
        @RequestParam("book", defaultValue = "") book: String,
        @RequestParam("edition", defaultValue = "") edition: String,
    ): ResponseEntity<*> {


        val specification = Specification<Edition> { root, query, criteriaBuilder ->
            // root is Edition entity


            val predicates = mutableListOf<Predicate>()

            if (authorSlugReqParam.isNotBlank()) {

                query.distinct(true)
                val author = query.from(Author::class.java)
                val editionAuthors = root.get<Set<Author>>("authors")

                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(author.get<String>("authorSlug"), authorSlugReqParam),
                                                   criteriaBuilder.isMember(author, editionAuthors)))
            }

            if (book.isNotBlank()) {
                val editionBook = root.join<Edition, Book>("book")

                val predicate = criteriaBuilder.equal(editionBook.get<String>("bookSlug"), book)
                predicates.add(predicate)
            }

            if (edition.isNotBlank()) {
                val predicate = criteriaBuilder.equal(root.get<Edition>("editionSlug"), edition)
                predicates.add(predicate)
            }

            return@Specification criteriaBuilder.and(*predicates.toTypedArray())
        }

        val editions = editionRepository.findAll(specification)

        return ResponseEntity.ok(editions)
    }
}
