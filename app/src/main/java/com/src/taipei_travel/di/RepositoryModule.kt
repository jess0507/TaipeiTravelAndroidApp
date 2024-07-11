package com.src.taipei_travel.di

import com.src.taipei_travel.domain.SettingDataStoreRepository
import com.src.taipei_travel.domain.SettingDataStoreRepositoryImpl
import com.src.taipei_travel.domain.DataRepository
import com.src.taipei_travel.domain.DataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDataRepository(dataRepositoryImpl: DataRepositoryImpl): DataRepository

    @Binds
    @Singleton
    abstract fun bindSettingDataStoreRepository(settingDataStoreRepositoryImpl: SettingDataStoreRepositoryImpl): SettingDataStoreRepository
}