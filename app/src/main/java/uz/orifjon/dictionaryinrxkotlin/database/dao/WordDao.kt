package uz.orifjon.dictionaryinrxkotlin.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word

@Dao
interface WordDao {

    @Insert
    fun addWord(word: Word)

    @Update
    fun updateWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Query("SELECT * FROM word")
    fun listWord():Flowable<List<Word>>

}