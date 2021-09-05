package com.toyibnurseha.colearntest.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.toyibnurseha.colearntest.R
import com.toyibnurseha.colearntest.databinding.FragmentFilterDialogBinding
import com.toyibnurseha.colearntest.interfaces.FilterListener
import java.util.*


class FilterDialogFragment(private val listener: FilterListener) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterDialogBinding

    private var filterColor: String? = null
    private var filterOrientation: String? = null


    private var formattedColor : String? = null
    private var formattedOrientation : String? = null

    companion object {
        const val COLOR_FILTER = "color_filter_data"
        const val ORIENTATION_FILTER = "orientation_filter_data"
        fun newInstance(
            colorFilter: String?,
            orientationFilter: String?,
            interfaceListener: FilterListener
        ): FilterDialogFragment {
            val frag = FilterDialogFragment(interfaceListener)
            val args = Bundle()
            args.putString(COLOR_FILTER, colorFilter)
            args.putString(ORIENTATION_FILTER, orientationFilter)
            frag.arguments = args
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLastSelection()

        binding.btnApply.setOnClickListener {
            submitFilter()
        }

        binding.btnClear.setOnClickListener {
            binding.rgColor.check(R.id.rbAnyColor)
            binding.rgOrientation.check(R.id.rbAnyLandscape)
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

    }

    private fun submitFilter() {
        val colorId = binding.rgColor.checkedRadioButtonId
        filterColor = binding.root.findViewById<RadioButton>(colorId).text.toString()
        val orientationId = binding.rgOrientation.checkedRadioButtonId
        filterOrientation =
            binding.root.findViewById<RadioButton>(orientationId).text.toString()

        if (filterOrientation != resources.getString(R.string.any)) {
            formattedOrientation = filterOrientation?.toLowerCase(Locale.ROOT)
        }

        if (filterColor != resources.getString(R.string.any_color)) {
            formattedColor = filterColor?.replace(" ", "_")?.toLowerCase(Locale.ROOT).toString()
        }

        listener.finishFilter(formattedColor, formattedOrientation)
        dismiss()
    }

    private fun checkLastSelection() {
        val args: Bundle? = arguments

        val currentColorFilter = args?.getString(COLOR_FILTER)
        val currentOrientationFilter = args?.getString(ORIENTATION_FILTER)

        if (currentColorFilter == resources.getString(R.string.black_and_white).replace(" ", "_")
                .toLowerCase(Locale.ROOT)
        ) {
            binding.rgColor.check(R.id.rbBlackAndWhite)
        }
        if (currentOrientationFilter == resources.getString(R.string.landscape).toLowerCase(Locale.ROOT)) {
            binding.rgOrientation.check(R.id.rbLandscape)
        } else if (currentOrientationFilter == resources.getString(R.string.portrait).toLowerCase(
                Locale.ROOT
            )
        ) {
            binding.rgOrientation.check(R.id.rbPortrait)
        }
    }

}