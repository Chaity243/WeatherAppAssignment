package com.msil.sharedmobility.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.msil.sharedmobility.data.local.album.AlbumDao
import com.msil.sharedmobility.data.local.album.AlbumData

/**
 * Created by Shivang Goel on 08/09/2019.
 */
/**
 * Database class with all of its dao classes
 */
@Database(entities = [AlbumData.Album::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
}