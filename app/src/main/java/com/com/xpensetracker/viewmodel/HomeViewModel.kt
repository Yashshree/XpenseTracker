package com.com.xpensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.com.xpensetracker.model.Transaction
import com.com.xpensetracker.repository.AddCategoryRepository
import com.com.xpensetracker.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var transactionRepository: TransactionRepository) :
    ViewModel() {
    val transactionList : LiveData<List<Transaction>>
        get(){
           return transactionRepository.transactionList
        }

 val incomeAmountList : LiveData<List<Int>>
        get(){
           return transactionRepository.incomeAmountList
        }

    val expenseAmountList : LiveData<List<Int>>
        get(){
            return transactionRepository.expenseAmountList
        }

    val investmentAmountList : LiveData<List<Int>>
        get(){
            return transactionRepository.investmentAmountList
        }




}