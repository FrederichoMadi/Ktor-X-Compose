package com.fredericho.borutoapp.data.repository

import com.fredericho.borutoapp.data.local.BorutoDatabase
import com.fredericho.borutoapp.domain.model.Hero
import com.fredericho.borutoapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(
    borutoDatabase : BorutoDatabase,
) : LocalDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override suspend fun getSelectedHero(heroId: Int): Hero {
        return heroDao.getSelected(heroId = heroId)
    }
}