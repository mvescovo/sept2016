# Software Engineering Process and Tools 2016

A Java weather program to demonstrate software engineering process and tools. This is the product of the assignment.

# Features

- Select any Australian weather station (based on BOM list).
- View some history for a selected weather station.
- View some forecasts for a selected weather station.
- See that data in the form of a chart.
- Refresh the currently displayed weather data.
- Save weather stations to a favourite list.
- Remove a weather station from the favourite list.

# Screenshots
<img src="https://cloud.githubusercontent.com/assets/15829736/22180274/b063848e-e0bf-11e6-9835-065644a312f6.png" height="351" width="500">
<img src="https://cloud.githubusercontent.com/assets/15829736/22180291/0cf92adc-e0c0-11e6-9af4-4e5c59632d67.png" height="351" width="500">

# Install

This program uses the Gradle build system for importing libraries and was built with IntelliJ. For Eclipse there is the Gradle Buildship plugin.

###### API keys

The app uses [https://openweathermap.org](https://openweathermap.org) and [forecast.io](forecast.io).

# Issues
- The current version is incomplete and partly broken. Only two stations are available due to missing lat/lon data. This is an issue due to one of the weather API's requiring lat/lon data and not offering a city name option. By eliminating the restrictive API (forecast.io) this problem can be resolved.

# Licence
[![AUR](https://img.shields.io/aur/license/yaourt.svg)]()

[GNU General Public License v3.0](http://choosealicense.com/licenses/gpl-3.0/)
