package uz.orifjon.dictionaryinrxkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.adapters.AdapterRecyclerView
import uz.orifjon.dictionaryinrxkotlin.database.AppDatabase
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentLikedBinding
import java.util.function.Predicate


class LikedFragment : Fragment() {

    private lateinit var binding: FragmentLikedBinding
    private lateinit var adapter: AdapterRecyclerView
    private var size = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLikedBinding.inflate(inflater, container, false)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.btnAdd) {
                findNavController().navigate(R.id.categoryFragment)
            }
            true
        }
        adapter = AdapterRecyclerView { word ->
            val bundle = Bundle()
            bundle.putSerializable("word", word)
            findNavController().navigate(R.id.infoFragment, bundle)
        }

        AppDatabase.getDatabase(requireContext()).wordDao().listWord()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { item ->
                adapter.submitList(item.filter { it.isLike == 1 })
                size = item.filter { it.isLike == 1 }.size
            }

        if (size == 0) {
            Toast.makeText(requireContext(), "Like bosilganlar mavjud emas!!", Toast.LENGTH_SHORT)
                .show()
        }
        binding.rv.adapter = adapter




        return binding.root
    }
}