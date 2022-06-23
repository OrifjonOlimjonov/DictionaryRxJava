package uz.orifjon.dictionaryinrxkotlin.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.adapters.AdapterRecyclerView
import uz.orifjon.dictionaryinrxkotlin.adapters.AdapterRecyclerViewCategory
import uz.orifjon.dictionaryinrxkotlin.database.AppDatabase
import uz.orifjon.dictionaryinrxkotlin.database.entity.Category
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word
import uz.orifjon.dictionaryinrxkotlin.databinding.DeleteDialogBinding
import uz.orifjon.dictionaryinrxkotlin.databinding.DialogAddTabBinding
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var adapter: AdapterRecyclerViewCategory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavigation.visibility = View.INVISIBLE
        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation2)
        bottomNavigation.visibility = View.VISIBLE

//         bottomNavigation.setOnItemSelectedListener(this)
        adapter = AdapterRecyclerViewCategory { category, item ->
            val popup = PopupMenu(requireContext(),item)
            popup.inflate(R.menu.popup)

            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item1: MenuItem? ->

                when (item1!!.itemId) {
                    R.id.btnEdit -> {
                        val alertDialog = AlertDialog.Builder(requireContext())
                        val binding: DialogAddTabBinding =
                            DialogAddTabBinding.inflate(layoutInflater)
                        alertDialog.setView(binding.root)
                        val alertDialog1 = alertDialog.create()
                        binding.edittext.setText(category.name)
                        alertDialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        binding.btnCancel.setOnClickListener { view1 -> alertDialog1.dismiss() }
                        binding.btnSave.setOnClickListener { view12 ->
                            val text = binding.edittext.text.toString()
                            if (text.isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "Maydonni to'ldiring!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val category1 = Category(id = category.id, name = text)
                                AppDatabase.getDatabase(requireContext()).categoryDaoRx()
                                    .updateCategory(category1)
                                alertDialog1.dismiss()
                            }
                        }
                        alertDialog1.show()
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
                            AppDatabase.getDatabase(requireContext()).categoryDaoRx()
                                .deleteCategory(category)
                            alertDialog1.dismiss()
                        }
                        alertDialog1.show()

                    }

                }

                true
            })

            popup.show()
        }
        AppDatabase.getDatabase(requireContext()).categoryDaoRx().listCategory()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                adapter.submitList(it)
//                if(it.isEmpty()){
//                    Toast.makeText(requireContext(), "Ma'lumotlar mavjud emas!!", Toast.LENGTH_SHORT).show()
//                }
            }


        binding.rv.adapter = adapter

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnAdd -> {
                    val alertDialog = AlertDialog.Builder(requireContext())
                    val binding: DialogAddTabBinding = DialogAddTabBinding.inflate(layoutInflater)
                    alertDialog.setView(binding.root)
                    val alertDialog1 = alertDialog.create()
                    alertDialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    binding.btnCancel.setOnClickListener { view1 -> alertDialog1.dismiss() }
                    binding.btnSave.setOnClickListener { view12 ->
                        val text = binding.edittext.text.toString()
                        if (text.isEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                "Maydonni to'ldiring!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val category = Category(name = text)
                            AppDatabase.getDatabase(requireContext()).categoryDaoRx()
                                .addCategory(category)
                            alertDialog1.dismiss()
                        }
                    }
                    alertDialog1.show()
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