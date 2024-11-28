package dev.williamscg.promcoserapp.apiService

import okhttp3.OkHttpClient

object RetrofitInstance {

    private val TOKEN ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjQsImlhdCI6MTczMTE2Mjg3OSwiZXhwIjoxNzMxMjQ5Mjc5fQ.HGTZhk0y0ZMKFOhs4DIJs8p8FU7Pr1nFphp-rDTqzVE"
    private const val BASE_URL = "https://cd0b-38-25-17-64.ngrok-free.app/api/"
    private val client = OkHttpClient
        .Builder()
        .addInterceptor(AuthInterceptor(TOKEN))
        .build()
}