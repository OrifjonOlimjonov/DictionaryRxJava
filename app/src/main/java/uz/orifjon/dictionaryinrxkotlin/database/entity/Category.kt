package uz.orifjon.dictionaryinrxkotlin.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val name:String
):Serializable