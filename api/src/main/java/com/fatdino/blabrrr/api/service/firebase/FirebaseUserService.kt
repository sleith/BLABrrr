package com.fatdino.blabrrr.api.service.firebase

import android.net.Uri
import android.util.Log
import com.fatdino.blabrrr.api.extensions.toMD5
import com.fatdino.blabrrr.api.model.User
import com.fatdino.blabrrr.api.model.responds.CheckUsernameAvailabilityResp
import com.fatdino.blabrrr.api.model.responds.UserResp
import com.fatdino.blabrrr.api.service.ApiUserService
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import java.io.File


class FirebaseUserService() : ApiUserService {

    companion object {
        const val USER_PATH = "users"
        const val PASSWORD_PATH = "passwords"
        const val AVATAR_PATH = "avatars"

    }

    override fun doCheckUsernameAvailable(username: String): Observable<CheckUsernameAvailabilityResp> {
        return Observable.create {
            val reference = Firebase.database.reference
            reference.child(USER_PATH).child(username).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    it.onNext(CheckUsernameAvailabilityResp(!snapshot.exists()))
                    it.onComplete()
                }

                override fun onCancelled(error: DatabaseError) {
                    it.onNext(CheckUsernameAvailabilityResp(error.message))
                    it.onComplete()
                }
            })
        }
    }

    override fun doRegister(
        username: String,
        name: String,
        password: String,
        avatar: File?
    ): Observable<UserResp> {

        return Observable.create {

            //upload image
            if (avatar != null) {
                val storage = Firebase.storage
                val storageRef = storage.reference
                val file = Uri.fromFile(avatar)
                val pathRef = storageRef.child("${AVATAR_PATH}/$username")
                pathRef.putFile(file)
                    .addOnSuccessListener { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                            val imageUrl = uri.toString()
                            Log.d(javaClass.simpleName, "Uploaded image to $imageUrl")
                            register(
                                username,
                                name = name,
                                password = password,
                                avatar = imageUrl,
                                emitter = it
                            )

                        }
                    }.addOnFailureListener(OnFailureListener { e ->
                        Log.e(javaClass.simpleName, "Upload image failed $e")
                        register(
                            username,
                            name = name,
                            password = password,
                            avatar = "",
                            emitter = it
                        )
                    })
            } else {
                register(username, name = name, password = password, avatar = "", emitter = it)
            }
        }
    }

    private fun register(
        username: String,
        name: String,
        password: String,
        avatar: String, emitter: ObservableEmitter<UserResp>
    ) {
        val hashedPassword = password.toMD5()

        val reference = Firebase.database.reference
        val userData = User(username, name, avatar)
        val updates = hashMapOf(
            "/$USER_PATH/$username" to userData,
            "/$PASSWORD_PATH/$username" to hashedPassword
        )
        reference.updateChildren(updates) { error, _ ->
            if (error == null) {
                fetchUserResp(username, emitter)
            } else {
                emitter.onNext(UserResp(error = error.message))
                emitter.onComplete()
            }
        }
    }

    override fun doLogin(username: String, password: String): Observable<UserResp> {
        val hashedPassword = password.toMD5()

        return Observable.create {
            val reference = Firebase.database.reference
            reference.child(PASSWORD_PATH).child(username).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val hashedPasswordFromServer = snapshot.getValue<String>()

                        if (hashedPasswordFromServer == hashedPassword) {
                            fetchUserResp(username, it)
                        } else {
                            it.onNext(UserResp(error = "Incorrect password"))
                            it.onComplete()
                        }
                    } else {
                        it.onNext(UserResp(error = "User is not existed"))
                        it.onComplete()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    it.onNext(UserResp(error = error.message))
                    it.onComplete()
                }
            })
        }
    }

    private fun fetchUserResp(username: String, emitter: ObservableEmitter<UserResp>) {
        val reference = Firebase.database.reference
        reference.child(USER_PATH).child(username).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue<User>()
                    emitter.onNext(UserResp(user))
                } else {
                    emitter.onNext(UserResp(error = "User is not exists"))
                }
                emitter.onComplete()
            }

            override fun onCancelled(error: DatabaseError) {
                emitter.onNext(UserResp(error = error.message))
                emitter.onComplete()
            }
        })
    }
}