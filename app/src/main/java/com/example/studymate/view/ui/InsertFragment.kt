package com.example.studymate.view.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.studymate.databinding.FragmentInsertBinding
import com.example.studymate.model.data.Friend
import com.example.studymate.utility.cropToBlob
import com.example.studymate.utility.errorDialog
import com.example.studymate.viewmodel.FriendViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

class InsertFragment : Fragment() {

    private lateinit var binding: FragmentInsertBinding
    private val nav by lazy { findNavController() }
    private val vm: FriendViewModel by activityViewModels()

    // TODO: Get content launcher
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            binding.imgPhoto.setImageURI(it.data?.data)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentInsertBinding.inflate(inflater, container, false)

        reset()
        binding.imgPhoto.setOnClickListener  { select() }
        binding.btnReset.setOnClickListener  { reset()  }
        binding.btnSubmit.setOnClickListener { submit() }

        return binding.root
    }

    private fun select() {
        // TODO: Select file
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        binding.edtId.text.clear()
        binding.edtName.text.clear()
        binding.edtAge.text.clear()
        binding.imgPhoto.setImageDrawable(null)
        binding.edtId.requestFocus()
    }

    private fun submit() {
        val f = Friend(
            id    = binding.edtId.text.toString().trim(),
            name  = binding.edtName.text.toString().trim(),
            age   = binding.edtAge.text.toString().toIntOrNull() ?: 0,
            photo = binding.imgPhoto.cropToBlob(300,300),
        )

        lifecycleScope.launch {
            val err = vm.validate(f)
            if (err != "") {
                errorDialog(err)
                return@launch
            }
            vm.set(f)
            nav.navigateUp()
        }
    }

}