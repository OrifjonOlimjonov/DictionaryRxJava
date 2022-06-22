package uz.orifjon.dictionaryinrxkotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("course_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val translate: String,
    @ColumnInfo(name = "course_id")
    val courseId:Long,
    val image:ByteArray,
    var isLike:Int = 0
)