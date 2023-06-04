package me.oyurimatheus.specificationdemo.publishinghouse

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "author")
@Entity
class Author(
        @Id
        @GeneratedValue(strategy = IDENTITY)
        @Column(name = "id")
        val id: Long? = null,

        @Column(name = "name")
        val name: String,

        @Column(name = "author_slug")
        val authorSlug: String,
) {




    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Author

        if (name != other.name) return false
        return authorSlug == other.authorSlug
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + authorSlug.hashCode()
        return result
    }
}