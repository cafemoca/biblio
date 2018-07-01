package cm.moca.work.biblio.services

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
    fun findByTitleContainsAndAuthorContainsAndPublisherContains(title: String, author: String, publisher: String) : List<Book> {
        return bookRepository.findByTitleContainsAndAuthorContainsAndPublisherContains(title, author, publisher)
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
