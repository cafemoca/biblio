package cm.moca.work.biblio.repositories

import cm.moca.work.biblio.entities.Book
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
internal class BookRepositoryTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var bookRepository: BookRepository

    @Test
    @Sql(statements = ["INSERT INTO books (id, title, author, publisher) VALUES (123, 'test title', 'test author', 'test publisher')"])
    fun findById() {
        val book = entityManager.find(Book::class.java, 123L)
        val actual = bookRepository.findById(book.id).orElseGet { null }
        assertThat(actual).isNotNull
        assertThat(actual).isEqualTo(book)
    }

    @Test
    fun findByParams() {
        val book = entityManager.find(Book::class.java, 123L)
        val actual = bookRepository.findByTitleContainsAndAuthorContainsAndPublisherContains("title", "", "")
        assertThat(actual).isNotNull
        assertThat(actual).hasSize(1)
        assertThat(actual).first().isEqualTo(book)
    }

    @Test
    fun save() {
        val book = Book(123, "test title", "test author", "test publisher")
        bookRepository.saveAndFlush(book)
        val actual = entityManager.find(Book::class.java, book.id)
        assertThat(actual).isEqualTo(book)
    }

    @Test
    @Sql(statements = ["INSERT INTO books (id, title, author, publisher) VALUES (123, 'test title', 'test author', 'test publisher')"])
    fun delete() {
        val book = entityManager.find(Book::class.java, 123L)
        bookRepository.deleteById(book.id)
        bookRepository.flush()
        val actual = entityManager.find(Book::class.java, book.id)
        assertThat(actual).isNull()
    }

}
