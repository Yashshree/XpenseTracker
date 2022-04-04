package com.com.xpensetracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.com.xpensetracker.datastore.UserPreferences
import com.com.xpensetracker.model.Category
import com.com.xpensetracker.model.User
import com.com.xpensetracker.repository.AddCategoryRepository
import com.com.xpensetracker.utils.Resource
import com.com.xpensetracker.utils.Status
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@HiltViewModel
class LoginViewModel @Inject constructor(
    val firebaseReference: DatabaseReference,
    val userPreferences: UserPreferences,
    var addCategoryRepository: AddCategoryRepository
) : ViewModel() {
    init {

    }
    fun login(name: String): LiveData<Resource<User>> {
        val loginStatusMutableLiveData = MutableLiveData<Resource<User>>()
        viewModelScope.launch {
            firebaseReference.child("users").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.children.count() != 0 )
                    {
                        var user: User? = null
                        user = register(name)

                        saveUserDataInDb(name)

                         loginStatusMutableLiveData.postValue(
                            Resource(
                                Status.SUCCESS,
                                null,
                                user
                            )
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("error", error.message)
                    loginStatusMutableLiveData.postValue(
                        Resource(
                            Status.ERROR,
                            error.message,
                            null
                        )
                    )
                }

            })
        }

        return loginStatusMutableLiveData
    }

    private fun saveUserDataInDb(name: String) {
        viewModelScope.launch {
            userPreferences.saveIsActive(true)
            userPreferences.saveUserName(name)
        }
    }

    private fun register(name: String): User {
        val id = UUID.randomUUID()
        val ref = Firebase.database.getReference("users/${id}")
        val currentDate = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        val user =
            User(id = id.toString(), name = name, date = df.format(currentDate), isActive = true)
        ref.setValue(
            user
        )
        return user
    }

    fun insertAllCategory(categories: List<Category>) {
        viewModelScope.launch(Dispatchers.IO){
            addCategoryRepository.insertAllCategories(categories)
        }
    }

    fun getAllCategories():LiveData<List<Category>>{
        val categoryMutableLiveData = MutableLiveData<List<Category>>()
        viewModelScope.launch(Dispatchers.IO){
            categoryMutableLiveData.postValue(addCategoryRepository.getAllCategories())
        }

        return  categoryMutableLiveData
    }
}