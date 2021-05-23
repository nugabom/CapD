package com.example.myapplication.rest.Resmain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.rest.RestMain.SikdangSetting.SikdangSettingDialog

//SikdangMain_res 에서 사용
//오른쪽 사이드바에 예약 신청 내역 바인드
//예약 신청 하나 클릭시 예약 받을지 선택하는 다이얼로그 띄움

class ResMainMessageRVAdapter(var context: Context, var sikdangmainRes: SikdangMain_res): RecyclerView.Adapter<ResMainMessageRVAdapter.Holder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.res_ordermessage_line, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return sikdangmainRes.messages.size
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        public fun bind(pos:Int){
            var count = pos
            var orderId =sikdangmainRes.messages[count].orderId
            var oml_nameTV :TextView = itemView.findViewById(R.id.oml_nameTV)
            oml_nameTV.setText(sikdangmainRes.messages[count].conName)

            var oml_pnTV :TextView = itemView.findViewById(R.id.oml_pnTV)
            oml_pnTV.setText(sikdangmainRes.messages[count].pn)

            var oml_priceTV :TextView = itemView.findViewById(R.id.oml_priceTV)
            oml_priceTV.setText(sikdangmainRes.messages[count].price.toString()+" 원")

            var oml_timeTV :TextView = itemView.findViewById(R.id.oml_timeTV)
            /*
            oml_timeTV.setText(sikdangmainRes.messages[count].sh.toString()+" 시"+sikdangmainRes.messages[count].sm.toString()+" 분 ~ "+
                    sikdangmainRes.messages[count].eh.toString()+" 시"+sikdangmainRes.messages[count].em.toString()+" 분 / 예약시간:"+
                    sikdangmainRes.messages[count].y.toString()+"."+sikdangmainRes.messages[count].m.toString()+"."+sikdangmainRes.messages[count].day.toString()+"."+
                    sikdangmainRes.messages[count].h.toString()+"."+sikdangmainRes.messages[count].min.toString()+"."+sikdangmainRes.messages[count].sec.toString())*/

            oml_timeTV.setText(sikdangmainRes.messages[count].sh.toString()+" 시"+sikdangmainRes.messages[count].sm.toString()+" 분 ~ "+
                    sikdangmainRes.messages[count].eh.toString()+" 시"+sikdangmainRes.messages[count].em.toString()+" 분" )

            var oml_layout : LinearLayout= itemView.findViewById(R.id.oml_layout)
            oml_layout.setOnClickListener {
                showOrderMessageDialog(count, orderId)
            }



        }
    }


    public  fun showOrderMessageDialog(pos:Int, orderId:String){
        var customDialog = OrderMessageDialog(context, pos, orderId, sikdangmainRes)
        customDialog!!.show()
    }
}