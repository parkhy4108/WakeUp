package com.dev_musashi.wakeup.module

import com.dev_musashi.wakeup.data.repository.SharedPrefRepositoryImpl
import com.dev_musashi.wakeup.domain.SharedPrefRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SharedPrefModule {

    @Binds
    @Singleton
    abstract fun bindSharePref(
        sharedPrefRepositoryImpl: SharedPrefRepositoryImpl
    ): SharedPrefRepository

}