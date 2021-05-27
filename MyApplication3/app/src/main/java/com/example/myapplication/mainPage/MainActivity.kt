package com.example.myapplication.mainPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.storage.StorageManager
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.Store
import com.example.myapplication.bookhistory.BookHistoryFragment
import com.example.myapplication.bookmark.BookMarkFragment
import com.example.myapplication.dataclass.Location
import com.example.myapplication.dataclass.StoreInfo
import com.example.myapplication.mypage.MyPage
import com.example.myapplication.sikdangChoicePage.SikdangChoice
import com.example.myapplication.storeActivity.EditReviewActivity
import com.example.myapplication.storeActivity.StoreActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.booktime_timeline.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigator: BottomNavigationView
    private var selectedFragment : Fragment? = null
    private var navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    selectedFragment = Sikdang_main()
                }

                R.id.nav_bookmark -> {
                    selectedFragment = BookMarkFragment()
                }

                R.id.nav_dish -> {
                    selectedFragment = BookHistoryFragment()
                }

                R.id.nav_search-> {
                    selectedFragment = null
                    var _intent = Intent(this, SikdangChoice::class.java)
                    startActivity(_intent)
                }

                R.id.nav_profile-> {
                    selectedFragment = MyPage()

                    /*
                    val _intent = Intent(this, EditReviewActivity::class.java)
                    _intent.putExtra("store_info", store_info)
                    startActivity(_intent)

                     */
                }

            }

            if(selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    selectedFragment!!
                ).commit()
            }

            return@OnNavigationItemSelectedListener true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigator = findViewById(R.id.bottom_navigation)
        bottomNavigator.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        bottomNavigator.selectedItemId = R.id.nav_home
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Sikdang_main()).commit()

    }
}