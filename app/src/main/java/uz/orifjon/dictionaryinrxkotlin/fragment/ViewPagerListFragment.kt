package uz.orifjon.dictionaryinrxkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.adapters.AdapterRecyclerView
import uz.orifjon.dictionaryinrxkotlin.database.AppDatabase
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentViewPagerListBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ViewPagerListFragment : Fragment() {
    private var param1: Long? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getLong(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentViewPagerListBinding
    private lateinit var adapter: AdapterRecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerListBinding.inflate(inflater)
        adapter = AdapterRecyclerView {word ->

        }

        AppDatabase.getDatabase(requireContext()).wordDao().listWord()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                adapter.submitList(it)
            }

        binding.rv.adapter = adapter


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Long, param2: String) =
            ViewPagerListFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


    }
}