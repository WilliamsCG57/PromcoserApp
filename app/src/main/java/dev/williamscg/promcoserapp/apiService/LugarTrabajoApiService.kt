package dev.williamscg.promcoserapp.apiService

import dev.williamscg.promcoserapp.model.LugarTrabajoModel
import retrofit2.Call
import retrofit2.http.GET

interface LugarTrabajoApiService {
    @GET("api/LugarTrabajo/GetAllActive")
    fun getAllLugares(): Call<List<LugarTrabajoModel>>
}