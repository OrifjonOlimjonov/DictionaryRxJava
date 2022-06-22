package uz.orifjon.dictionaryinrxkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment(), NavigationBarView.OnItemSelectedListener  {

    private lateinit var binding:FragmentCategoryBinding
    private lateinit var bottomNavigation:BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavigation.visibility = View.INVISIBLE
        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation2)
        bottomNavigation.visibility = View.VISIBLE

        bottomNavigation.setOnItemSelectedListener(this)




        binding.toolbar.setNavigationOnClickListener {
            bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation)
            findNavController().popBackStack()
            bottomNavigation.visibility = View.VISIBLE
            bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation2)
            bottomNavigation.visibility = View.INVISIBLE
        }




        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when(item.itemId){
                R.id.categoryFragment->{
                    findNavController().navigate(R.id.categoryFragment)
                }
                R.id.wordsFragment->{
                    findNavController().navigate(R.id.wordsFragment)
                }
            }
            return false
    }


}