package cm.moca.work.biblio.repositories

import cm.moca.work.biblio.entities.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {

    fun findByTitle(title: String): List<Book>
    fun findByAuthor(author: String): List<Book>
    fun findByPublisher(publisher: String): List<Book>

}
