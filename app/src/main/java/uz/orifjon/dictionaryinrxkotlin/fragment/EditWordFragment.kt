package uz.orifjon.dictionaryinrxkotlin.fragment

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.adapters.SpinnerAdapter
import uz.orifjon.dictionaryinrxkotlin.database.AppDatabase
import uz.orifjon.dictionaryinrxkotlin.database.entity.Category
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word
import uz.orifjon.dictionaryinrxkotlin.databinding.DialogCamGalleryBinding
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentEditWordBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class EditWordFragment : Fragment() {

    private lateinit var binding: FragmentEditWordBinding
    private lateinit var currentPhotoPath: String
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var adapter: SpinnerAdapter
    private lateinit var list: ArrayList<Category>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditWordBinding.inflate(inflater, container, false)
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation2)
        bottomNavigationView.visibility = View.INVISIBLE
        val id = arguments?.getLong("id")
        val word = AppDatabase.getDatabase(requireContext()).wordDao().getFindById(id!!)
        list = AppDatabase.getDatabase(requireContext()).categoryDao()
            .listCategory() as ArrayList<Category>
        adapter = SpinnerAdapter(list)
        binding.edittext.setText(word.name)
        binding.edittext1.setText(word.translate)
        binding.spinner.adapter = adapter

        list.forEachIndexed { index, category ->
            if (category.id == word.categoryId) {
                binding.spinner.setSelection(index)
            }
        }




        binding.imageSymbol.setOnClickListener {

            val alertDialog = AlertDialog.Builder(requireContext())
            val binding: DialogCamGalleryBinding =
                DialogCamGalleryBinding.inflate(layoutInflater)
            alertDialog.setView(binding.root)
            val alertDialog1 = alertDialog.create()
            alertDialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.camera.setOnClickListener { view1 ->
                takeCamere()
                alertDialog1.dismiss()
            }
            binding.gallery.setOnClickListener { view12 ->
                takePhotoResult.launch("image/*")
                alertDialog1.dismiss()
            }
            alertDialog1.show()

        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener {
            val name = binding.edittext.text.toString()
            val translate = binding.edittext1.text.toString()
            val id1 = list[binding.spinner.selectedItemPosition].id
            val img = getBitmapFromView(binding.imageSymbol)
            val byteStream = ByteArrayOutputStream()
            img!!.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
            val byteArray = byteStream.toByteArray()
            val word1 = Word(
                word.id,
                name = name,
                translate = translate,
                categoryId = id1,
                image = byteArray
            )
            AppDatabase.getDatabase(requireContext()).wordDao().updateWord(word1)
            findNavController().popBackStack()
        }
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }

    fun getBitmapFromView(view: View): Bitmap? {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun takeCamere() {
        val file = try {
            createImageFile()
        } catch (e: Exception) {
            null
        }
        val uriForFile = file?.let {
            FileProvider.getUriForFile(requireContext(), "uz.orifjon.dictionaryinrxkotlin", it)
        }
        takePhoneCameraResult.launch(uriForFile)
    }

    private val takePhotoResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) return@registerForActivityResult
            binding.imageSymbol.setImageURI(uri)
            val openInputStream = requireActivity().contentResolver?.openInputStream(uri)
            val file = File(requireActivity().filesDir, "face.png")
            val fileOutputStream = FileOutputStream(file)
            openInputStream?.copyTo(fileOutputStream)
            openInputStream?.close()
        }
    private val takePhoneCameraResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                binding.imageSymbol.setImageURI(Uri.fromFile(File(currentPhotoPath)))
            }
        }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val externalFilesDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("${System.currentTimeMillis()}", ".png", externalFilesDir)
            .apply {
                currentPhotoPath = absolutePath
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavigationView.visibility = View.VISIBLE
    }

}