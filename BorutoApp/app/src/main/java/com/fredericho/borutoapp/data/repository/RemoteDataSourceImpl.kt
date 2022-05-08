package com.fredericho.borutoapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fredericho.borutoapp.data.local.BorutoDatabase
import com.fredericho.borutoapp.data.paging_source.HeroRemoteMediator
import com.fredericho.borutoapp.data.paging_source.SearchHeroesSources
import com.fredericho.borutoapp.data.remote.BorutoApi
import com.fredericho.borutoapp.domain.model.Hero
import com.fredericho.borutoapp.domain.repository.RemoteDataSource
import com.fredericho.borutoapp.util.Constants
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val borutoApi : BorutoApi,
    private val borutoDatabase : BorutoDatabase
) : RemoteDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        val pagingSourceFactory = { heroDao.getAllHeroes()}
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = HeroRemoteMediator(
                borutoApi = borutoApi,
                borutoDatabase = borutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query : String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchHeroesSources(borutoApi = borutoApi, query = query)
            }
        ).flow
    }
}