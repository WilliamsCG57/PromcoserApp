package dev.williamscg.promcoserapp.apiService
import dev.williamscg.promcoserapp.model.CreacionDetalleModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetalleParteApiService {
    @GET("api/DetalleParteDiario/GetAllActive/{idParteDiario}")
    fun getAllActiveDetails(
        @Path("idParteDiario") idParteDiario: Int
    ): Call<List<CreacionDetalleModel>>
}