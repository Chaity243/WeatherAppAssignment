package com.msil.sharedmobility.data.mapper

import com.msil.sharedmobility.data.local.album.AlbumData
import com.msil.sharedmobility.domain.entity.Entity

/**
 * Created by Shivang Goel on 08/09/2019.
 */
/**
 * Extension class to map album entity to album data
 */
fun Entity.Album.map() = AlbumData.Album(
    id = id,
    userId = userId,
    title = title
)