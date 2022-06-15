package com.example.reviewapp.presentation.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reviewapp.R
import com.example.reviewapp.data.local.db.AppDatabase
import com.example.reviewapp.data.model.MenuEntityMapper
import com.example.reviewapp.data.repository.NewsRepositoryImpl
import com.example.reviewapp.databinding.FragmentHomeBinding
import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.usecase.articles.ArticlesUseCase
import com.example.reviewapp.domain.usecase.menu.MenuUseCase
import com.example.reviewapp.presentation.viewmodel.HomeViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var db: AppDatabase
    @Inject
    lateinit var menuUseCase: MenuUseCase
    @Inject
    lateinit var articlesUseCase: ArticlesUseCase
    @Inject
    lateinit var newsRepositoryImpl: NewsRepositoryImpl
    private val homeViewModel: HomeViewModel by  viewModels()

    private var job : Job? = Job()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadSlideImage()
        loadMenu()
        loadCategory()
        loadListArticles()
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
        val adapter = MenuAdapter( mutableListOf())
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
        homeViewModel.listMenu.observe(viewLifecycleOwner, Observer<List<Menu>> {
            adapter.setData(it)
        })
        binding.menu.adapter = adapter
        binding.menu.layoutManager = linearLayoutManager
    }

    private fun loadCategory() {
        var categoryAdapter = CategoryAdapter(mutableListOf())

        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        //Chèn một kẻ ngang giữa các phần tử
        val dividerHorizontal = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        //Chèn một kẻ đứng giữa các phần tử
        val dividerVertical = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        homeViewModel.listNews.observe(viewLifecycleOwner, Observer<List<Articles>> {
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
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        //Chèn một kẻ ngang giữa các phần tử
        val dividerHorizontal = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        //Chèn một kẻ đứng giữa các phần tử
        val dividerVertical = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
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
//        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
//            homeViewModel.getArticlePage().observe(viewLifecycleOwner) {
//                articleAdapter.submitData(lifecycle, it)
//            }
//        }
        homeViewModel.getArticlePage()
        homeViewModel.listArticles.observe(viewLifecycleOwner,Observer<PagingData<Articles>>{
            lifecycleScope.launch(Dispatchers.Main){
                articleAdapter.submitData(it)
            }
        })

//        job = lifecycleScope.launch {
//            homeViewModel.getArticlePage().collectLatest{
//                Log.d("Paging","loading")
//                articleAdapter.submitData(it)
//            }
//        }

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
        val controller = findNavController()
        controller.navigate(R.id.action_homeFragment_to_articleFragment)
    }

}