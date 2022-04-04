package com.com.xpensetracker.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.com.xpensetracker.R
import com.com.xpensetracker.adapters.TransactionAdapter
import com.com.xpensetracker.databinding.FragmentHomeBinding
import com.com.xpensetracker.model.Transaction
import com.com.xpensetracker.utils.getDaysLeftForMonthToEnd
import com.com.xpensetracker.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener {
     var _binding: FragmentHomeBinding ? =null
 val binding get() = _binding!!
    val homeViewModel : HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding =  FragmentHomeBinding.bind(view).root
        initializeView()
    }

    private fun initializeView() {
        Log.e("Home Activity=======","called")

        val currentDate = Calendar.getInstance().time

        val df = SimpleDateFormat("MMMM YYYY", Locale.getDefault());

        binding.txtHomeHeadMonth.text = df.format(currentDate)

        binding.txtDaysLeftToCompleteMonth.text = String.format("%s Days Left ",
            getDaysLeftForMonthToEnd().toString())

        binding.layoutEmptyScreen.txtAddTransaction.setOnClickListener(this)

        binding.fabAddTransaction.setOnClickListener(this)

        val layoutManager = LinearLayoutManager(requireActivity())

        homeViewModel.transactionList.observe(requireActivity(), Observer {
            if(it.count()!=0) {

                showPopulatedScreenLayout()

                binding.recyclerViewTransaction.adapter =
                    TransactionAdapter(ArrayList<Transaction>(it), requireActivity())
                binding.recyclerViewTransaction.layoutManager = layoutManager

            }else{
                showEmptyScreenLayout()
            }
        })


        homeViewModel.incomeAmountList.observe(requireActivity(), Observer {
            if(it.count()!=0) {
                binding.txtAmountAvailable.text= it.sum().toString()


            }
        })

        homeViewModel.expenseAmountList.observe(requireActivity(), Observer {
            if(it.count()!=0) {
                binding.txtExpenseAmount.text= it.sum().toString()


            }
        })

        homeViewModel.investmentAmountList.observe(requireActivity(), androidx.lifecycle.Observer {
            if(it.count()!=0) {
                binding.txtInvestedAmount.text= it.sum().toString()


            }
        })

    }

    private fun showPopulatedScreenLayout() {
        binding.mainLayout.visibility= View.VISIBLE
        binding.layoutEmptyScreen.layoutEmptyScreen.visibility = View.GONE
        binding.fabAddTransaction.visibility=View.VISIBLE
    }

    private fun showEmptyScreenLayout() {
        binding.mainLayout.visibility= View.GONE
        binding.layoutEmptyScreen.layoutEmptyScreen.visibility = View.VISIBLE
        binding.fabAddTransaction.visibility=View.GONE
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.txtAddTransaction,R.id.fabAddTransaction->{
                goToAddTransactionActivity()
            }
        }
    }

    private fun goToAddTransactionActivity() {
        val intent= Intent(requireActivity(),AddTransactionActivity::class.java)
        startActivity(intent)
    }
}