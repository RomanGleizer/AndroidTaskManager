package ru.university.data.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toUri().toString()

        val filename = when {
            url.endsWith("/auth/signup")  -> "signup_response.json"
            url.endsWith("/auth/signin")  -> "signin_response.json"
            url.endsWith("/projects")      -> "projects_list.json"
            url.contains("/projects/") && url.endsWith("/tasks") -> "tasks_list.json"
            else                            -> null
        }

        val json = filename?.let { loadAsset(it) } ?: "{}"

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(json.toResponseBody("application/json".toMediaType()))
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun loadAsset(name: String): String = try {
        context.assets.open(name).bufferedReader().use { it.readText() }
    } catch (e: IOException) {
        "{}"
    }
}