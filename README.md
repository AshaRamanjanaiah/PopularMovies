# Popular Movies App

## Project Overview
Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. In this project, I have built an app to allow users to discover the most popular movies playing.

## Project Details:
 
1. My app will present the user with a grid arrangement of movie posters upon launch.

2. Allows user to change sort order via a setting menu. When sort criteria (most popular, highest rated, and favorites) is changed the main view gets updated accordingly.

3. When the "favorites" setting option is selected, the main view displays the entire favorites collection.

4. Allows the user to tap on a movie poster and transition to a details screen with additional information such as
original title, movie poster, image thumbnail, A plot synopsis (called overview in the api), user rating (called vote_average in the api) and release date

5. App will fetch trailers and reviews by making a network request and displays data in RecyclerView.

6. When a trailer is selected, app launchs the trailer in YouTube.

7. In the movies detail screen, a user can tap on "Mark As Favorite" button to mark that particular movie as favorite.

8. The titles, IDs, movie poster, synopsis, user rating, and release date of the user’s favorite movies are stored using Room and are updated whenever the user favorites or unfavorites a movie. Display favorite movies even when offline. 

9. Sharing functionality has been implemented to allow the user to share the trailer’s YouTube URL.

## What did I Learn?
Through this project:

1. Creating a gridview with custom adaptor

2. Parcelables and onSaveInstanceState()

3. Using Picasso library

4. Using Lint to check errors and warning in the project

5. Practice consuming data from RESTful API and JSON parsing to a model object

6. Making sure my app does not crash when there is no network connection! 

7. Design activity layouts

8. Android architecture components(Room, LiveData, ViewModel):

   a. Database is implemented using Room to store Favorite movies. 

   b. Database is not re-queried unnecessarily. LiveData is used to observe changes in the database and update the UI accordingly.

   c. Database is not re-queried unnecessarily after rotation. Cached LiveData from ViewModel is used instead.

9. Retrofit 

10. gson

11. RecycleView with Adapter

12. Executors

## This is how the App looks

![](2018_11_30_15_00_34%20(1).gif)


## How to run the app
Add your own API key in gradle.properties and use BuildConfig property.
