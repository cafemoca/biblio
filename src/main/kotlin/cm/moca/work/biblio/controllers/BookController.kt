package cm.moca.work.biblio.controllers

import cm.moca.work.biblio.entities.Book
import cm.moca.work.biblio.services.BookService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("book")
class BookController(private val bookService: BookService) {

    @GetMapping("{id}", MediaType.APPLICATION_JSON_UTF8_VALUE)
    fun id(@PathVariable("id") id: Long): ResponseEntity<Book> {
        val book = bookService.findById(id)
        return if (book != null) {
            ResponseEntity.ok(book)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("list", MediaType.APPLICATION_JSON_UTF8_VALUE)
    fun list(page: Pageable): ResponseEntity<List<Book>> {
        return ResponseEntity.ok(bookService.findAll(page))
    }

    @GetMapping("search", MediaType.APPLICATION_JSON_UTF8_VALUE)
    fun search(@RequestParam(name = "title", required = false, defaultValue = "") title: String,
               @RequestParam(name = "author", required = false, defaultValue = "") author: String,
               @RequestParam(name = "publisher", required = false, defaultValue = "") publisher: String): ResponseEntity<List<Book>> {
        return ResponseEntity.ok(bookService.findByParams(title, author, publisher))
    }

    @PostMapping("", MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE)
    fun save(@RequestBody book: Book): ResponseEntity<String> {
        bookService.save(book)
        return ResponseEntity.ok("success")
    }

    @PutMapping("{id}")
    fun save(@PathVariable("id") id: Long, @RequestBody book: Book): ResponseEntity<String> {
        book.id = id
        bookService.save(book)
        return ResponseEntity.ok("success")
    }

    @DeleteMapping("{id}", MediaType.TEXT_PLAIN_VALUE)
    fun delete(@PathVariable("id") id: Long): ResponseEntity<String> {
        bookService.delete(id)
        return ResponseEntity.ok("success")
    }

}
