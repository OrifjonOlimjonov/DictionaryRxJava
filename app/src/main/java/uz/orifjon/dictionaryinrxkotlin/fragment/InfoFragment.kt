package uz.orifjon.dictionaryinrxkotlin.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.database.AppDatabase
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {

    private lateinit var binding:FragmentInfoBinding
    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater)
        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavigation.visibility = View.INVISIBLE
        val word = arguments?.getSerializable("word") as Word

        binding.toolbar.title = word.name
        if(word.isLike == 1){
            binding.btnLike.setBackgroundResource(R.drawable.btn_blur_is_liked)
        }else{
            binding.btnLike.setBackgroundResource(R.drawable.btn_blur_is_disliked)
        }
        binding.tvName.text = word.name
        binding.tvInfo.text = word.translate
        binding.img.setImageBitmap(
            BitmapFactory.decodeByteArray(
                word.image,
                0,
                word.image.size
            )
        )
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

        binding.btnLike.setOnClickListener {
            if (word.isLike == 1) {
                word.isLike = 0
                binding.btnLike.setBackgroundResource(R.drawable.btn_blur_is_disliked)
            } else {
                word.isLike = 1
                binding.btnLike.setBackgroundResource(R.drawable.btn_blur_is_liked)
            }
            AppDatabase.getDatabase(requireContext()).wordDao().updateWord(word)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavigation.visibility = View.VISIBLE
    }

}