package cm.moca.work.biblio.controllers

import cm.moca.work.biblio.entities.Book
import cm.moca.work.biblio.services.BookService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping



@Controller
@RequestMapping("/")
class IndexController(private val bookService: BookService) {

    @GetMapping("")
    fun index(page: Pageable, model: Model): String {
        val books = bookService.findAll(page)
        model.addAttribute("books", books)
        return "index"
    }

    @GetMapping("search")
    fun search(@RequestParam(name = "title", required = false, defaultValue = "") title: String,
               @RequestParam(name = "author", required = false, defaultValue = "") author: String,
               @RequestParam(name = "publisher", required = false, defaultValue = "") publisher: String,
               model: Model): String {
        val books = bookService.findByParams(title, author, publisher)
        model.addAttribute("books", books)
        return "index"
    }

    @GetMapping("{id}")
    fun find(@PathVariable("id") id: Long, model: Model): String {
        val book = bookService.findById(id)
        model.addAttribute("book", book)
        return "single"
    }

    @GetMapping("create")
    fun create(model: Model): String {
        return "create"
    }

    @PostMapping("create")
    fun create(@ModelAttribute book: Book): String {
        bookService.save(book)
        return "redirect:/"
    }

    @PutMapping("{id}")
    fun update(@PathVariable("id") id: Long, @ModelAttribute book: Book): String {
        book.id = id
        bookService.save(book)
        return "redirect:$id"
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: Long): String {
        bookService.delete(id)
        return "redirect:/"
    }

}
