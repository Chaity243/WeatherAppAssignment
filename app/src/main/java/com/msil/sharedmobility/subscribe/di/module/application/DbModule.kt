package com.msil.sharedmobility.subscribe.di.module.application

import android.content.Context
import androidx.room.Room
import com.msil.sharedmobility.data.local.MyDatabase
import com.msil.sharedmobility.subscribe.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Shivang Goel on 08/08/2019.
 */
@Module
class DbModule {

    /*
    * The method returns the Database object
    * */

    @Singleton
    @Provides
    fun provideMyDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MyDatabase::class.java, "mydatabase").build()

    @Singleton
    @Provides
    fun provideAlbumDao(myDatabase: MyDatabase) = myDatabase.albumDao()
}