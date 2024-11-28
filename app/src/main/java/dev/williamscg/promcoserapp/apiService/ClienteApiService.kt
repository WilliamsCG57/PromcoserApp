package dev.williamscg.promcoserapp.apiService


import dev.williamscg.promcoserapp.model.ClienteModel
import retrofit2.Call
import retrofit2.http.GET

interface ClienteApiService {
    @GET("api/Cliente/GetAllActive")
    fun getAllClientes(): Call<List<ClienteModel>>
}