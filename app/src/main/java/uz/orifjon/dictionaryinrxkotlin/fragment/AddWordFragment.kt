package uz.orifjon.dictionaryinrxkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentAddWordBinding


class AddWordFragment : Fragment() {

    private lateinit var binding:FragmentAddWordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWordBinding.inflate(inflater)




        return binding.root
    }


}