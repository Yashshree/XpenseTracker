package com.com.xpensetracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.com.xpensetracker.R
import com.com.xpensetracker.databinding.SingleItemTransactionBinding
import com.com.xpensetracker.interfaces.OnListItemClickListener
import com.com.xpensetracker.interfaces.OnTransactionItemClickListener
import com.com.xpensetracker.model.Transaction
import com.com.xpensetracker.utils.TransactionType

class TransactionAdapter(var transactionList: ArrayList<Transaction>, var context: Context,var listener:OnTransactionItemClickListener) :
    RecyclerView.Adapter<TransactionAdapter.CustomViewHolder>() {

    class CustomViewHolder(val binding: SingleItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding =
            SingleItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int){

        with(holder) {
            with(transactionList[position]) {

                binding.rootView.setOnClickListener {
                    listener.onItemClick(transactionList[position])
                }

                binding.txtTransactionName.text = note
                binding.txtAmount.text = amount.toString()

                when (type) {
                    TransactionType.SAVING_INVESTMENT -> {
                        binding.txtTransactionName.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorBlue
                            )
                        )

                        binding.txtAmount.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorBlue
                            )
                        )

                        if(note=="")
                        binding.txtTransactionName.text = context.getString(R.string.txt_saving)
                    }

                    TransactionType.INCOME -> {
                        binding.txtTransactionName.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorGreen
                            )
                        )

                        binding.txtAmount.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorGreen
                            )
                        )

                        if(note=="")
                            binding.txtTransactionName.text = context.getString(R.string.txt_income)
                    }

                    TransactionType.EXPENSE -> {
                        binding.txtTransactionName.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorRed
                            )
                        )

                        binding.txtAmount.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorRed
                            )
                        )

                        if(note=="")
                            binding.txtTransactionName.text = expenseType
                    }


                }
            }
        }
    }

    override fun getItemCount(): Int = transactionList.size
}