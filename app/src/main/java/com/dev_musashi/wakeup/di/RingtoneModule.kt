package com.dev_musashi.wakeup.di

import com.dev_musashi.wakeup.data.repository.RingtoneServiceImpl
import com.dev_musashi.wakeup.domain.RingtoneService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RingtoneModule {
    @Binds
    @Singleton
    abstract fun bindRingtone(
        ringtoneRepositoryImpl: RingtoneServiceImpl
    ): RingtoneService

}