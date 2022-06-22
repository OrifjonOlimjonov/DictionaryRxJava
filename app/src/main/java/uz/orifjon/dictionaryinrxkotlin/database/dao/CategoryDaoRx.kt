package uz.orifjon.dictionaryinrxkotlin.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable
import uz.orifjon.dictionaryinrxkotlin.database.entity.Category

@Dao
interface CategoryDaoRx {

    @Insert
    fun addCategory(category: Category)

    @Update
    fun updateCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)

    @Query("SELECT * FROM category")
    fun listCategory():Flowable<List<Category>>


}