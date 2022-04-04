package com.com.xpensetracker.adapters

import android.R.color
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.com.xpensetracker.R
import com.com.xpensetracker.databinding.SingleItemCategoryLayoutBinding
import com.com.xpensetracker.interfaces.OnListItemClickListener
import com.com.xpensetracker.model.Category


class CategoryAdapter(var listener: OnListItemClickListener,var categoryList: ArrayList<Category>, var context: Context) :
    RecyclerView.Adapter<CategoryAdapter.CustomViewHolder>() {

    class CustomViewHolder(var binding: SingleItemCategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding =
            SingleItemCategoryLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {


        with(holder.binding) {

            holder.binding.txtCategory.setOnClickListener {
                Thread.sleep(250)
               // notifyItemChanged(position)
                categoryList.forEach { it.isSelected=false }
                        categoryList[position].isSelected = true
                notifyDataSetChanged()

                listener.onListItemClick(categoryList[position])
            }

            with(categoryList[position]) {
                holder.binding.txtCategory.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                txtCategory.text = this.title

                txtCategory.setCompoundDrawablesWithIntrinsicBounds(0, this.image, 0, 0);

                if(this.isSelected){
                    for (drawable in holder.binding.txtCategory.compoundDrawablesRelative) {
                        if (drawable != null) {
                            drawable.colorFilter =
                                PorterDuffColorFilter(
                                    ContextCompat.getColor(
                                        holder.binding.txtCategory.getContext(),
                                        R.color.white
                                    ), PorterDuff.Mode.SRC_IN
                                )
                        }
                    }

                    holder.binding.txtCategory.setTextColor( ContextCompat.getColor(
                        context,
                       R.color.white
                    ))
                    holder.binding.txtCategory.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            categoryList[position].color
                        )
                    )
                }else{
                    for (drawable in holder.binding.txtCategory.compoundDrawablesRelative) {
                        if (drawable != null) {
                            drawable.colorFilter =
                                PorterDuffColorFilter(
                                    ContextCompat.getColor(
                                        holder.binding.txtCategory.getContext(),
                                        categoryList[position].color
                                    ), PorterDuff.Mode.SRC_IN
                                )
                        }
                    }

                    holder.binding.txtCategory.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                           R.color.white
                        )
                    )
                }



                //txtCategory.setBackgroundColor(R.color.color)

                txtCategory.foreground =
                    AppCompatResources.getDrawable(context,categoryList[position].rippleBackground)
            }
        }
    }

    override fun getItemCount(): Int = categoryList.size


}