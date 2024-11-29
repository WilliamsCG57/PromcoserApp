package dev.williamscg.promcoserapp.apiService

import dev.williamscg.promcoserapp.model.MaquinariaModel
import retrofit2.Call
import retrofit2.http.GET

interface MaquinariaApiService {
    @GET("api/Maquinaria/GetAllActive")
    fun getAllMaquinarias(): Call<List<MaquinariaModel>>
}