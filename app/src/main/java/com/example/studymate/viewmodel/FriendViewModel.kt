package com.example.studymate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studymate.model.data.Friend
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await

class FriendViewModel : ViewModel() {

    // TODO: Initialization
    private val col = Firebase.firestore.collection("friends")
    private val friends = MutableLiveData<List<Friend>>()

    init{
        col.addSnapshotListener { snap, _ -> friends.value = snap?.toObjects() }
    }

    // ---------------------------------------------------------------------------------------------

    fun init() = 0 // TODO

    suspend fun get(id: String): Friend? {
        return col.document(id).get().await().toObject<Friend>()
    }

    fun getAll() = friends // TODO

    fun delete(id: String) {
        // TODO
        col.document(id).delete()
    }

    fun deleteAll() {
        // TODO
        // col.get().addOnSuccessListener { snap -> snap.documents.forEach { doc -> delete(doc.id) } }
        friends.value?.forEach { f -> delete(f.id) }
    }

    fun set(f: Friend) {
        // TODO
        col.document(f.id).set(f)
    }

    //----------------------------------------------------------------------------------------------
    // TODO: Call FireStore directly to check the data
//    private suspend fun idExists(id: String): Boolean{
//        return col.document(id).get().await().exists()
//    }

    // TODO: Call local to check the data
    private fun idExists(id: String): Boolean{
        return friends.value?.any{f -> f.id == id} ?: false
    }

//    private suspend fun nameExists(name: String): Boolean{
//        return col.whereEqualTo("name",name).get().await().size() > 0
//    }

    private fun nameExists(name: String): Boolean{
        return friends.value?.any{f -> f.name == name} ?: false
    }

    fun validate(f: Friend, insert: Boolean = true): String {
        val regexId = Regex("""^[0-9A-Z]{4}$""")
        var e = ""

        if (insert) {
            e += if (f.id == "") "- Id is required.\n"
            else if (!f.id.matches(regexId)) "- Id format is invalid (format: X999).\n"
            else if (idExists(f.id)) "- Id is duplicated.\n"
            else ""
        }

        e += if (f.name == "") "- Name is required.\n"
        else if (f.name.length < 3) "- Name is too short (at least 3 letters).\n"
        else if (nameExists(f.name)) "- Name is duplicated.\n"
        else ""

        e += if (f.age == 0) "- Age is required.\n"
        else if (f.age < 18) "- Underage (at least 18).\n"
        else ""

        // TODO: Validate if photo is empty
        e += if (f.photo.toBytes().isEmpty()) "- Photo is required.\n"
        else ""

        return e
    }
}