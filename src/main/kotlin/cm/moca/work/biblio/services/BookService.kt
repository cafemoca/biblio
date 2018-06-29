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
    fun findAll(page: Pageable) : List<Book> {
        return bookRepository.findAll(page).content
    }

    @Transactional(readOnly = true)
    fun findByTitle(title: String) : List<Book> {
        return bookRepository.findByTitle(title)
    }

    @Transactional(readOnly = true)
    fun findByAuthor(author: String) : List<Book> {
        return bookRepository.findByAuthor(author)
    }

    @Transactional(readOnly = true)
    fun findByPublisher(publisher: String) : List<Book> {
        return bookRepository.findByPublisher(publisher)
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
