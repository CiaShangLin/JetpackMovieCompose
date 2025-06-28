package com.shang.network.retrofit

// class MovieDataSourceImp @Inject constructor(private val _movieApiService: MovieApiService) :
//    MovieDataSource {
//    override suspend fun getMovieGenres(): MovieGenreBean {
//        val json = _movieApiService.getMovieGenres().body()?.string() ?: ""
//        val bean: MovieGenreResponse = Json.Default.decodeFromString<MovieGenreResponse>(json)
//        return bean.asExternalModel()
//    }
// }
