package com.fredericho.borutoapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fredericho.borutoapp.data.local.BorutoDatabase
import com.fredericho.borutoapp.data.repository.LocalDataSourceImpl
import com.fredericho.borutoapp.domain.repository.LocalDataSource
import com.fredericho.borutoapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context : Context
    ) : BorutoDatabase = Room.databaseBuilder(
        context,
        BorutoDatabase::class.java,
        Constants.BORUTO_DATABASE
    ).build()

    @Provides
    @Singleton
    fun provideLocalDataSource(
        database : BorutoDatabase
    ) : LocalDataSource{
        return LocalDataSourceImpl(database)
    }

}