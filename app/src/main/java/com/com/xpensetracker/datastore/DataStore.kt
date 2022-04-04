package com.com.xpensetracker.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferences (@ApplicationContext val appContext:Context)
{
    val Context.datastore : DataStore<Preferences> by preferencesDataStore(
        name = "user_pref"
    )

    companion object{
        val IS_ACTIVE = booleanPreferencesKey(name = "is_active")
        val USER_NAME = stringPreferencesKey(name = "user_name")
    }

    suspend fun saveIsActive(isActive:Boolean){
        appContext.datastore.edit {
            preferences -> preferences [IS_ACTIVE] = isActive
        }
    }

    val isActive : Flow<Boolean> = appContext.datastore.data.map {
        preferences -> preferences[IS_ACTIVE] ?:false
    }

    suspend fun saveUserName(name:String){
        appContext.datastore.edit {
            preferences-> preferences[USER_NAME] = name
        }
    }

    val userName : Flow<String> = appContext.datastore.data.map {
        preferences -> preferences[USER_NAME] ?: "Default"
    }
}