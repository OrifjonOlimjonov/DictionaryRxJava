package uz.orifjon.dictionaryinrxkotlin.fragment

import android.Manifest
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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vmadalin.easypermissions.BuildConfig
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.orifjon.dictionaryinrxkotlin.R
import uz.orifjon.dictionaryinrxkotlin.adapters.SpinnerAdapter
import uz.orifjon.dictionaryinrxkotlin.database.AppDatabase
import uz.orifjon.dictionaryinrxkotlin.database.entity.Category
import uz.orifjon.dictionaryinrxkotlin.database.entity.Word
import uz.orifjon.dictionaryinrxkotlin.databinding.DialogCamGalleryBinding
import uz.orifjon.dictionaryinrxkotlin.databinding.FragmentAddWordBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class AddWordFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: FragmentAddWordBinding
    private lateinit var currentPhotoPath: String
    private lateinit var adapter: SpinnerAdapter
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var list: ArrayList<Category>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWordBinding.inflate(inflater)
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation2)
        bottomNavigationView.visibility = View.INVISIBLE
        list = AppDatabase.getDatabase(requireContext()).categoryDao()
            .listCategory() as ArrayList<Category>
        adapter = SpinnerAdapter(list)
        binding.spinner.adapter = adapter

        binding.imageSymbol.setOnClickListener {
            if (!hasContactPermission()) {
                requestContactsPermission()
            } else {
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
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener {
            
            val name = binding.edittext.text.toString()
            val translate = binding.edittext1.text.toString()
            if(name.isNotEmpty() && translate.isNotEmpty() && binding.spinner.selectedItemPosition > -1) {
                val id = list[binding.spinner.selectedItemPosition].id
                val img = getBitmapFromView(binding.imageSymbol)
                val byteStream = ByteArrayOutputStream()
                img!!.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
                val byteArray = byteStream.toByteArray()
                val word = Word(
                    name = name,
                    translate = translate,
                    categoryId = id,
                    image = byteArray
                )
                AppDatabase.getDatabase(requireContext()).wordDao().addWord(word)
                findNavController().popBackStack()
            }else{
                Toast.makeText(requireContext(), "Maydonlarni to'ldiring!!", Toast.LENGTH_SHORT).show()
            }
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

    private fun hasContactPermission() =
        EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )

    private fun requestContactsPermission() {
        EasyPermissions.requestPermissions(
            this, "Ilova to'liq ishlashi uchun ruxsatlarni yoqing!!",
            1,
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestContactsPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (hasContactPermission()) {

        } else {
            Toast.makeText(requireContext(), "Not granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavigationView.visibility = View.VISIBLE
    }
}