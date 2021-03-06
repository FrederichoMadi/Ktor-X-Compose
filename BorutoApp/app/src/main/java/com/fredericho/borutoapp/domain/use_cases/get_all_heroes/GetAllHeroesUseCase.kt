package com.fredericho.borutoapp.domain.use_cases.get_all_heroes

import androidx.paging.PagingData
import com.fredericho.borutoapp.data.repository.Repository
import com.fredericho.borutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

class GetAllHeroesUseCase(
    private val repository : Repository
) {
    operator fun invoke() : Flow<PagingData<Hero>> {
        return repository.getAllHeroes()
    }
}