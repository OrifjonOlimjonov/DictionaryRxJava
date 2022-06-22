package uz.orifjon.dictionaryinrxkotlin.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.adapters.AdapterViewPager2
import uz.orifjon.dictionaryinrxkotlin.database.AppDatabase
import uz.orifjon.dictionaryinrxkotlin.database.entity.Category
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentMainBinding
import uz.orifjon.dictionaryinrxkotlin.databinding.ItemTablayoutBinding


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: AdapterViewPager2
    private lateinit var list: ArrayList<Category>
    private var size = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.btnAdd) {
                findNavController().navigate(R.id.categoryFragment)
            }
            true
        }

        list = AppDatabase.getDatabase(requireContext()).categoryDao().listCategory() as ArrayList<Category>
//        AppDatabase.getDatabase(requireContext())
//            .categoryDaoRx()
//            .listCategory()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe {
//             list.addAll(it)
//            }
        if (list.size == 0) {
            Toast.makeText(requireContext(), "Ma'lumotlar mavjud emas!!", Toast.LENGTH_SHORT).show()
        }
        adapter = AdapterViewPager2(
            requireActivity(),
            list
        )

        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tablayout, binding.viewPager2) { tab, position ->
           tab.text = list[position].name
        }.attach()



        return binding.root
    }




}