package dev.williamscg.promcoserapp.apiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://ac8d-38-25-122-10.ngrok-free.app/"

    val instance: DetalleParteApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DetalleParteApiService::class.java)
    }
}