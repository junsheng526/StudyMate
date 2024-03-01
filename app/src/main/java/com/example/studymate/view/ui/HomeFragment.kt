package com.example.studymate.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studymate.R
import com.example.studymate.databinding.FragmentHomeBinding
import com.example.studymate.model.data.Friend
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnFriend.setOnClickListener { nav.navigate(R.id.listFragment) }
        binding.btnRead.setOnClickListener   { read()   }
        binding.btnSet.setOnClickListener    { set()    }
        binding.btnUpdate.setOnClickListener { update() }
        binding.btnDelete.setOnClickListener { delete() }

        return binding.root
    }

    private fun read() {
        // TODO
        Firebase.firestore
            .collection("friends")
            .get()
            .addOnSuccessListener { snap ->
                val list = snap.toObjects<Friend>()
                var result = ""
                list.forEach{ f-> result += "${f.id} ${f.name} ${f.age}\n"}
                binding.txtResult.text = result
            }
    }

    private fun set() {
        // TODO
        val f = Friend("A0004", "Diana", 24)

        Firebase.firestore
            .collection("friends")
            .document(f.id)
            .set(f)
            .addOnSuccessListener { toast("Record inserted") }
    }

    private fun update() {
        // TODO
        Firebase.firestore
            .collection("friends")
            .document("A0004")
            .update("age", 99)
            .addOnSuccessListener { toast("Updated successful") }
    }

    private fun delete() {
        // TODO
        Firebase.firestore
            .collection("friends")
            .document("A0004")
            .delete()
            .addOnSuccessListener { toast("Deleted successful") }
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}