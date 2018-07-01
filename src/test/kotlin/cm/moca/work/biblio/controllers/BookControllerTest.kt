package cm.moca.work.biblio.controllers

import cm.moca.work.biblio.entities.Book
import cm.moca.work.biblio.services.BookService
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.nio.charset.Charset

@RunWith(SpringRunner::class)
@WebMvcTest(BookController::class)
internal class BookControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var bookService: BookService


    private val contentType = MediaType(MediaType.APPLICATION_JSON.type,
            MediaType.APPLICATION_JSON.subtype, Charset.forName("utf8"))

    @Test
    fun findSingle() {
        val book = Book(0, "test title", "test author", "test publisher")
        val bookJson = mapper.writeValueAsString(book)
        whenever(bookService.findById(anyLong())).thenReturn(book)

        val result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/book/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk)
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$").isNotEmpty)
                .andExpect(jsonPath("$.title").value(book.title))
                .andExpect(jsonPath("$.author").value(book.author))
                .andExpect(jsonPath("$.publisher").value(book.publisher))
                .andDo(`print`())
                .andReturn()

        assertThat(result.response.contentAsString).isEqualTo(bookJson)
    }

}
