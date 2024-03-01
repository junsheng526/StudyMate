package com.example.studymate.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.studymate.R
import com.example.studymate.databinding.FragmentListBinding
import com.example.studymate.model.data.Friend
import com.example.studymate.view.adapters.FriendAdapter
import com.example.studymate.viewmodel.FriendViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val nav by lazy { findNavController() }
    private val vm: FriendViewModel by activityViewModels()

    private lateinit var adapter: FriendAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        binding.btnInsert.setOnClickListener { nav.navigate(R.id.insertFragment) }
        binding.btnDeleteAll.setOnClickListener { deleteAll() }

        adapter = FriendAdapter { holder, friend ->
            // Item click -> navigate to UpdateFragment (id)
            holder.binding.root.setOnClickListener {
                nav.navigate(R.id.updateFragment, bundleOf("id" to friend.id))
            }
            // Delete button click -> delete record
            holder.binding.btnDelete.setOnClickListener {
                delete(friend.id)
            }
        }
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        // TODO: Get all
//        Firebase.firestore
//            .collection("friends")
//            .get()
//            .addOnSuccessListener { snap ->
//                val list = snap.toObjects<Friend>()
//                adapter.submitList(list)
//                binding.txtCount.text = "${list.size} friends"
//            }
        vm.getAll().observe(viewLifecycleOwner){list ->
            adapter.submitList(list)
            binding.txtCount.text = "${list.size} friends"
        }

        return binding.root
    }

    private fun deleteAll() {
        // TODO: Delete all
        vm.deleteAll()
    }

    private fun delete(id: String) {
        // TODO: Delete
//        Firebase.firestore
//            .collection("friends")
//            .document(id)
//            .delete()
        vm.delete(id)
    }

}