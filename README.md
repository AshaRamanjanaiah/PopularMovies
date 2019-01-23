# Popular Movies App

## Project Overview
Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. In this project, I have built an app to allow users to discover the most popular movies playing. I have split the development of this app in two stages. First, let's talk about stage 1.

## Stage 1:
 
My app will present the user with a grid arrangement of movie posters upon launch.

Allow user to change sort order via a setting.

The sort order can be by most popular or by highest-rated

Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
original title
movie poster image thumbnail
A plot synopsis (called overview in the api)
user rating (called vote_average in the api)
release date

## What did I Learn?
Through this project:

Creating a gridview with custom adaptor

Parcelables and onSaveInstanceState()

Using Picasso library

Using Lint to check errors and warning in the project

Practice consuming data from RESTful API and JSON parsing to a model object

Making sure my app does not crash when there is no network connection! 

Design activity layouts

Populate all fields in the layout accordingly

## Stage 2

1. App will fetch trailers by making a request to the /movie/{id}/videos endpoint.

2. App will fetch reviews by making a request to the /movie/{id}/reviews endpoint.

3. When a trailer is selected, app uses an Intent to launch the trailer.

4. When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.

5. When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the database.

6. In the movies detail screen, a user can tap a button (Mark As Favorite) to mark it as a Favorite. Tap the button on a "Unfavorite movie" will unfavorite it.

7. The titles, IDs, movie poster, synopsis, user rating, and release date of the user’s favorite movies are stored using Room and are updated whenever the user favorites or unfavorites a movie. Display favorite movies even when offline. 

8. Sharing functionality has been implemented to allow the user to share the trailer’s YouTube URL from the movie details screen.

## What did I learn?
In stage 2 of this project:

1. Android architecture components(Room, LiveData, ViewModel):

Database is implemented using Room to store Favorite movies. 

Database is not re-queried unnecessarily. LiveData is used to observe changes in the database and update the UI accordingly.

Database is not re-queried unnecessarily after rotation. Cached LiveData from ViewModel is used instead.

2. Retrofit 

3. gson

4. RecycleView with Adapter

5. Executors

## This is how the App looks

![](2018_11_30_15_00_34%20(1).gif)


## How to run the app
Add your own API key in gradle.properties and use BuildConfig property.
