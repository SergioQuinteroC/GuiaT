package com.sergio.guiat.server.serverrepository

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.GuiaTProject
import com.sergio.guiat.local.Users
import com.sergio.guiat.local.GuiaTDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UsersRepository {

    val db = Firebase.firestore
    private val auth: FirebaseAuth = Firebase.auth

    suspend fun registerUser(mail: String, password: String, name:String): String? {
        return try {
            val result = auth.createUserWithEmailAndPassword(mail, password).await()
            result.user?.uid
        } catch (e: FirebaseAuthException) {
            Log.d("errorRegister", e.localizedMessage)
            e.localizedMessage
        } catch (e: FirebaseNetworkException){
            e.localizedMessage
        }
    }

    suspend fun searchUser(): QuerySnapshot {
        return withContext(Dispatchers.IO){
            db.collection("users").get().await()
        }
    }
}


