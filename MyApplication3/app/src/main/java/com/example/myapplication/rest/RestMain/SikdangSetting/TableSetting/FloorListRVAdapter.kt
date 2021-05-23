package com.example.myapplication.rest.RestMain.SikdangSetting.TableSetting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.rest.RestMain.SikdangSetting.AddIngRVAdapter
import com.example.myapplication.rest.RestMain.SikdangSetting.IngAddDialog

//TableFloorSettingDialog 에서 사용
//각 층 써져있는 버튼 바인드
//버튼 누르면 그 창에 관한 다이얼로그 : TableSettingDialog 띄움

class FloorListRVAdapter(var context: Context, var tableFloorSettingDialog: TableFloorSettingDialog): RecyclerView.Adapter<FloorListRVAdapter.Holder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorListRVAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.generalitem_button, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: FloorListRVAdapter.Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return tableFloorSettingDialog.tableData.floorList.size


    }


    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        public fun bind(pos:Int){
            var floorButton: Button = itemView.findViewById(R.id.gButton)
            var floorSeq = pos
            floorButton.setText(tableFloorSettingDialog.tableData.floorList[floorSeq].toString() + "층")

            floorButton.setOnClickListener {
                tableFloorSettingDialog.showTableSettingDialog(floorSeq, tableFloorSettingDialog.tableData.floorList[floorSeq])
            }
        }



    }
}