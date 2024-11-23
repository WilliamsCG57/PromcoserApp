package dev.williamscg.promcoserapp.apiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://e209-200-0-166-76.ngrok-free.app/"

    val instance: DetalleParteApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DetalleParteApiService::class.java)
    }
}