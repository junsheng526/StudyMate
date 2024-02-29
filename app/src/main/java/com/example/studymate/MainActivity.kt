package com.example.studymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studymate.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    val firestoreDb = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding!!.btnReadData.setOnClickListener(){
            firestoreDb.collection("users")
                .get()
                .addOnCompleteListener {
                    val result: StringBuffer = StringBuffer()
                    if(it.isSuccessful){
                        for(document in it.result!!){
                            result.append(document.data.getValue("name")).append("\n")
                        }
                    }
                    binding?.textView?.text = result
                }
        }
    }
}