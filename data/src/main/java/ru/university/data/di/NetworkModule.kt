package ru.university.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.university.data.preference.UserPreferencesDataStore
import ru.university.network.api.AuthApi
import ru.university.network.api.ProjectsApi
import ru.university.network.api.TasksApi
import ru.university.network.di.RetrofitFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl(): String = "https://api.example.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        prefs: UserPreferencesDataStore
    ): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val token = runBlocking { prefs.authTokenFlow.firstOrNull() }
            val request = chain.request().newBuilder().apply {
                if (!token.isNullOrBlank()) header("Authorization", "Bearer $token")
            }.build()
            chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("BASE_URL") baseUrl: String,
        client: OkHttpClient
    ): Retrofit = RetrofitFactory.create(baseUrl)
        .newBuilder()
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideProjectsApi(retrofit: Retrofit): ProjectsApi =
        retrofit.create(ProjectsApi::class.java)

    @Provides
    @Singleton
    fun provideTasksApi(retrofit: Retrofit): TasksApi =
        retrofit.create(TasksApi::class.java)
}
