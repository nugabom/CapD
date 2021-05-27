package com.example.myapplication.mainPage

import com.example.myapplication.R

//카테고리 리스트 저장하는 함수
class CatList {
    //여기서 수정하면 카테고리 리스트 변함
    //2의 배수로 넣어야 함 아니면 끝에 하나 짤림

    private val catArray = arrayListOf("돼지고기", "닭고기", "한식",
            "중식", "일식*회", "아시안*양식", "면",
            "분식", "포차", "디저트", "프랜차이즈",
            "뭐가", "문제야")

    public fun getCatArray(): ArrayList<String> {
        return catArray
    }

    companion object{
        private val instance = arrayListOf("돼지고기", "닭고기", "한식",
                "중식", "일식*회", "아시안*양식", "면",
                "분식", "포차", "디저트", "프랜차이즈",
                "뭐가", "문제야")

        fun getInstance() : ArrayList<String> {
            return instance
        }

        private val catories = arrayListOf(
                CatoryType("돼지고기", R.drawable.icon_pork),
                CatoryType("닭고기", R.drawable.icon_chicken),
                CatoryType("한식", R.drawable.icon_korea),
                CatoryType("중식", R.drawable.icon_china),
                CatoryType("일식*회", R.drawable.icon_japan),
                CatoryType("분식", R.drawable.icon_easy),
                CatoryType("포차", R.drawable.icon_pocha),
                CatoryType("디저트", R.drawable.icon_desert),
                CatoryType("아시안*양식", R.drawable.icon_pizza)
        )

        fun getType(string: String) : Int {
            return catories.first { it.catory == string }.catory_image
        }
    }
}

data class CatoryType(
    val catory : String,
    val catory_image : Int
)