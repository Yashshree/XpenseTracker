package com.com.xpensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.com.xpensetracker.model.Category
import com.com.xpensetracker.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.com.xpensetracker.repository.AddCategoryRepository
import com.com.xpensetracker.utils.Resource
import com.com.xpensetracker.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class AddTransactionViewModel @Inject constructor(var addCategoryRepository: AddCategoryRepository) : ViewModel(){

    fun getAllCategories(): LiveData<List<Category>> {
        val categoryMutableLiveData = MutableLiveData<List<Category>>()
        viewModelScope.launch(Dispatchers.IO){
            categoryMutableLiveData.postValue(addCategoryRepository.getAllCategories())
        }

        return  categoryMutableLiveData
    }

    fun addTransaction(transaction: Transaction): LiveData<Resource<Long>>{
        val transactionMutableData = MutableLiveData<Resource<Long>>()

        viewModelScope.launch(Dispatchers.IO) {
            transactionMutableData.postValue(Resource(Status.SUCCESS,"", addCategoryRepository.addTransaction(transaction)))
        }

       return transactionMutableData
    }



}