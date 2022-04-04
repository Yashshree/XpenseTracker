package com.com.xpensetracker.di

import android.content.Context
import androidx.room.Room
import com.com.xpensetracker.datastore.UserPreferences
import com.com.xpensetracker.room.AppDatabase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideFirebaseInstance() = FirebaseDatabase.getInstance().reference


    @Provides
    @Singleton
    fun provideUserPreference(@ApplicationContext appContext:Context) = UserPreferences(appContext)

    @Provides
    @Singleton
    fun provideRoomDatabaseInstance(@ApplicationContext appContext:Context)= Room.databaseBuilder(
        appContext,
        AppDatabase::class.java, "expenseTracker.db"
    ).build()


}