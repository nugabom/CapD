package com.example.sikdangbook_rest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ChoiceMySikdangRVAdapter(var context: Context):RecyclerView.Adapter<ChoiceMySikdangRVAdapter.Holder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.res_sikdang_line, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }



    public fun getDate(){

    }


    //데이터베이스 접속해 식당 정보 불러온다.
    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        public fun bind(pos:Int) {
            var sikdangImage:ImageView = itemView.findViewById(R.id.sikdangImage)
            var sikdangLine_NameTV:TextView = itemView.findViewById(R.id.sikdangLine_NameTV)
            var sikdangLine_addr:TextView = itemView.findViewById(R.id.sikdangLine_addr)
            var sikdangLine_number:TextView = itemView.findViewById(R.id.sikdangLine_number)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SikdangMain_res::class.java)
                context.startActivity(intent)

            }
        }

    }


}