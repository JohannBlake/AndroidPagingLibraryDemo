package dev.fakedata.bo

data class UsersAPIOptions (
    var fromPredefinedList: Boolean = true,
    var startPos: Int = 0,
    var pageSize: Int = 20,
    var sortDesc: Boolean = true,
    var imageSize: Int = 200

)