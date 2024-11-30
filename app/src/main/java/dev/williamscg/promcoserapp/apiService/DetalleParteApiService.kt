package dev.williamscg.promcoserapp.apiService
import dev.williamscg.promcoserapp.model.CreacionDetalleModel
import dev.williamscg.promcoserapp.model.DetalleParteDiarioModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

public interface DetalleParteApiService {
    @GET("api/DetalleParteDiario/GetAllActive/{idParteDiario}")
    fun getAllActiveDetails(
        @Path("idParteDiario") idParteDiario: Int
    ): Call<List<CreacionDetalleModel>>

    @PUT("api/DetalleParteDiario/Deactivate/{id}")
    fun deactivateDetail(@Path("id") id: Int): Call<Void>

    @POST("api/DetalleParteDiario/Create")
    suspend fun crearDetalleParteDiario(@Body newDetalleParteDiario: DetalleParteDiarioModel): Response<Unit>
}