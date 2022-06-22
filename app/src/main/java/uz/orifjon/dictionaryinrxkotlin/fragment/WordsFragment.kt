package uz.orifjon.dictionaryinrxkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.adapters.AdapterRecyclerView
import uz.orifjon.dictionaryinrxkotlin.database.AppDatabase
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentWordsBinding

class WordsFragment : Fragment() , NavigationBarView.OnItemSelectedListener  {

    private lateinit var binding:FragmentWordsBinding
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var adapter:AdapterRecyclerView
    private var size = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordsBinding.inflate(inflater,container,false)

        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavigation.visibility = View.INVISIBLE
        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation2)
        bottomNavigation.visibility = View.VISIBLE
        adapter = AdapterRecyclerView { word ->

        }
     //   bottomNavigation.setOnItemSelectedListener(this)
        AppDatabase.getDatabase(requireContext()).wordDao().listWord().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                adapter.submitList(it)
                size = it.size
            }
        if(size == 0){
            Toast.makeText(requireContext(), "Ma'lumotlar mavjud emas!!", Toast.LENGTH_SHORT).show()
        }
        binding.rv.adapter = adapter

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.btnAdd->{
                        findNavController().navigate(R.id.addWordFragment)
                }
            }
            true
        }

        binding.toolbar.setNavigationOnClickListener {
            bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation)
            findNavController().popBackStack()
            bottomNavigation.visibility = View.VISIBLE
            bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation2)
            bottomNavigation.visibility = View.INVISIBLE
        }
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation)
                    findNavController().popBackStack()
                    bottomNavigation.visibility = View.VISIBLE
                    bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation2)
                    bottomNavigation.visibility = View.INVISIBLE
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)


        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.categoryFragment->{
                findNavController().popBackStack()
                findNavController().navigate(R.id.categoryFragment)
            }
            R.id.wordsFragment->{
                findNavController().popBackStack()
                findNavController().navigate(R.id.wordsFragment)
            }
        }
        return false
    }



}