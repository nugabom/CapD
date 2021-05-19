package com.example.myapplication.rest.RestMain.SikdangSetting.BookTimeSetting

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.rest.Time.TempTimeClass
import com.example.sikdangbook_rest.Time.TimeLineAdapter
import com.example.sikdangbook_rest.Time.TimeSelectDialog
import java.text.SimpleDateFormat
import java.util.*

class BookTimeSettingRVAdapter(var context: Context, val bookTimeSettingDialog: BookTimeSettingDialog): RecyclerView.Adapter<BookTimeSettingRVAdapter.Holder>() {

    var timeNumMax = bookTimeSettingDialog.tempTimeClass.getTimeArrayList().size
    //var timePoint = timeSet()
    //var vartimePoint=timePoint
    var vartimePoint=0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookTimeSettingRVAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.res_timeline, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        var i =timeNumMax
        //Log.d("확인 getItemCount ", i.toString()+" "+(i/2).toString())
        return (i+1)/4

    }

    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        //var callTimePoint =vartimePoint
        public fun bind(){
            //this.setIsRecyclable(false)

            var button1: Button = itemView.findViewById(R.id.timebtn1)
            val timeText = bookTimeSettingDialog.tempTimeClass.getTimeArrayList()[vartimePoint]
            button1.setText(timeText)


            button1.setOnClickListener {
                if (button1.text.toString()!=""){
                    bookTimeSettingDialog.deleteTime(button1.text.toString())
                }
            }

            //Log.d("확인 왼쪽 버튼 바인드 ", timeText+vartimePoint.toString())

            //오른쪽 버튼 바인드
            var button2: Button = itemView.findViewById(R.id.timebtn2)
            vartimePoint+=1
            if (vartimePoint<timeNumMax){
                button2.setText(bookTimeSettingDialog.tempTimeClass.getTimeArrayList()[vartimePoint])

            }
            else{
                button2.setText("")
            }
            //Log.d("확인 가운데 버튼 바인드 ", timeText+vartimePoint.toString())

            button2.setOnClickListener {
                if (button2.text.toString()!=""){
                    bookTimeSettingDialog.deleteTime(button2.text.toString())
                }
            }



            var button3: Button = itemView.findViewById(R.id.timebtn3)
            vartimePoint+=1
            if (vartimePoint<timeNumMax){
                button3.setText(bookTimeSettingDialog.tempTimeClass.getTimeArrayList()[vartimePoint])
            }
            else{
                button3.setText("")
            }
            //Log.d("확인 오른쪽 버튼 바인드 ", timeText+vartimePoint.toString())

            button3.setOnClickListener {
                if (button3.text.toString()!=""){
                    bookTimeSettingDialog.deleteTime(button3.text.toString())
                }
            }

            var button4: Button = itemView.findViewById(R.id.timebtn4)
            vartimePoint+=1
            if (vartimePoint<timeNumMax){
                button4.setText(bookTimeSettingDialog.tempTimeClass.getTimeArrayList()[vartimePoint])
                vartimePoint+=1
            }
            else{
                button4.setText("")
            }

            button4.setOnClickListener {
                if (button4.text.toString()!=""){
                    bookTimeSettingDialog.deleteTime(button4.text.toString())
                }
            }



        }

    }



    public fun timeSet():Int{
        val time = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("kk:mm")
        val curTime = dateFormat.format(Date(time))
        var timeString:String=""
        timeString=timeString+curTime[0]+curTime[1]+curTime[3]+curTime[4]

        var i = 0
        while (i<bookTimeSettingDialog.tempTimeClass.getTimeArrayList().size){
            if(timeString <= bookTimeSettingDialog.tempTimeClass.getTimeArrayList()[i]){
                break
            }
            //Log.d("확인 time 현재 다음 ", timeString+" "+bookTimeData.getTimeArrayList()[i])
            i++
        }
        if(i==bookTimeSettingDialog.tempTimeClass.getTimeArrayList().size){
            //이경우는 하루 영업이 끝남
            val myToast = Toast.makeText(context, "영업끝", Toast.LENGTH_SHORT).show()
            Log.d("확인 time 현재 다음 ", timeString+"영업끝")
        }
        //Log.d("확인 time 현재 다음 ", timeString+" "+bookTimeData.getTimeArrayList()[i])


        //Log.d("확인 time", timeString+" "+bookTimeData.getTimeArrayList()[0])

        return i
    }


}