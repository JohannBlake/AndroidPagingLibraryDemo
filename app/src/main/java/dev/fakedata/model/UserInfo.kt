package dev.fakedata.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class UserInfo (
    @PrimaryKey
    var id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var photoUrl: String = "",
    var jobTitle: String = ""
)