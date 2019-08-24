# Android Paging Library Demo
An Android app that demonstrates paging using limited data or endless data.

This app illustrates the use of the Android Paging Library and follows the practices as outlined at:

[https://developer.android.com/topic/libraries/architecture/paging](https://developer.android.com/topic/libraries/architecture/paging)

The app demonstrates two modes of paging: networking only or network/db

With network only, a list of users is retrieved from the site [fakedata.dev](fakedata.dev) and is displayed in a recyclerview without any caching at all. The network/db mode retrieves users
from the backend and stores them in a Room database and will re-use this cached data when it's needed. If the recyclerview runs out of data, another network call is made and
its results are cached in the Room database.

Fakedata.dev is a site I own and created for the purpose of generating test data. The free api allows you to retrieve up to 1 million users. The Users api contains a parameter that
you can set that indicates to only provide users where real face photos are used. This list of users is limited to 1,000. It is this list that is used for the network/db mode.
Alternatively, you can choose to not use this option, in which case up to 1 million users will be returned where their photos will be a mixture of real photos, user uploaded
avatars, Github auto-generated pixelated avatars or the default Ghithub icon. This larger list of 1 million users is ideal where you want to test out endless scrolling in your app.
This list is used for testing the network only mode.

User ids and photos are from Github users while the names are fictional. No Github photos are stored at fakedata.dev. Only links to photos are provided with each user and the size
of the photo can be set using an optional parameter. This allows you to test out your recyclerview to see how it performs with different image sizes.

The Users api does not retrieve its data from a database. It is generated in memory using an algorithm resulting in a very high performance rate of retrieval. This helps you to
discern between the performance of your app from that of the backend. You can be certain that if your scrolling is slow or intermittent, it has nothing to do with the backend.

The list of 1,000 users is sorted by first name while the list of 1 million has no sorting. However, it should be noted that regarless which list you use, the same list is always
generated with the same users. Random users are not displayed although the Users api does provide a method to generate random users. It isn't used in this app however.

The app uses PositionalDataSource in a custom data source when the networkd only mode is enabled. For the network/db mode, the default data factory from Room is used. LiveData is
used for both modes.

To toggle between network only and network/db mode, just tap on the floating action button in the bottom right corner of the app.

Technologies used:

* Kotlin
* Android Paging Library
* MVVM pattern
* LiveData
* RxJava
* Dagger
* Retrofit
* Room
* Glide (for image loading)
