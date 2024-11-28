package dev.williamscg.promcoserapp.apiService

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjQsImlhdCI6MTczMTE2Mjg3OSwiZXhwIjoxNzMxMjQ5Mjc5fQ.HGTZhk0y0ZMKFOhs4DIJs8p8FU7Pr1nFphp-rDTqzVE"
    private const val BASE_URL = "https://aca5-38-25-111-46.ngrok-free.app/api/"

    private val client = OkHttpClient
        .Builder()
        .addInterceptor(AuthInterceptor(TOKEN)) // Añade el interceptor para el token
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Define la base URL
            .client(client) // Usa el cliente con el interceptor
            .addConverterFactory(GsonConverterFactory.create()) // Usa Gson para parsear JSON
            .build()
    }

    // Método genérico para crear servicios
    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}
