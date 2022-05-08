package com.fredericho.borutoapp.di

import android.content.Context
import com.fredericho.borutoapp.data.repository.DataStoreOperationsImpl
import com.fredericho.borutoapp.data.repository.Repository
import com.fredericho.borutoapp.domain.repository.DataStoreOperations
import com.fredericho.borutoapp.domain.use_cases.UseCases
import com.fredericho.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.fredericho.borutoapp.domain.use_cases.get_selected_hero.GetSelectedHeroUseCase
import com.fredericho.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.fredericho.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.fredericho.borutoapp.domain.use_cases.search_heroes.SearchHeroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context : Context
    ) : DataStoreOperations {
        return DataStoreOperationsImpl(context)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository) : UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository = repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository = repository),
            getAllHeroesUseCase = GetAllHeroesUseCase(repository),
            searchHeroesUseCase = SearchHeroesUseCase(repository),
            getSelectedHeroUseCase = GetSelectedHeroUseCase(repository)
        )
    }

}