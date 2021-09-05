package com.toyibnurseha.colearntest.data.remote

import com.toyibnurseha.colearntest.data.local.MyPhoto

data class PhotoResponse(
    var results: List<MyPhoto>
)