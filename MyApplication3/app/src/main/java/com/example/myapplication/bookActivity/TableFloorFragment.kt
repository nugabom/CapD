package com.example.myapplication.bookActivity

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_app_setting.view.*
import okhttp3.internal.parseCookie

class TableFloorFragment(val floor_name : String, val table_info : ArrayList<Table>)
    : Fragment()
{
    lateinit var floorText : TextView
    lateinit var floor_layout : ConstraintLayout
    lateinit var floor_background : ImageView
    var tableSelectedByUser : HashMap<String, Table> = hashMapOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.booktable_floorfragment, container, false)

        floorText = view.findViewById(R.id.floorText)
        floorText.text = floor_name
        floor_background = view.findViewById(R.id.floor_layout)
        floor_layout = view.findViewById(R.id.floorLayout)
        floor_background.setImageResource(R.drawable.store_layout_example)
        val table_to_imageView = TableToImageView()
        for (table in table_info) {
            floor_layout.addView(table_to_imageView.build(table))
        }
        return view
    }

    inner class TableToImageView {
        fun build(table : Table) : ImageView {
            var result_image = ImageView(context)
            result_image.tag = table
            makeShape(table, result_image)
            setLayoutParam(table, result_image)
            return result_image
        }

        private fun setLayoutParam(table : Table, image : ImageView) {
            var layoutParam = ConstraintLayout.LayoutParams(dpToPx(table.width), dpToPx(table.height))
            layoutParam.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParam.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParam.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParam.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParam.horizontalBias = table.x
            layoutParam.verticalBias = table.y

            image.layoutParams = layoutParam
        }

        private fun makeShape(table : Table, image : ImageView) {
            if(table.isBooked == true) {
                image.setOnClickListener {
                    Toast.makeText(context, "테이블은 이미 예약되었습니다.", Toast.LENGTH_SHORT).show()
                }
                if(table.shape.equals("circle")) {
                    image.background = resources.getDrawable(R.drawable.button_round_red, null)
                } else {
                    image.setBackgroundResource(R.drawable.button_rect_red)
                }
            } else {
                image.setOnClickListener {
                    showDialog(table)
                }
                if(table.shape.equals("circle")) {
                    image.background = resources.getDrawable(R.drawable.button_round_gray, null)
                } else {
                    image.setBackgroundResource(R.drawable.button_rect_gray)
                }
            }



        }

        private fun dpToPx(length : Int) : Int{
            var density:Float = resources.displayMetrics.density
            return Math.round(length.toFloat() * density)
        }
    }

    fun showDialog(table: Table){
        var customDialog = BookPersonDialog(requireContext(), this,table)
        customDialog!!.show()
    }

    fun setResult(selectedTable : Table) {
        tableSelectedByUser.put(selectedTable.id, selectedTable)
        for (view in floor_layout.children) {
            if (view.tag is Table) {
                val table = view.tag as Table
                if(table.id == selectedTable.id) {
                    when (table.shape) {
                        "circle" -> view.setBackgroundResource(R.drawable.button_round_green)
                        else -> view.setBackgroundResource(R.drawable.button_rect_green)
                    }
                }
            }
        }
    }

    fun invalid(invalid_table : Table) {
        if(!tableSelectedByUser.containsKey(invalid_table.id)) return
        Log.d("TableFloorFragment", "invalid: tag = ${invalid_table.id}")
        tableSelectedByUser.remove(invalid_table.id)
        for (view in floor_layout.children) {
            if (view.tag is Table) {
                val table = view.tag as Table
                if(table.id == invalid_table.id) {
                    when(table.shape) {
                        "circle" -> view.setBackgroundResource(R.drawable.button_round_gray)
                        else -> view.setBackgroundResource(R.drawable.button_rect_gray)
                    }
                }
            }
        }
    }
}