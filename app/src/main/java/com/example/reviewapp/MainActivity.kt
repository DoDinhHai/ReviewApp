package com.example.reviewapp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.reviewapp.data.local.db.AppDatabase
import com.example.reviewapp.databinding.ActivityMainBinding
import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.usecase.menu.MenuUseCase
import com.example.reviewapp.presentation.ui.home.*
import com.example.reviewapp.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var menuUseCase: MenuUseCase

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        loadMenu()
        loadFragment("HOME" )
        homeViewModel.setOldFragment("HOME")
        binding.bottomNavigationView.setOnItemSelectedListener { item ->

            //binding.bottomNavigationView.selectedItemId = item.itemId
            when (item.itemId) {
                R.id.homeFragment -> {
                    loadFragment("HOME" )
                    return@setOnItemSelectedListener true
                }
                R.id.videoFragment -> {
                    loadFragment("VIDEO")
                    return@setOnItemSelectedListener true
                }
                R.id.addFragment -> {
                    loadFragment("ADD")
                    return@setOnItemSelectedListener true
                }
                R.id.notificationFragment -> {
                    loadFragment("NOTIFY")
                    return@setOnItemSelectedListener true
                }
                R.id.userFragment -> {
                    loadFragment("USER")
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onBackPressed() {
        return
    }

    private fun loadMenu() {
        val menu1 = Menu(1, "Đăng tin cho tặng", R.drawable.ic_gift)
        val menu2 = Menu(2, "Review oto", R.drawable.ic_car)
        val menu3 = Menu(3, "Review nhà đất", R.drawable.ic_transaction)
        val menu4 = Menu(4, "Thông tin tài chính", R.drawable.ic_dola)
        val menu5 = Menu(5, "Thông tin năng lượng", R.drawable.ic_oil)

        CoroutineScope(Dispatchers.IO).launch {
            menuUseCase.addMenu(menu1)
            menuUseCase.addMenu(menu2)
            menuUseCase.addMenu(menu3)
            menuUseCase.addMenu(menu4)
            menuUseCase.addMenu(menu5)

        }
        homeViewModel.getMenu()
        Log.d("homeViewModel", homeViewModel.hashCode().toString())
    }

    private fun loadFragment( tag: String) {
        // load fragment
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment: Fragment?
        when(tag){
            "HOME" -> {
                fragment = supportFragmentManager.findFragmentByTag(tag)
                if (fragment == null){
                    fragment = HomeFragment()
                    transaction.add(R.id.nav_host_fragment, fragment,tag)
                    transaction.addToBackStack(null)

                }else{
                    transaction.show(fragment)
                }
                hideFragment(tag)
            }
            "VIDEO" -> {
                fragment = supportFragmentManager.findFragmentByTag(tag)
                if (fragment == null){
                    fragment = VideoFragment()
                    transaction.add(R.id.nav_host_fragment, fragment,tag)
                    transaction.addToBackStack(null)

                }else{
                    transaction.show(fragment)
                }
                hideFragment(tag)
            }
            "ADD" -> {
                fragment = supportFragmentManager.findFragmentByTag(tag)
                if (fragment == null){
                    fragment = AddFragment()
                    transaction.add(R.id.nav_host_fragment, fragment,tag)
                    transaction.addToBackStack(null)

                }else{
                    transaction.show(fragment)
                }
                hideFragment(tag)
            }
            "NOTIFY" -> {
                fragment = supportFragmentManager.findFragmentByTag(tag)
                if (fragment == null){
                    fragment = NotificationFragment()
                    transaction.add(R.id.nav_host_fragment, fragment,tag)
                    transaction.addToBackStack(null)

                }else{
                    transaction.show(fragment)
                }
                hideFragment(tag)
            }
            "USER" -> {
                fragment = supportFragmentManager.findFragmentByTag(tag)
                if (fragment == null){
                    fragment = UserFragment()
                    fragment.getView()?.setBackgroundColor(R.color.white);
                    transaction.add(R.id.nav_host_fragment, fragment,tag)
                    transaction.addToBackStack(null)

                }else{
                    transaction.show(fragment)
                }
                hideFragment(tag)
            }
        }
        transaction.commit()
    }

    private fun hideFragment( toTAG: String){
        val fromTAG = homeViewModel.getOldFragment()
        if (fromTAG == toTAG){
            return
        }
        var fromFragment: Fragment?
        fromFragment = supportFragmentManager.findFragmentByTag(fromTAG)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (fromFragment != null) {
            transaction.hide(fromFragment)
            transaction.commit()
            homeViewModel.setOldFragment(toTAG)
        }
    }
}