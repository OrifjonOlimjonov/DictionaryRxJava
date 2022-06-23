package uz.orifjon.dictionaryinrxkotlin.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.adapters.AdapterRecyclerView
import uz.orifjon.dictionaryinrxkotlin.adapters.AdapterRecyclerViewCategory
import uz.orifjon.dictionaryinrxkotlin.adapters.AdapterRecyclerViewWord
import uz.orifjon.dictionaryinrxkotlin.database.AppDatabase
import uz.orifjon.dictionaryinrxkotlin.database.entity.Category
import uz.orifjon.dictionaryinrxkotlin.databinding.DeleteDialogBinding
import uz.orifjon.dictionaryinrxkotlin.databinding.DialogAddTabBinding
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentWordsBinding

class WordsFragment : Fragment()   {

    private lateinit var binding:FragmentWordsBinding
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var adapter: AdapterRecyclerViewWord
    private var size = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordsBinding.inflate(inflater,container,false)

        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavigation.visibility = View.INVISIBLE
        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation2)
        bottomNavigation.visibility = View.VISIBLE
        adapter = AdapterRecyclerViewWord { word,item ->
            val popup = PopupMenu(requireContext(),item)
            popup.inflate(R.menu.popup)

            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item1: MenuItem? ->

                when (item1!!.itemId) {
                    R.id.btnEdit -> {
                        val bundle= Bundle()
                        bundle.putLong("id",word.id)
                        findNavController().navigate(R.id.editWordFragment,bundle)
                    }
                    R.id.btnDelete -> {
                        val alertDialog = AlertDialog.Builder(requireContext())
                        val binding: DeleteDialogBinding =
                            DeleteDialogBinding.inflate(layoutInflater)
                        alertDialog.setView(binding.root)
                        val alertDialog1 = alertDialog.create()
                        alertDialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        binding.btnCancel.setOnClickListener { view1 -> alertDialog1.dismiss() }
                        binding.btnDelete.setOnClickListener { view12 ->
                            AppDatabase.getDatabase(requireContext()).wordDao().deleteWord(word)
                            alertDialog1.dismiss()
                        }
                        alertDialog1.show()

                    }

                }

                true
            })

            popup.show()
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



}