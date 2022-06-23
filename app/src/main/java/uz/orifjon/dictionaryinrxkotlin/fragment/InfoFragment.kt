package uz.orifjon.dictionaryinrxkotlin.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater)
        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavigation.visibility = View.VISIBLE
        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation2)
        bottomNavigation.visibility = View.INVISIBLE
        val word = arguments?.getSerializable("word") as Word

        binding.toolbar.title = word.name

        binding.tvName.text = word.name
        binding.tvInfo.text = word.translate
        binding.img.setImageBitmap(
            BitmapFactory.decodeByteArray(
                word.image,
                0,
                word.image.size
            )
        )





        return binding.root
    }

}