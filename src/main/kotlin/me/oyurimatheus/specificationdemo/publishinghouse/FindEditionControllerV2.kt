package me.oyurimatheus.specificationdemo.publishinghouse

import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/search")
class FindEditionControllerV2(
    private val editionRepository: EditionRepository
) {

    @GetMapping("/v2")
    fun find(
        @RequestParam("author-slug", defaultValue = "") authorSlugReqParam: String,
        @RequestParam("book", defaultValue = "") book: String,
        @RequestParam("edition", defaultValue = "") edition: String,
    ): ResponseEntity<*> {
        var spec = Specification.where<Edition> { root, query, builder ->
            // making fetch to minimize lazy evaluation of response
            // this will result in only one query at the end
            root.fetch<Edition, Book>("book")
            root.fetch<Edition, Author>("authors")
            // including distinct to query
            query.distinct(true)
            builder.conjunction()
        }
        if (authorSlugReqParam.isNotBlank()) {
            spec = spec.and(EditionRepository.Specs.byAuthor(authorSlugReqParam))
        }
        if (book.isNotBlank()) {
            spec = spec.and(EditionRepository.Specs.byBook(book))
        }
        if (edition.isNotBlank()) {
            spec = spec.and(EditionRepository.Specs.byEdition(edition))
        }

        val editions = editionRepository.findAll(
            EditionRepository.Specs.orderByEdition(spec)
        )

        return ResponseEntity.ok(editions)
    }
}
