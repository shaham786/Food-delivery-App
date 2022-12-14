package com.example.myapplication

import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter2 (private val dataset : ArrayList<Fooddata>):
    RecyclerView.Adapter<RecyclerAdapter2.ViewHolder>(), Filterable{

    var emptylist = ArrayList<Fooddata>()
    // exampleListFull . exampleList

    init {
        emptylist = dataset as ArrayList<Fooddata>
    }

    var onItemClick : ((Fooddata) -> Unit)? = null


    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val itemimage : ImageView
        var itemtitle : TextView
        var itemdetail : TextView


        init {

            itemimage = view.findViewById(R.id.item_image)
            itemtitle = view.findViewById(R.id.item_title)
            itemdetail = view.findViewById(R.id.item_detail)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter2.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardlayout2,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter2.ViewHolder, position: Int) {
        holder. itemdetail.text = emptylist[position].textView
        holder.itemtitle.text = emptylist[position].textView2
        holder.itemimage.setImageResource(emptylist[position].imageView)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(emptylist[position])
        }
    }



    override fun getItemCount() = emptylist.size

    override fun getFilter() : Filter {
        return object  : Filter(){
            override fun performFiltering(constraints: CharSequence?): FilterResults {
                val charString = constraints?.toString() ?: ""
                if (charString.isEmpty()) {
                    emptylist = dataset as ArrayList<Fooddata>
                } else {
                    val resultlist =  ArrayList<Fooddata>()
                    for (row in dataset!!) {
                        if (row.textView2.toLowerCase()
                                .startsWith(charString.toString().toLowerCase(),true)
                        ) {
                            resultlist?.add(row)
                        }
                    }
                    emptylist = resultlist


                }
                val filterResults = FilterResults()
                filterResults.values = emptylist
                return filterResults
            }

            override fun publishResults(constraints: CharSequence?, result: FilterResults) {
                emptylist =
                    result.values as ArrayList<Fooddata> /* = java.util.ArrayList<com.example.myapplication.Fooddata> */
                notifyDataSetChanged()


            }
        }
    }


}