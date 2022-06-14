package com.example.reviewapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reviewapp.data.local.db.AppDatabase
import com.example.reviewapp.data.model.MenuEntityMapper
import com.example.reviewapp.databinding.ActivityHomeBinding
import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.usecase.menu.MenuUseCase
import com.example.reviewapp.presentation.ui.home.*
import com.example.reviewapp.presentation.viewmodel.HomeViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var menuUseCase: MenuUseCase

    private val homeViewModel: HomeViewModel by viewModels()
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_home)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        //homeViewModel.getNews()
        loadSlideImage()
        loadMenu()
        loadCategory()
        loadListArticles()
        setContentView(binding.root)
    }

    private fun loadSlideImage() {
        val images = intArrayOf(
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six
        )
        val sliderAdapter = SliderAdapter(images)
        binding.imageSlider.setSliderAdapter(sliderAdapter)
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        binding.imageSlider.startAutoCycle()
    }

    private fun loadMenu() {
        val menu1 = Menu(1, "Đăng tin cho tặng", R.drawable.ic_gift)
        val menu2 = Menu(2, "Review oto", R.drawable.ic_car)
        val menu3 = Menu(3, "Review nhà đất", R.drawable.ic_transaction)
        val menu4 = Menu(4, "Thông tin tài chính", R.drawable.ic_dola)
        val menu5 = Menu(5, "Thông tin năng lượng", R.drawable.ic_oil)


        val mapper = MenuEntityMapper()
        //val menuRepository = MenuRepositoryImpl(db.menuDao(),mapper)
        val adapter = MenuAdapter(application, mutableListOf())
        val linearLayoutManager =
            LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
        CoroutineScope(Dispatchers.IO).launch {
            menuUseCase.addMenu(menu1)
            menuUseCase.addMenu(menu2)
            menuUseCase.addMenu(menu3)
            menuUseCase.addMenu(menu4)
            menuUseCase.addMenu(menu5)
//            menuRepository.insertMenu(menu1)
//            menuRepository.insertMenu(menu3)
//            menuRepository.insertMenu(menu4)
//            menuRepository.insertMenu(menu5)
        }
        homeViewModel.getMenu()
        homeViewModel.listMenu.observe(this, Observer<List<Menu>> {
            adapter.setData(it)
        })
        binding.menu.adapter = adapter
        binding.menu.layoutManager = linearLayoutManager
    }

    private fun loadCategory() {
        var categoryAdapter = CategoryAdapter(mutableListOf())

        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        //Chèn một kẻ ngang giữa các phần tử
        val dividerHorizontal = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        //Chèn một kẻ đứng giữa các phần tử
        val dividerVertical = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        homeViewModel.listNews.observe(this, Observer<List<Articles>> {
            categoryAdapter.setData(it)
            homeViewModel.insertArticles(it)

        })
        binding.listCategory.adapter = categoryAdapter
        binding.listCategory.layoutManager = gridLayoutManager
        binding.listCategory.addItemDecoration(dividerHorizontal)
        binding.listCategory.addItemDecoration(dividerVertical)
    }

    private fun loadListArticles() {
        var articleAdapter: ArticleAdapter? = null
        articleAdapter = ArticleAdapter(onItemClick)
        //hướng VERTICAL
        val linearLayoutManager =
            LinearLayoutManager(application, LinearLayoutManager.VERTICAL, false)
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        //Chèn một kẻ ngang giữa các phần tử
        val dividerHorizontal = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        //Chèn một kẻ đứng giữa các phần tử
        val dividerVertical = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        Log.d("Paging","start")
        //binding.progressBar.isVisible = true
        articleAdapter.addLoadStateListener { loadStates ->
            when (loadStates.source.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressBar.isVisible = false
                }
                is LoadState.Loading -> {
                    binding.progressBar.isVisible = articleAdapter.itemCount == 0
                }
                is LoadState.Error -> binding.progressBar.isVisible = false
            }
        }
        job?.cancel()
        job = lifecycleScope.launch {
            homeViewModel.getArticlePage().collectLatest{
                Log.d("Paging","loading")
                articleAdapter.submitData(it)
            }
        }
        Log.d("Paging","end")
        binding.listNews.adapter = articleAdapter.withLoadStateHeaderAndFooter(
            header = ArticleLoadStateAdapter { articleAdapter.retry()},
            footer = ArticleLoadStateAdapter { articleAdapter.retry()}
        )
        binding.listNews.layoutManager = gridLayoutManager
        binding.listNews.addItemDecoration(dividerHorizontal)
        binding.listNews.addItemDecoration(dividerVertical)
    }

    private val onItemClick: (Articles) -> Unit = {

    }
}