package com.msil.sharedmobility.data.mapper

import com.msil.sharedmobility.data.local.album.AlbumData
import com.msil.sharedmobility.domain.entity.Entity

/**
 * Created by Shivang Goel on 08/09/2019.
 */
/**
 * Extension class to map album data to album entity
 */
fun AlbumData.Album.map() = Entity.Album(
    id = id,
    userId = userId,
    title = title
)