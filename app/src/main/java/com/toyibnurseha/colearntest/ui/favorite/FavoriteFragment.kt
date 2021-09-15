package com.toyibnurseha.colearntest.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.toyibnurseha.colearntest.R
import com.toyibnurseha.colearntest.adapter.FavoriteAdapter
import com.toyibnurseha.colearntest.adapter.PhotoLoadStateAdapter
import com.toyibnurseha.colearntest.data.local.MyPhoto
import com.toyibnurseha.colearntest.databinding.FragmentFavoriteBinding
import com.toyibnurseha.colearntest.ui.MainActivity
import com.toyibnurseha.colearntest.ui.detail.DetailFragment
import com.toyibnurseha.colearntest.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private val viewModel by viewModels<MainViewModel>()
    private val pagingAdapter by lazy { FavoriteAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = "Favorite Photo"
        adapter = pagingAdapter
        setupRecycler()
        callRequest()

        pagingAdapter.setOnItemClickListener {
            it?.let { data ->
                val newUrl =
                    data.urlBig?.let { url ->
                        MyPhoto.PhotoUrls(
                            full = url
                        )
                    }
                val newEntity = MyPhoto(
                    id = data.id,
                    desc = data.desc,
                    urls = newUrl
                    )
            val bundle = bundleOf(DetailFragment.URL_DATA to newEntity)
            findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment, bundle)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun callRequest() {
        lifecycleScope.launch {
            viewModel.getFavorites().collectLatest { source ->  pagingAdapter.submitData(source)}
        }
    }

    private fun setupRecycler() {
        binding.rvFavorite.apply {
            adapter = pagingAdapter
            val layout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            layout.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            layoutManager = layout
        }

        pagingAdapter.addLoadStateListener { loadState ->
            binding.apply {

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    pagingAdapter.itemCount < 1
                ) {
                    rvFavorite.isVisible = false
                    tvEmpty.isVisible = true
                } else {
                    tvEmpty.isVisible = false
                }
            }
        }
    }

}

