package com.dev_musashi.wakeup.module

import com.dev_musashi.wakeup.data.repository.VibratorServiceImpl
import com.dev_musashi.wakeup.domain.VibratorService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VibratorModule {

    @Binds
    @Singleton
    abstract fun bindVibratorDivider(
        vibratorRepositoryImpl: VibratorServiceImpl
    ): VibratorService

}