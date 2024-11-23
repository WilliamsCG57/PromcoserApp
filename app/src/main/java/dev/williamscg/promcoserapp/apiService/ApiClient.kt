package dev.williamscg.promcoserapp.apiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://cd0b-38-25-17-64.ngrok-free.app/"

    val instance: DetalleParteApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DetalleParteApiService::class.java)
    }
}