package com.example.myapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.models.Product
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(private var dataset: ArrayList<Product>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(),Filterable {


    var emptylist2 = ArrayList<Product>()
    // exampleListFull . exampleList

    init {
        emptylist2 = dataset
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list : ArrayList<Product>){
        emptylist2.clear()
        emptylist2.addAll(list)
        notifyDataSetChanged()
    }


    var onItemClick1 : ((Product) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemimage : ImageView
        var itemtitle : TextView
        var itemdetail : TextView


        init {

            itemimage = view.findViewById(R.id.item_image)
            itemtitle = view.findViewById(R.id.item_title)
            itemdetail = view.findViewById(R.id.item_detail)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardlayout,parent,false)

        return ViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*holder.imageView.text = dataset[position].textView*/

        holder.itemdetail.text = "Rs. " + emptylist2[position].price.toString()
        holder.itemtitle.text = emptylist2[position].brand
//      holder.itemimage.setImageResource(emptylist2[position].thumbnail)
        Glide.with(holder.itemimage).load(emptylist2[position].thumbnail).into(holder.itemimage)

        holder.itemView.setOnClickListener {
            onItemClick1?.invoke(emptylist2[position])
        }



    }

    override fun getItemCount() = emptylist2.size

        override fun getFilter() : Filter{
            return object  : Filter(){
                override fun performFiltering(constraints: CharSequence?): FilterResults {
                    val charString = constraints?.toString() ?: ""
                    emptylist2 = if (charString.isEmpty()) {
                        dataset
                    } else {
                        val resultlist =  ArrayList<Product>()
                        for (row in dataset) {
                            if (row.brand.lowercase(Locale.getDefault())
                                    .startsWith(charString.lowercase(Locale.getDefault()),true)
                            ) {
                                resultlist.add(row)
                            }
                        }
                        resultlist


                    }
                    val filterResults = FilterResults()
                    filterResults.values = emptylist2
                    return filterResults
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun publishResults(constraints: CharSequence?, result: FilterResults) {
                    emptylist2 =
                        result.values as ArrayList<Product>
                    notifyDataSetChanged()


                }
            }
        }
}
