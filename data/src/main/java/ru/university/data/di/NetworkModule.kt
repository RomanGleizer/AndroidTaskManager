package ru.university.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.university.data.network.MockInterceptor
import ru.university.network.api.AuthApi
import ru.university.network.api.ProjectsApi
import ru.university.network.api.TasksApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideMockInterceptor(
        @ApplicationContext context: Context
    ): MockInterceptor = MockInterceptor(context)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        mockInterceptor: MockInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(mockInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("http://localhost/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides @Singleton
    fun provideProjectsApi(retrofit: Retrofit): ProjectsApi = retrofit.create(ProjectsApi::class.java)

    @Provides @Singleton
    fun provideTasksApi(retrofit: Retrofit): TasksApi = retrofit.create(TasksApi::class.java)
}
