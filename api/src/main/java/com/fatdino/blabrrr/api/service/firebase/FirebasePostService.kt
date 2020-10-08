package com.fatdino.blabrrr.api.service.firebase

import android.util.Log
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.responds.PostResp
import com.fatdino.blabrrr.api.service.ApiPostService
import com.google.firebase.database.ChildEventListener
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

    override fun getLivePosts(): Observable<PostResp> {
        return Observable.create {
            val reference = Firebase.database.reference
            reference.child(POST_PATH).orderByKey().limitToLast(100)
                .addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        Log.e(javaClass.simpleName, "@onChildAdded:" + snapshot.key!!)

                        val post = snapshot.getValue<Post>()
                        it.onNext(PostResp(post, true))
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                        Log.d(javaClass.simpleName, "@onChildChanged: ${snapshot.key}")

                        val post = snapshot.getValue<Post>()
                        it.onNext(PostResp(post, false))
                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {
                        Log.e(javaClass.simpleName, "@onChildRemoved: ${snapshot.key}")
                        val post = snapshot.getValue<Post>()
                        it.onNext(PostResp(post, isNew = false, isDeleted = true))
                    }

                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                        Log.e(javaClass.simpleName, "@onChildMoved: ${snapshot.key}")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e(javaClass.simpleName, "@onCancelled: ${error.message}")
                        it.onNext(PostResp(error = error.message))
                        it.onComplete()
                    }
                })
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
                        emitter.onNext(PostResp(error = "User is not exists"))
                    }
                    emitter.onComplete()
                }

                override fun onCancelled(error: DatabaseError) {
                    emitter.onNext(PostResp(error = error.message))
                    emitter.onComplete()
                }
            })
    }
}