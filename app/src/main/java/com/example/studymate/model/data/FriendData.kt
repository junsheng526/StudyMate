package com.example.studymate.model.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import java.util.Date

// TODO: Specify document id
// TODO: Add photo and date
data class Friend(
    @DocumentId
    var id   : String = "",
    var name : String = "",
    var age  : Int = 0,
    var date : Date = Date(),
    var photo: Blob = Blob.fromBytes(ByteArray(0)),
)