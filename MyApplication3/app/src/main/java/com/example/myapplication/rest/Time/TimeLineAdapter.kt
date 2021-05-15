package com.example.sikdangbook_rest.Time

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
import java.text.SimpleDateFormat
import java.util.*

class TimeLineAdapter(var context: Context, val timeSelectDialog: TimeSelectDialog): RecyclerView.Adapter<TimeLineAdapter.Holder>() {
    var tempTimeClass = TempTimeClass(1111)

    var timeNumMax = tempTimeClass.getTimeArrayList().size
    var timePoint = timeSet()
    var vartimePoint=timePoint

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {


        val view = LayoutInflater.from(context).inflate(R.layout.res_timeline, parent, false)
        return Holder(view)
    }

    public fun timeSet():Int{
        val time = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("kk:mm")
        val curTime = dateFormat.format(Date(time))
        var timeString:String=""
        timeString=timeString+curTime[0]+curTime[1]+curTime[3]+curTime[4]

        var i = 0
        while (i<tempTimeClass.getTimeArrayList().size){
            if(timeString <= tempTimeClass.getTimeArrayList()[i]){
                break
            }
            //Log.d("확인 time 현재 다음 ", timeString+" "+bookTimeData.getTimeArrayList()[i])
            i++
        }
        if(i==tempTimeClass.getTimeArrayList().size){
            //이경우는 하루 영업이 끝남
            val myToast = Toast.makeText(context, "영업끝", Toast.LENGTH_SHORT).show()
            Log.d("확인 time 현재 다음 ", timeString+"영업끝")
        }
        //Log.d("확인 time 현재 다음 ", timeString+" "+bookTimeData.getTimeArrayList()[i])


        //Log.d("확인 time", timeString+" "+bookTimeData.getTimeArrayList()[0])

        return i
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        var i =timeNumMax - timePoint
        //Log.d("확인 getItemCount ", i.toString()+" "+(i/2).toString())
        return (i+1)/4
    }

    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        var callTimePoint =vartimePoint
        public fun bind(){
            //this.setIsRecyclable(false)

            var button1: Button = itemView.findViewById(R.id.timebtn1)
            val timeText = tempTimeClass.getTimeArrayList()[vartimePoint]
            button1.setText(timeText)
            if (tempTimeClass.getIsFull()[vartimePoint]==false){//이시간대 예약이 꽉찬 경우
                button1.setBackgroundColor(Color.RED)
                button1.setOnClickListener {
                    val myToast = Toast.makeText(context, "예약이 가득 찼습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else{//예약이 빈 경우
                button1.setOnClickListener {
                    tempTimeClass.setBookTime(timeText)
                }
            }

            button1.setOnClickListener {
                if (button1.text.toString()!=""){
                    timeSelectDialog.setTimeNum(button1.text.toString())
                }
            }

            //Log.d("확인 왼쪽 버튼 바인드 ", timeText+vartimePoint.toString())

            //오른쪽 버튼 바인드
            var button2: Button = itemView.findViewById(R.id.timebtn2)
            vartimePoint+=1
            if (vartimePoint<timeNumMax){
                button2.setText(tempTimeClass.getTimeArrayList()[vartimePoint])
                if (tempTimeClass.getIsFull()[vartimePoint]==false){//예약이 가득 찬 경우
                    button2.setBackgroundColor(Color.RED)
                    button2.setOnClickListener {
                        val myToast = Toast.makeText(context, "예약이 가득 찼습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                else{//예약이 빈 경우
                    button2.setOnClickListener {
                        tempTimeClass.setBookTime(timeText)
                        //Log.d("확인 buttonSetOnClickListner ", "3")
                    }
                }
            }
            else{
                button2.setText("")
            }
            //Log.d("확인 가운데 버튼 바인드 ", timeText+vartimePoint.toString())

            button2.setOnClickListener {
                if (button2.text.toString()!=""){
                    timeSelectDialog.setTimeNum(button2.text.toString())
                }
            }



            var button3: Button = itemView.findViewById(R.id.timebtn3)
            vartimePoint+=1
            if (vartimePoint<timeNumMax){
                button3.setText(tempTimeClass.getTimeArrayList()[vartimePoint])
                if (tempTimeClass.getIsFull()[vartimePoint]==false){//예약이 가득 찬 경우
                    button3.setBackgroundColor(Color.RED)
                    button3.setOnClickListener {
                        val myToast = Toast.makeText(context, "예약이 가득 찼습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                else{//예약이 빈 경우
                    button3.setOnClickListener {
                        tempTimeClass.setBookTime(timeText)
                        //Log.d("확인 buttonSetOnClickListner ", "3")
                    }
                }
            }
            else{
                button3.setText("")
            }
            //Log.d("확인 오른쪽 버튼 바인드 ", timeText+vartimePoint.toString())

            button3.setOnClickListener {
                if (button3.text.toString()!=""){
                    timeSelectDialog.setTimeNum(button3.text.toString())
                }
            }

            var button4: Button = itemView.findViewById(R.id.timebtn4)
            vartimePoint+=1
            if (vartimePoint<timeNumMax){
                button4.setText(tempTimeClass.getTimeArrayList()[vartimePoint])
                if (tempTimeClass.getIsFull()[vartimePoint]==false){//예약이 가득 찬 경우
                    button4.setBackgroundColor(Color.RED)
                    button4.setOnClickListener {
                        val myToast = Toast.makeText(context, "예약이 가득 찼습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                else{//예약이 빈 경우
                    button4.setOnClickListener {
                        tempTimeClass.setBookTime(timeText)
                        //Log.d("확인 buttonSetOnClickListner ", "3")
                    }
                }
                vartimePoint+=1
            }
            else{
                button4.setText("")
            }

            button4.setOnClickListener {
                if (button4.text.toString()!=""){
                    timeSelectDialog.setTimeNum(button4.text.toString())
                }
            }



        }



    }


}