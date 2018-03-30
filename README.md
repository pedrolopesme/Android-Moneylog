<h1 align="center">
  <br>
  <img src="https://raw.githubusercontent.com/pedrolopesme/moneylog/master/mocks/icon.png" alt="Cinepedia" width="200px">
  <br>
  MoneyLog
  <br>
  <br>
</h1>

<h4 align="center"> Udacity Capstone Project </h4>

### Description

With the app MoneyLog you can manage and track your expenses.
At any time, you can check all your money transactions ordered by
creation date and check your finance balance.


### Intended User

MoneyLog App comes to help people who need to track their expenses on a daily basis.


### Features
* Main screen show the user's expenses ordered by date of creation.
* An easy way to register new transactions, categorizing as Debt or Income
* The app will save user's location when saving a transaction
* The app will suggest locations to associate with a transaction using Google Places API
* An Widget to show the user's financial balance
* The user will be notified via Push Notification whenever his account turns to Negative or
Positive


### Running

This project was built using Android Studio 3.0.1 and Gradle 4.1
All dependencies are listed at build.gradle.

In order to run this application you'll need to have two keys from Google APIs:

* *Google Maps Android API*: It should be restricted to the package _com.moneylog.android.moneylog_, as an Android Apps and API restrictions setted to Google Maps Android API. Once you get the API Key, you should place it in the *config_google_maps_key* entry, in the strings.xml file.

* *Google Places API Web Service*: It should be restricted to the package _com.moneylog.android.moneylog_, as an Android Apps and API restrictions setted to Google Places API Web Service. Once you get the API Key, you should place it in the *config_google_places_key* entry, in the strings.xml file.


### Libraries

This project uses:

* [Butterknife](http://jakewharton.github.io/butterknife/)
* [Timber](https://github.com/JakeWharton/timber)
* [Google Maps and Location](https://developers.google.com/maps/documentation/android-api)
* [Google Places](https://developers.google.com/places/android-api)


### Capstone Stage 1 Specs

The *Capstone_Stage1.pdf* file is located under _/docs_ dir.

### Screenshots

| Phone - Transactions | Phone - Add Transaction |
|---|---|
| ![Phone - Transactions](mocks/screenshots/phone_tx.png) | ![Phone - Add Transaction](mocks/screenshots/phone_add_tx.png) |

| Tablet - Transactions | 
|---|
| ![Tablet Transactions](mocks/screenshots/tablet_transactions.png) | 

| Tablet - Add Transaction | 
|---|
| ![Tablet Add Transaction](mocks/screenshots/tablet_add_tx.png) | 

| Notification | Home Screen Widget |
|---|---|
| ![Notification](mocks/screenshots/app_notification.png) | ![Home Screen Widget](mocks/screenshots/widget_preview.png) |

| Google Places Suggestions |
|---|
| ![Google Places Suggestions](mocks/screenshots/google_suggestions.png) |
