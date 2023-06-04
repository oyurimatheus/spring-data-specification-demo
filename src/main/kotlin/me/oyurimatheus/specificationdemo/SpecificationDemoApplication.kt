package me.oyurimatheus.specificationdemo

import me.oyurimatheus.specificationdemo.publishinghouse.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpecificationDemoApplication {

	@Bean
	fun runner(
			authorRepository: AuthorRepository,
			bookRepository: BookRepository,
			editionRepository: EditionRepository,
	): CommandLineRunner {

		val authors = listOf(
					Author(name = "Jay Wengrow", authorSlug = "jay-wengrow"),
					Author(name = "Martin Kleppmann", authorSlug = "martin-keppmann"),
					Author(name = "Dimitri Fontaine", authorSlug = "dimitri-fontaine"),
				)

		val books = listOf(
				Book(name = "The art of PostgreSQL", bookSlug = "postgres"),
				Book(name = "Design Data-Intensive Applications", bookSlug = "ddia"),
				Book(name = "A Common-Sense Guide to Data Structures and Algorithms", bookSlug = "dsalg"),
		)

		val editions = listOf(
				Edition(books[0], 1, setOf(authors[2])),
				Edition(books[0], 2, setOf(authors[2])),
				Edition(books[1], 1, setOf(authors[1])),
				Edition(books[1], 2, setOf(authors[1])),
				Edition(books[2], 1, setOf(authors[0])),
				Edition(books[2], 2, setOf(authors[0])),

		)



		return CommandLineRunner {
			authorRepository.saveAll(authors)
			bookRepository.saveAll(books)
			editionRepository.saveAll(editions)
		}
	}
}

fun main(args: Array<String>) {
	runApplication<SpecificationDemoApplication>(*args)
}
