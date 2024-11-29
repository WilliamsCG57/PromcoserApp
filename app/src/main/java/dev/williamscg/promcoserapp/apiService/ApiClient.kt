package dev.williamscg.promcoserapp.apiService
import android.content.Context
import com.google.android.gms.common.api.Api.Client
import okhttp3.Interceptor
import dev.williamscg.promcoserapp.apiService.DetalleParteApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Response

class UnauthorizedException(message: String) : Exception(message)

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = getTokenFromSharedPreferences(context)

        if (token.isNullOrEmpty()) {
            throw UnauthorizedException("Token is missing")
        }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }

    private fun getTokenFromSharedPreferences(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userToken", null)
    }
}

object ApiClient {
    private const val BASE_URL = "https://0169-38-25-122-10.ngrok-free.app/"

    private fun getOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    fun getDetalleParteApiService(context: Context): DetalleParteApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(context))
            .build()
            .create(DetalleParteApiService::class.java)
    }

    fun getMaquinariaApiService(context: Context): MaquinariaApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(context))
            .build()
            .create(MaquinariaApiService::class.java)
    }

    fun getClienteApiService(context: Context): ClienteApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(context))
            .build()
            .create(ClienteApiService::class.java)
    }

    fun getLugarApiService(context: Context): LugarTrabajoApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(context))
            .build()
            .create(LugarTrabajoApiService::class.java)
    }

    fun getParteDiarioApiService(context: Context): ParteDiarioService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(context))
            .build()
            .create(ParteDiarioService::class.java)
    }

}

object ApiClientLogIn {
    private const val BASE_URL = "https://0169-38-25-122-10.ngrok-free.app/"

    val instance: LogInApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LogInApiService::class.java)
    }
}

object RetrofitInstance {
    private const val BASE_URL = "https://0169-38-25-122-10.ngrok-free.app/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ParteDiarioService by lazy {
        retrofit.create(ParteDiarioService::class.java)
    }
}