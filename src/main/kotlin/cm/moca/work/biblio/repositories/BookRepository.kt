package cm.moca.work.biblio.repositories

import cm.moca.work.biblio.entities.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {

    fun findByTitleContainsAndAuthorContainsAndPublisherContains(title: String, author: String, publisher: String): List<Book>

}
