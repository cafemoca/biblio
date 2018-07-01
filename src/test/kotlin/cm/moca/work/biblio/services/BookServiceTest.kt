package cm.moca.work.biblio.services

import au.com.console.jpaspecificationdsl.and
import au.com.console.jpaspecificationdsl.equal
import au.com.console.jpaspecificationdsl.like
import cm.moca.work.biblio.entities.Book
import cm.moca.work.biblio.repositories.BookRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
internal class BookServiceTest {

    @Test
    fun findById() {
        val book = Optional.ofNullable(Book(0, "test title", "test author", "test publisher"))
        val mock = mock<BookRepository> {
            on { findById(anyLong()) }.doReturn(book)
        }
        mock.findById(anyLong()).apply {
            assertThat(this).isNotNull
            assertThat(this).isEqualTo(book)
        }
        verify(mock, times(1)).findById(anyLong())
    }

    @Test
    fun findByParams() {
        val books = mutableListOf(Book(0, "test title", "test author", "test publisher"))
        val spec = and(
                Book::title.like("%test title%"),
                Book::author.like("%test author%"),
                Book::publisher.like("%test publisher%"))
        val mock = mock<BookRepository> {
            on { findAll(spec) }.doReturn(books)
        }
        mock.findAll(spec).apply {
            assertThat(this).isNotNull
            assertThat(this).hasSize(1)
            assertThat(this).containsSequence(books)
        }
        verify(mock, times(1)).findAll(spec)
    }

    @Test
    fun findAll() {
        val books = mutableListOf(
                Book(0, "test title 1", "test author 1", "test publisher 1"),
                Book(1, "test title 2", "test author 2", "test publisher 2"),
                Book(2, "test title 3", "test author 3", "test publisher 3")
        )
        val pageable = PageRequest.of(0, 5)
        val page = PageImpl<Book>(books, pageable, 3)
        val mock = mock<BookRepository> {
            on { findAll(pageable) }.doReturn(page)
        }
        mock.findAll(pageable).apply {
            assertThat(this).isNotNull
            assertThat(this).hasSize(3)
            assertThat(this).containsSequence(books)
        }
        verify(mock, times(1)).findAll(pageable)
    }

    @Test
    fun findAllWithEmptyList() {
        val books = mutableListOf<Book>()
        val pageable = PageRequest.of(0, 5)
        val page = PageImpl<Book>(books, pageable, 0)
        val mock = mock<BookRepository> {
            on { findAll(pageable) }.doReturn(page)
        }
        mock.findAll(pageable).apply {
            assertThat(this).isNullOrEmpty()
            assertThat(this).hasSize(0)
        }
        verify(mock, times(1)).findAll(pageable)
    }

}