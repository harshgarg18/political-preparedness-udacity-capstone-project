package com.udacity.political.preparedness.network.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "followed_elections",
    foreignKeys = [
        ForeignKey(
            entity = Election::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("electionId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class FollowedElection(
    @PrimaryKey val electionId: Int
)
