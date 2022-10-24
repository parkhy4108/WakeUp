package com.dev_musashi.wakeup.module

import android.app.Application
import android.content.Context
import com.dev_musashi.wakeup.data.repository.SharedPrefRepositoryImpl
import com.dev_musashi.wakeup.data.repository.RingtoneServiceImpl
import com.dev_musashi.wakeup.data.repository.VibratorServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharePref(@ApplicationContext context: Context) : SharedPrefRepositoryImpl {
        return SharedPrefRepositoryImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) : RingtoneServiceImpl {
        return RingtoneServiceImpl(context = context)
    }


    @Singleton
    @Provides
    fun provideVibrator(application: Application): VibratorServiceImpl {
        return VibratorServiceImpl(application = application)
    }



}