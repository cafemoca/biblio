package cm.moca.work.biblio.entities

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "books")
data class Book(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(name = "title", nullable = false)
        var title: String = "",

        @Column(name = "author", nullable = false)
        var author: String = "",

        @Column(name = "publisher", nullable = false)
        var publisher: String = ""

) : Serializable
