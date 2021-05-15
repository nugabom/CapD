package com.example.myapplication.rest.RestMain.SikdangSetting

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication._Ingredient
import com.example.myapplication.bookActivity.MenuData

//EditMenuDialog 에서 사용
//메뉴 여러개 리사이클러뷰에 넣어줌
class EditMenuRVAdapter(var context: Context, val sikdangNum:String): RecyclerView.Adapter<EditMenuRVAdapter.Holder>() {

    var menuDataAL = ArrayList<MenuData>()

    init{
        setMenuData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.res_menuline, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        //메뉴 개수
        //Log.d("확인 EditMenuRVAdapter", menuDataAL.size.toString())
        return menuDataAL.size
        //return 2
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var menuImage : ImageView = itemView.findViewById(R.id.ml_imageView)
        var menuName : TextView = itemView.findViewById(R.id.ml_menuNameTV)
        var menuExp : TextView = itemView.findViewById(R.id.ml_menuExpTV)
        var menuPrice : TextView = itemView.findViewById(R.id.ml_menuPriceTV)
        var menuIng : TextView = itemView.findViewById(R.id.ml_ingTV)

        public fun bind(pos:Int){
            Log.d("확인 BeforeIngRVAdapter","###############################")
            menuImage.setBackgroundResource(R.drawable.food_placeholder)
            menuName.setText(menuDataAL[pos].product)
            menuExp.setText(menuDataAL[pos].product)
            menuPrice.setText(menuDataAL[pos].price.toString()+" 원")
            var ing=""
            for (i in 0 until menuDataAL[pos].ingredients.size){
                ing +=menuDataAL[pos].ingredients[i].ing
                ing+=":"
                ing +=menuDataAL[pos].ingredients[i].country
                ing += " "
            }
            menuIng.setText(ing)

            itemView.setOnClickListener {
                showMenuEditDialog(pos)
            }
        }
    }

    private fun setMenuData(){
        var ing1= _Ingredient("보리", "땅")
        var ing2= _Ingredient("파인애플", "땅")
        var ingAL=ArrayList<_Ingredient>()
        ingAL.add(ing1)
        ingAL.add(ing2)
        menuDataAL.add(MenuData("맥콜", "image_url",1000, "ㅁㅁㅁㅁ", ingAL))
        menuDataAL.add(MenuData("파인애플피자","image_url", 15000, "파아인애프으응ㄹㅁ", ingAL))

    }

    public fun showMenuEditDialog(pos:Int){
        var customDialog = MenuEditDialog(context, sikdangNum, pos, menuDataAL[pos])
        customDialog!!.show()
    }


}