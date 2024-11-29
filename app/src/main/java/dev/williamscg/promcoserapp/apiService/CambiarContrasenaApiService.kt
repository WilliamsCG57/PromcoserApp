package dev.williamscg.promcoserapp.apiService

import dev.williamscg.promcoserapp.model.CambioContrasenaModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// API Interface
interface CambiarContrasenaApiService {
    @POST("api/Personal/ChangePassword")
    suspend fun changePassword(
        @Body request: CambioContrasenaModel
    ): Response<String>
}

// Request data class
