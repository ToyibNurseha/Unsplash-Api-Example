package com.toyibnurseha.colearntest.ui.detail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator
import com.toyibnurseha.colearntest.databinding.FragmentDetailBinding
import com.toyibnurseha.colearntest.ui.MainActivity


class DetailFragment : Fragment() {

    companion object {
        const val URL_DATA = "photoData"
    }

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
        (activity as MainActivity).supportActionBar?.title = photoData?.user?.name

        binding.ivImageDetail.apply {
            setProgressIndicator(ProgressPieIndicator())
            showImage(Uri.parse(photoData?.urls?.full))
        }
    }



}