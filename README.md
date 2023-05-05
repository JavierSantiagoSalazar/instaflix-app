# InstaFlix

## Description

"InstaFlix is a movie and series catalog application for Android devices. The app allows users to browse and discover a wide variety of movies and series, and provides detailed information about each of them. A simple application that consumes the services provided by the TheMovieDB open API

- The project was developed in Android Native with Kotlin using.
- Modularization by capes and architecture with Clean Architecture.

The app has the following modules:

- app: The main module.
- data: Abstractions of DataSources and the Repositories.
- usecases: All the interactors/usecases in the whole app.
- domain: App business logic.
- testShared: Domain models for UnitTest.
- appTestShared: Fake modules and helpers for Instrumentation tests.
- buildSrc: Module for the organization of dependencies.

## Clean Arch Layers: 

![Full CleanArchitecture](https://user-images.githubusercontent.com/100961042/236562750-18dc2622-89c4-439f-87f0-ccb7c65fe221.png)

But for the case of android development I use this more abstracted and suitable form for development

![Fluend Clean](https://user-images.githubusercontent.com/100961042/236562816-881dc805-a1ed-4253-83a3-b3a835810e82.png)

- [x] Presentation/Framework: A layer that interacts with the UI.
- [x] UseCases: All app interactors. 
- [x] Data: Abstract definition of all the data sources and repositories
- [x] Domain: Contains the business logic of the app.

- Model-View-ViewModel as pattern for the presentation layer.
- Gradle dependencies are organized with buildSrc and KotlinDSL and updated manually with the help of the ben-manes script: ./gradlew dependencyUpdates
- Repository pattern for the data layer.
- Coroutines with Flow for the background tasks.
- Dagger Hilt for Dependency Injection.
- Retrofit and OkHttp to consume the API Rest. 
- Room to persist data locally.
- Navigation is done with Navigation Component.


## Testing

- Unit tests were made with Mockito and Turbine.
- IntegrationTests.
- UI tests were made with Espresso and Hilt, the UI tests do not consume network services, all the services are mocked.
- Page Object Pattern was implemented to UI tests so, the UI test are easier to read, and the implementation is encapsulated in the Pages.

## CI/CD

The Github Repository has one pipeline with Github Actions that checks the unit test, is executed when a PR is raised
pointing to main branch and when a merge is done to main branch

## TO IMPROVE

- Use a parent class Program in order to maintain inheritance between the similar properties of Movies and TvShow.
- Improve the way categories are handled within the application.
- Migrate to Jetpack Compose.
- Improve the way to obtain the categories in order to make the application more reactive to the user, generating more possibilities to obtain many more categories.
- Find a way to maintain one instance of the SnackBar to fix the error of having multiple SnackBars when there is connectivity error.
- More unit tests.

### Dependencies used

- [x] Kotlin v1.6.10
- [x] Dagger Hilt v2.44.2
- [x] Retrofit2 v2.9.0
- [x] OkHttp3 v4.11.0
- [x] Coroutines v1.6.4
- [x] Glide v4.15.1
- [x] Android Navigation v2.4.2
- [x] Safe Args v2.5.3
- [x] Mockito v4.1.0
- [x] Turbine v0.12.3
- [x] Espresso v3.5.1
- [x] Room v2.5.1
- [x] Arrow v1.1.5

## Requirements

- [x] Minimum version: Android 7 - API level 24
- [x] JAVA 11 in project estructure

### Made by Javier Santiago Salazar - javier452011@hotmail.es - www.linkedin.com/in/javier-santiago-salazar/
