package com.example.myapplication.rest.RestMain.SikdangSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.myapplication.R

//EditSikdangDialog 에서 사용
//식당 설명 수정하는 다이얼로그
class EditSikdangExpDialog(context: Context, val sikdangNum:String): Dialog(context) {

    lateinit var sikdangExpET:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_editsikdangexp_dialog)
        sikdangExpET=findViewById(R.id.sikdangExpET)

        setSikdangExp()


        var eseCloseBtn: Button =findViewById(R.id.eseCloseBtn)
        eseCloseBtn.setOnClickListener {
            this.dismiss()
        }

        var eseChangeBtn:Button = findViewById(R.id.eseChangeBtn)
        eseChangeBtn.setOnClickListener {
            changeSikdangExp()
        }
    }


    //데이터베이스 접속하여 식당 설명 불러온다.
    private fun setSikdangExp(){
        sikdangExpET.setText("bhc의 bi는 배달중심 브랜드인 레귤러 타입 매장과 내점과 배달영업을 함께 할 수 있는 비어존 타입 매장으로 나뉘어집니다.\n" +
                "레귤러 타입과 비어존 타입 모두 bhc의 통합된 로고를 활용하여 브랜드 이미지를 강화하였습니다.\n깨끗한 치킨 BHC는 나쁜 콜레스테롤 수치를 저하시키고 혈액순환을 돕는 불포화지방산과 비타민E가 풍부한 고올레산 해바라기유를 튀김유로 사용하여 소비자들의 건강보호에 앞장서고 있습니다.\n" +
                "그냥 해바라기유 NO! 고올레산 해바라기유 YES!\n" +
                "웰빙 트렌드에 적합한 유지류로 사랑받고 있는 해바라기유.하지만, BHC의 튀김유는 그냥 해바라기유가 아닙니다.\n" +
                "고올레산 해바라기유는 좋은 콜레스테롤을 높여주고 나쁜 콜레스테롤은 낮추어주는 단일 불포화 지방산의 함량이 일반 해바라기유보다\n" +
                "3배 이상 높아 맛과 영양이 한층 강화된 웰빙 유지류입니다.\n" +
                "BHC가 고올레산 해바라기유를 택한 이유\n" +
                "해바라기 씨처럼 고소하고 담백한 맛과 향이 뛰어납니다.\n" +
                "체지방 증가의 원인이 되는 포화지방산 함량이 적고, 트랜스지방 생성이 거의 없습니다.\n" +
                "다른 식물성 유지보다 비타민 E의 함유율이 높아, 노화를 예방하는 항산화 작용 및 심혈관 질환 예방에 좋습니다.\n" +
                "발연점이 높아(240℃) 산화안전성이 우수합니다.")
    }


    private fun changeSikdangExp(){
        //데이터베이스로 식당설명 보낸다.
        this.dismiss()
    }
}