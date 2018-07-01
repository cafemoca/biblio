package cm.moca.work.biblio.services

import au.com.console.jpaspecificationdsl.*
import cm.moca.work.biblio.entities.Book
import cm.moca.work.biblio.repositories.BookRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class BookService(val bookRepository: BookRepository) {

    @Transactional(readOnly = true)
    fun findById(id: Long): Book? {
        return bookRepository.findById(id).orElseGet(null)
    }

    @Transactional(readOnly = true)
    fun findByParams(title: String, author: String, publisher: String) : List<Book> {
        return bookRepository.findAll(and(Book::title.like("%$title%"), Book::author.like("%$author%"), Book::publisher.like("%$publisher%")))
    }

    @Transactional(readOnly = true)
    fun findAll(page: Pageable) : List<Book> {
        return bookRepository.findAll(page).content
    }

    @Transactional()
    fun save(book: Book) {
        bookRepository.save(book)
    }

    @Transactional()
    fun delete(id: Long) {
        bookRepository.deleteById(id)
    }

}
