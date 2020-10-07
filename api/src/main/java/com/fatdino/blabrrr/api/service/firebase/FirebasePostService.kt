package com.fatdino.blabrrr.api.service.firebase

import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.responds.PostResp
import com.fatdino.blabrrr.api.service.ApiPostService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter


class FirebasePostService() : ApiPostService {

    companion object {
        const val POST_PATH = "posts"
        const val USER_POST_PATH = "user-posts"

    }


    override fun doPost(username: String, body: String, image: ByteArray?): Observable<PostResp> {
        return Observable.create {
            val reference = Firebase.database.reference

            val key = reference.child(POST_PATH).push().key
            if (key == null) {
                it.onNext(PostResp("Couldn't get push key for posts"))
                it.onComplete()
                return@create
            }

            val post = Post(key, username, body)

            val updates = hashMapOf<String, Any>(
                "/$POST_PATH/$key" to post,
                "/$USER_POST_PATH/$username/$key" to post
            )
            reference.updateChildren(updates) { error, _ ->
                if (error != null) {
                    it.onNext(PostResp(error.message))
                    it.onComplete()
                } else {
                    fetchPost(key, it)
                }
            }
        }
    }

    private fun fetchPost(key: String, emitter: ObservableEmitter<PostResp>) {
        val reference = Firebase.database.reference
        reference.child(POST_PATH).child(key)
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val post = snapshot.getValue<Post>()
                        emitter.onNext(PostResp(post))
                    } else {
                        emitter.onNext(PostResp("User is not exists"))
                    }
                    emitter.onComplete()
                }

                override fun onCancelled(error: DatabaseError) {
                    emitter.onNext(PostResp(error.message))
                    emitter.onComplete()
                }
            })
    }
}