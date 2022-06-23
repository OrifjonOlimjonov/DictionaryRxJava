package uz.orifjon.dictionaryinrxkotlin.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWord(word: Word)

    @Update
    fun updateWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Query("SELECT * FROM word ORDER BY name")
    fun listWord():Flowable<List<Word>>

    @Query("SELECT * FROM word WHERE category_id = :id ORDER BY name")
    fun listIdWords(id:Int):Flowable<List<Word>>

    @Query("SELECT * FROM word WHERE id = :id")
    fun getFindById(id:Long):Word

}