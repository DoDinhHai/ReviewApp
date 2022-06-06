package com.example.reviewapp.presentation.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import com.example.reviewapp.R
import com.example.reviewapp.data.local.db.AppDatabase
import com.example.reviewapp.data.model.MenuEntityMapper
import com.example.reviewapp.data.repository.MenuRepositoryImpl
import com.example.reviewapp.domain.model.Menu
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    @Inject
    lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sliderView: SliderView? = null
        val images = intArrayOf(
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six
        )

        val sliderAdapter = SliderAdapter(images)

        sliderView!!.setSliderAdapter(sliderAdapter)
        sliderView!!.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView!!.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        sliderView!!.startAutoCycle()


        //(parent.parent as View).setBackgroundColor(yourFancyColor)
    }
}