package com.drsync.paging3example.di

import android.content.Context
import androidx.room.Room
import com.drsync.paging3example.api.ApiConfig
import com.drsync.paging3example.database.BarangDatabase
import com.drsync.paging3example.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): ApiConfig {
        return retrofit.create(ApiConfig::class.java)
    }

    @Provides
    @Singleton
    fun provideBarangDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, BarangDatabase::class.java,
        "db_barang"
    ).build()

    @Provides
    @Singleton
    fun provideBarangDao(db: BarangDatabase) = db.barangDao()

    @Provides
    @Singleton
    fun provideRemoteKeysDao(db: BarangDatabase) = db.remoteKeysDao()
}