package com.toyibnurseha.colearntest.ui.detail

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator
import com.google.android.material.snackbar.Snackbar
import com.toyibnurseha.colearntest.R
import com.toyibnurseha.colearntest.databinding.FragmentDetailBinding
import com.toyibnurseha.colearntest.ui.MainActivity
import com.toyibnurseha.colearntest.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    companion object {
        const val URL_DATA = "photoData"
    }

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: FragmentDetailBinding

    private val args by navArgs<DetailFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photoData = args.photoData
        var isFavorited = false
        (activity as MainActivity).supportActionBar?.title = photoData?.user?.name

        binding.ivImageDetail.apply {
            setProgressIndicator(ProgressPieIndicator())
            showImage(Uri.parse(photoData?.urls?.full))
        }
        photoData?.let {
            viewModel.getFavoriteById(it.id).observe(viewLifecycleOwner, { data ->
                isFavorited = if (data != null) {
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorited_red)
                    true
                } else {
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite_red)
                    false
                }
            })
        }

        binding.btnFavorite.setOnClickListener {
            photoData?.let {
                if (isFavorited) {
                    viewModel.deletePhoto(photoData)
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite_red)
                    Snackbar.make(view, "Photo Removed from Favorite!", Snackbar.LENGTH_SHORT).show()
                    isFavorited = false
                } else {
                    viewModel.upsert(photoData)
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorited_red)
                    Snackbar.make(view, "Photo Added to Favorite!", Snackbar.LENGTH_SHORT).show()
                    isFavorited = true
                }
            }
        }
    }

}