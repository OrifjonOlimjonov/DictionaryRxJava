package uz.orifjon.dictionaryinrxkotlin

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationBarView
import uz.orifjon.dictionaryinrxkotlin.database.AppDatabase
import uz.orifjon.dictionaryinrxkotlin.database.entity.Category
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word
import uz.orifjon.dictionaryinrxkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()   {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppDatabase.getDatabase(this).categoryDao().addCategory(Category(name = "Futbol"))
        AppDatabase.getDatabase(this).wordDao().addWord(Word(name = "salom", translate = "Uzun", courseId = 1, image = ByteArray(2)))
        AppDatabase.getDatabase(this).wordDao().addWord(Word(name = "salom", translate = "Uzun", courseId = 1, image = ByteArray(2)))
        AppDatabase.getDatabase(this).wordDao().addWord(Word(name = "salom", translate = "Uzun", courseId = 1, image = ByteArray(2)))
        AppDatabase.getDatabase(this).wordDao().addWord(Word(name = "salom", translate = "Uzun", courseId = 1, image = ByteArray(2)))
        AppDatabase.getDatabase(this).wordDao().addWord(Word(name = "salom", translate = "Uzun", courseId = 1, image = ByteArray(2)))
        AppDatabase.getDatabase(this).wordDao().addWord(Word(name = "salom", translate = "Uzun", courseId = 1, image = ByteArray(2)))
        AppDatabase.getDatabase(this).wordDao().addWord(Word(name = "salom", translate = "Uzun", courseId = 1, image = ByteArray(2)))
        AppDatabase.getDatabase(this).wordDao().addWord(Word(name = "salom", translate = "Uzun", courseId = 1, image = ByteArray(2)))
        AppDatabase.getDatabase(this).wordDao().addWord(Word(name = "salom", translate = "Uzun", courseId = 1, image = ByteArray(2)))
        AppDatabase.getDatabase(this).wordDao().addWord(Word(name = "salom", translate = "Uzun", courseId = 1, image = ByteArray(2)))
        val navigationHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navigationHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)


        // binding.bottomNavigation.setOnItemSelectedListener(this)
    }


}