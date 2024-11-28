package dev.williamscg.promcoserapp.apiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://aca5-38-25-111-46.ngrok-free.app/"

    val instance: DetalleParteApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DetalleParteApiService::class.java)
    }
}