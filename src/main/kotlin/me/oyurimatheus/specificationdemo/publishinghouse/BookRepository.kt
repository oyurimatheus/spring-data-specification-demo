package me.oyurimatheus.specificationdemo.publishinghouse

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BookRepository: JpaRepository<Book, Long>, JpaSpecificationExecutor<Book>