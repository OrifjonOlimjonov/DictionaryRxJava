package uz.orifjon.dictionaryinrxkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import uz.orifjon.dictionaryinrxkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigationHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navigationHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        val navController1 = navigationHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation2, navController1)


        // binding.bottomNavigation.setOnItemSelectedListener(this)
    }


}