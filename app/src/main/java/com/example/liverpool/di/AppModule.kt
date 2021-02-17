package com.example.liverpool.di

import android.content.Context
import androidx.room.Room
import com.example.liverpool.BuildConfig
import com.example.liverpool.data.products.ProductsApi
import com.example.liverpool.data.products.ProductsApiImpl
import com.example.liverpool.data.products.ProductsServices
import com.example.liverpool.data.products.database.ProductsDb
import com.example.liverpool.domain.products.DeleteSuggestionUseCase
import com.example.liverpool.domain.products.SearchProductsUseCase
import com.example.liverpool.domain.products.GetSuggestionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://shoppapp.liverpool.com.mx/appclienteservices/services/v3/"

    @Provides
    @Singleton
    fun provideRoom(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context.applicationContext,
        ProductsDb::class.java,
        "productsDb")
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()

        if(BuildConfig.DEBUG){
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        builder
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)


        return Retrofit.Builder().client(builder.build())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    }

    @Provides
    fun provideProductsApi(
        retrofit: Retrofit,
        productsDb: ProductsDb
    ): ProductsApi = ProductsApiImpl(
        retrofit.create(ProductsServices::class.java),
        productsDb.suggestionsDao()
    )

    @Provides
    fun provideGetProductUseCase(
        productsApi: ProductsApi
    ) = SearchProductsUseCase(productsApi)

    @Provides
    fun provideGetSuggestionsUseCase(
        productsApi: ProductsApi
    ) = GetSuggestionsUseCase(productsApi)

    @Provides
    fun provideDeleteSuggestionUseCase(
        productsApi: ProductsApi
    ) = DeleteSuggestionUseCase(productsApi)

}