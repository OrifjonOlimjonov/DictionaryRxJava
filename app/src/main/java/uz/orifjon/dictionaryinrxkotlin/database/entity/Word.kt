package uz.orifjon.dictionaryinrxkotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("category_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val translate: String,
    @ColumnInfo(name = "category_id")
    val categoryId:Long,
    val image:ByteArray,
    var isLike:Int = 0
):Serializable