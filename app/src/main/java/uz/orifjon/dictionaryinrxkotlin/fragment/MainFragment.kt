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
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var list: ArrayList<Category>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        list = ArrayList()
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.btnAdd) {
                findNavController().navigate(R.id.categoryFragment)
            }
            true
        }

        AppDatabase.getDatabase(requireContext()).categoryDao().listCategory()
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe {
                list = ArrayList(it)
            }
        if (list.size == 0) {
            Toast.makeText(requireContext(), "Ma'lumotlar mavjud emas!!", Toast.LENGTH_SHORT).show()
        }
        adapter = AdapterViewPager2(
            requireActivity(),
            list
        )
        binding.viewPager2.adapter = adapter
        val tabLayoutMediator = TabLayoutMediator(
            binding.tablayout, binding.viewPager2
        ) { tab: TabLayout.Tab, position: Int ->
            val bindingItem: ItemTablayoutBinding =
                ItemTablayoutBinding.inflate(LayoutInflater.from(context), null, false)
            if (position == 0) {
                bindingItem.tabText.setTextColor(Color.WHITE)
            } else {
                bindingItem.tabText.setTextColor(Color.parseColor("#8A8A8A"))
            }
            tab.customView = bindingItem.root
            binding.tablayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val binding: ItemTablayoutBinding = ItemTablayoutBinding.bind(tab.customView!!)
                    binding.tabText.setTextColor(Color.WHITE)

                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    val binding: ItemTablayoutBinding = ItemTablayoutBinding.bind(tab.customView!!)
                    binding.tabText.setTextColor(Color.parseColor("#8A8A8A"))

                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
        tabLayoutMediator.attach()



        return binding.root
    }


}