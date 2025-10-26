                                                                                Zave – Nearby Shopping Assistant
                                                                                
🛒 Project Overview
Zave – Nearby Shopping Assistant is a native Android application designed to help users quickly find stores and shopping locations near their current geographical position. It leverages modern Android development libraries for authentication, location services, persistence, and networking.

✨ Features
User Authentication: Secure sign-in and sign-up using Firebase Authentication for Email/Password and Google Sign-In.

Nearby Search: Search for stores based on a user-provided keyword within a configurable radius.

Location Services: Uses FusedLocationProviderClient and requests runtime permissions to get the user's precise location.

Dynamic Configuration: Implements a RemoteConfigHelper (simulated via SharedPreferences) to dynamically adjust settings like the default search radius and a featured category.

Local Caching: Stores recent search queries and cached store results using Room Database for a better offline experience.

Map Integration: Displays search results and provides a shortcut to open the store's location in the native Google Maps application.

🛠️ Technology Stack

Category	Technology / Library	Usage
Language	Kotlin	Primary development language.
Platform	Android	Target SDK 35, Min SDK 24.
Architecture	MVVM-like	Separation of UI (Activities) and Data (Repository, Data Sources).
Authentication	Firebase Auth	Handles user login and registration.
Location	Google Play Services (FusedLocation)	Acquiring the user's current coordinates.
Networking	OkHttp, Gson	Making external API calls and parsing JSON responses.
Data Source	Nominatim (OpenStreetMap)	Used by Repo.kt for performing nearby store searches.
Persistence	Room, Coroutines	Local database for caching search queries and store data.
UI/UX	Coil, RecyclerView	Efficient image loading and displaying lists of stores.


📂 Project Structure Highlights
The application follows a standard package structure:

com.example.zavenearbyshoppingassistant (UI)

LoginActivity.kt: Handles the Firebase and Google sign-in/sign-up logic.

HomeActivity.kt: The main screen for entering a search query and initiating the nearby search.

SettingsActivity.kt: Activity for overriding the remote configuration values (radius, category).

ResultsActivity.kt: Displays the list of search results in a RecyclerView.

StoreAdapter.kt: Adapter for displaying individual store items.

com.example.zavenearbyshoppingassistant.data (Data Layer)

Repo.kt: The central repository managing data flow from the network (Nominatim) and local database (Room).

AppDatabase.kt, AppDao.kt, Models.kt: Room database definitions for SearchQuery and CachedStore.

RemoteConfigHelper.kt: A utility class simulating Firebase Remote Config via SharedPreferences.

PlacesService.kt: A wrapper for making raw HTTP calls to the search API.

🔑 Setup and Configuration
To run this project, you need to configure your Firebase project and provide a Google Maps API Key.

Firebase Project:

Create a new project in the Firebase Console.

Enable Authentication and turn on Email/Password and Google sign-in providers.

Add an Android app to the project and provide the correct package name (com.example.zavenearbyshoppingassistant).

Download the google-services.json file and place it in the app/ module directory.

Google Maps API Key:

Obtain a Google Maps API Key from the Google Cloud Console.

Add the key to your res/values/strings.xml file with the name Maps_key. This key is necessary for initialising Google Places and for the implicit intent that opens the search results in the Google Maps app.

XML

<string name="google_maps_key" translatable="false">YOUR_API_KEY_HERE</string>

Build Configuration:

Ensure the required plugins are applied in your root and app build.gradle.kts files (as seen in the provided files).

The app uses Kotlin Coroutines, Room, Coil, and OkHttp, which are already defined in the app-level build.gradle.kts.

▶️ Running the App
Sync the project with Gradle.

Run the application on an Android device or emulator.

The app will launch the Login Activity.

After successful login (or creating a new account), the Home Activity will request location permissions.

Enter a query (e.g., "supermarket" or "pharmacy") and press "Search Nearby Stores".

The results will be displayed, and clicking an item will take you to Google Maps.
