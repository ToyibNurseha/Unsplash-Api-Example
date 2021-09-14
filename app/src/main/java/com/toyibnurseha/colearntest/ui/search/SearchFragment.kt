package com.toyibnurseha.colearntest.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.toyibnurseha.colearntest.R
import com.toyibnurseha.colearntest.adapter.PhotoLoadStateAdapter
import com.toyibnurseha.colearntest.adapter.SearchAdapter
import com.toyibnurseha.colearntest.databinding.FragmentSearchBinding
import com.toyibnurseha.colearntest.interfaces.FilterListener
import com.toyibnurseha.colearntest.ui.MainActivity
import com.toyibnurseha.colearntest.ui.detail.DetailFragment
import com.toyibnurseha.colearntest.ui.dialog.FilterDialogFragment
import com.toyibnurseha.colearntest.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), FilterListener {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var searchAdapter: SearchAdapter

    private var searchQuery = ""
    private var colorFilter : String? = null
    private var orientationFilter : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = "Unsplash"
        (activity as MainActivity).supportActionBar?.title = "Unsplash"
        searchAdapter = SearchAdapter()
        searchQuery = "Football"
        clickListener()
        setupRecycler()
        callRequest()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.searchMenu)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchQuery = query
                    binding.rvPhotosSearch.scrollToPosition(0)
                    viewModel.getPhotos(searchQuery, colorFilter, orientationFilter)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.filterMenu -> {
                val fm: DialogFragment =
                    FilterDialogFragment.newInstance(colorFilter, orientationFilter, this)

                activity?.let { act ->
                    fm.show(act.supportFragmentManager, "DialogFragment")
                }

            }

            R.id.favoriteMenu -> {
                findNavController().navigate(R.id.action_searchFragment_to_favoriteFragment)
            }
        }
        return  super.onOptionsItemSelected(item)
    }

    private fun callRequest() {
        viewModel.getPhotos(searchQuery, colorFilter, orientationFilter).observe(viewLifecycleOwner) {
            searchAdapter.submitData(this.lifecycle, it)
        }
    }

    private fun setupRecycler() {
        binding.rvPhotosSearch.apply {
            adapter = searchAdapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter { searchAdapter.retry() },
                footer = PhotoLoadStateAdapter { searchAdapter.retry() }
            )
            val layout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            layout.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            layoutManager = layout
        }

        searchAdapter.addLoadStateListener { loadState ->
            binding.apply {
                loadingBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvPhotosSearch.isVisible = loadState.source.refresh is LoadState.NotLoading

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    searchAdapter.itemCount < 1
                ) {
                    rvPhotosSearch.isVisible = false
                    tvEmpty.isVisible = true
                } else {
                    tvEmpty.isVisible = false
                }
            }
        }
    }

    private fun clickListener() {
        searchAdapter.setOnItemClickListener {
            val bundle = bundleOf(DetailFragment.URL_DATA to it)
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle)
        }
    }

    override fun finishFilter(color: String?, orientation: String?) {
        colorFilter = color
        orientationFilter = orientation
        binding.rvPhotosSearch.scrollToPosition(0)
        callRequest()
    }
}