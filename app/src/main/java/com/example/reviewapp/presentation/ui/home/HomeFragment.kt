package com.example.reviewapp.presentation.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reviewapp.R
import com.example.reviewapp.data.local.db.AppDatabase
import com.example.reviewapp.databinding.FragmentHomeBinding
import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.presentation.viewmodel.HomeViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var db: AppDatabase
    private lateinit var homeViewModel: HomeViewModel// by  viewModels()

    private var job : Job? = Job()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
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
        val adapter = MenuAdapter( mutableListOf())
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeViewModel.listMenu.observe(viewLifecycleOwner, Observer<List<Menu>> {
            adapter.setData(it)
        })
        Log.d("homeViewModel",homeViewModel.hashCode().toString())
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
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            homeViewModel.getArticlePage().observe(viewLifecycleOwner) {
                articleAdapter.submitData(lifecycle, it)
            }
        }
//        homeViewModel.getArticlePage()
//        homeViewModel.listArticles.observe(viewLifecycleOwner,Observer<PagingData<Articles>>{
//            lifecycleScope.launch(Dispatchers.Main){
//                articleAdapter.submitData(it)
//            }
//        })

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
//        val controller = findNavController()
        homeViewModel.setArticle(it)
        loadFragment(ArticleFragment())
        //controller.navigate(R.id.action_homeFragment_to_articleFragment)
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

}