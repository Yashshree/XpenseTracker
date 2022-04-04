package com.com.xpensetracker.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.com.xpensetracker.R
import com.com.xpensetracker.adapters.CategoryAdapter
import com.com.xpensetracker.databinding.ActivityAddTransactionBinding
import com.com.xpensetracker.interfaces.OnListItemClickListener
import com.com.xpensetracker.model.Category
import com.com.xpensetracker.model.Transaction
import com.com.xpensetracker.utils.Status
import com.com.xpensetracker.utils.TransactionType
import com.com.xpensetracker.viewmodel.AddTransactionViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddTransactionActivity : AppCompatActivity(), View.OnClickListener,OnListItemClickListener {
    lateinit var binding: ActivityAddTransactionBinding
    val addTransactionViewModel: AddTransactionViewModel by viewModels()

    var transactionType : TransactionType ? = null
     var category:Category ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        binding.btnAddTransaction.setOnClickListener(this)

        addTransactionViewModel.getAllCategories().observe(this, androidx.lifecycle.Observer {
            val layoutManager = GridLayoutManager(this, 3)
            binding.recyclerViewCategory.layoutManager = layoutManager
            binding.recyclerViewCategory.adapter = CategoryAdapter(this,ArrayList<Category>(it), this)
        })

        binding.txtExpense.setOnClickListener(this)
        binding.txtIncome.setOnClickListener(this)
        binding.txtInvestment.setOnClickListener(this)

        binding.imgClose.setOnClickListener(this)
    }

    fun hideCategoryLayout(value: Boolean) {
        binding.layoutCategory.visibility = if (value) View.GONE else View.VISIBLE
    }

    private fun setTransactionTypeView(
        isExpense: Boolean = false,
        isIncome: Boolean = false,
        isInvestment: Boolean = false
    ) {
        Thread.sleep(250)
        if (isInvestment) {
            binding.txtInvestment.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.txtInvestment.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorBlueRipple
                )
            )
            binding.txtInvestment.setTextColor(ContextCompat.getColor(this, R.color.white))

        } else {
            binding.txtInvestment.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorBlueRipple
                )
            )
            binding.txtInvestment.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }
        if (isIncome) {
            binding.txtIncome.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.txtIncome.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorGreenRipple
                )
            )
        } else {
            binding.txtIncome.setTextColor(ContextCompat.getColor(this, R.color.colorGreenRipple))
            binding.txtIncome.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }
        if (isExpense) {
            binding.txtExpense.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.txtExpense.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorRedRipple
                )
            )
        } else {
            binding.txtExpense.setTextColor(ContextCompat.getColor(this, R.color.colorRedRipple))
            binding.txtExpense.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.imgClose ->{
                finish()
            }

            R.id.btnAddTransaction ->{
                if(transactionType==null||(transactionType==TransactionType.EXPENSE && category!!.categoryId==0L)
                    ||binding.edtAmount.text.toString().isEmpty())
                {
                    Snackbar.make(binding.rootView, getString(R.string.txt_please_enter), Snackbar.LENGTH_SHORT)
                        .show()
                }
                        else
                addTransactionToDatabase()
            }

            R.id.txtExpense -> {
                transactionType = TransactionType.EXPENSE
                setTransactionTypeView(isExpense = true)
                hideCategoryLayout(false)
            }

            R.id.txtIncome -> {
                transactionType = TransactionType.INCOME
                setTransactionTypeView(isIncome = true)
                hideCategoryLayout(true)
            }

            R.id.txtInvestment -> {
                transactionType = TransactionType.SAVING_INVESTMENT
                setTransactionTypeView(isInvestment = true)
                hideCategoryLayout(true)
            }
        }
    }

    private fun addTransactionToDatabase() {
        val currentDate = Calendar.getInstance().time

        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        addTransactionViewModel.addTransaction(Transaction(type = transactionType!!,note = binding.edtAddNote.text.toString(),
            amount = binding.edtAmount.text.toString().toInt(),
            expenseCategoryId =if(category!=null) category!!.categoryId else 0,
            expenseType = if(category!=null) category!!.title else "",
            date = df.format(currentDate)
        )).observe(this, androidx.lifecycle.Observer {
            if(it.status==Status.SUCCESS)
            {
                Toast.makeText(this, getString(R.string.txt_transaction_added), Toast.LENGTH_SHORT).show()
                finish()

            }
        })
    }

    override fun onListItemClick(clickedCategory: Category) {
        category = clickedCategory
    }
}