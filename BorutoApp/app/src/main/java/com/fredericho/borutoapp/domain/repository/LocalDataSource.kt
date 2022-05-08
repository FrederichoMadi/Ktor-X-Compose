package com.fredericho.borutoapp.domain.repository

import com.fredericho.borutoapp.domain.model.Hero

interface LocalDataSource {
    suspend fun getSelectedHero(heroId : Int) : Hero
}