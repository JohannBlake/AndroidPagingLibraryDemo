package dev.fakedata.bo

data class UsersAPIOptions (
    var useFacePhotos: Boolean = true,
    var startPos: Int = 0,
    var pageSize: Int = 20,
    var sortDesc: Boolean = false,
    var imageSize: Int = 100

)