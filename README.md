# TheMovies
![license](https://img.shields.io/badge/license-MIT%20License-blue.svg) 
[![Build Status](https://travis-ci.org/skydoves/TheMovies.svg?branch=master)](https://travis-ci.org/skydoves/TheMovies) <br>

A simple project for [The Movie DB](https://www.themoviedb.org) based on Kotlin MVVM clean architecture and material design & animations.<br>

![gif0](https://user-images.githubusercontent.com/24237865/44523477-f6681180-a714-11e8-8f48-2dd3ca7cc8c0.gif) 
![gif1](https://user-images.githubusercontent.com/24237865/44523478-f6681180-a714-11e8-9597-c885977fe136.gif)

## How to build on your environment
Add your API key in local.properties file.
```xml
tmdb_api_key=YOUR_API_KEY
```

## Development process
Based on Test-driven development.<br>
1. API Service -> API Service Unit Test with api response mock files
2. DAO -> DAO Unit Test
3. Repository -> Repository Unit Test
4. ViewModel -> ViewModel Unit Test
5. DI & Refactoring
6. Implmentating UI & Layouts <br><br>
![tdd](https://user-images.githubusercontent.com/24237865/44525064-b572fb80-a71a-11e8-9930-e77cde96561f.png)

## Architecture
Based on mvvm architecture and repository pattern.<br><br>
![architecture](https://user-images.githubusercontent.com/24237865/44525736-e9e7b700-a71c-11e8-8045-42c4478dd67e.png)

## Specs & Open-source libraries
- Minimum SDK 16
- 100% Kotlin based, [anko](https://github.com/Kotlin/anko)
- MVVM Architecture
- Architecture Components (Lifecycle, LiveData, ViewModel, Room Persistence)
- DataBinding
- Material Design & Animations
- The Movie DB API
- [Dagger2](https://github.com/google/dagger) for dependency injection
- [Retrofit2 & Gson](https://github.com/square/retrofit) for constructing the REST API
- [OkHttp3](https://github.com/square/okhttp) for implementing interceptor, logging and mocking web server
- [Glide](https://github.com/bumptech/glide) for loading images
- [BaseRecyclerViewAdapter](https://github.com/skydoves/BaseRecyclerViewAdapter) for implementing adapters and viewHolders
- [Mockito-kotlin](https://github.com/nhaarman/mockito-kotlin) for Junit mock test
- [Timber](https://github.com/JakeWharton/timber) for logging
- [Stetho](https://github.com/facebook/stetho) for debugging persistence data & network packets
- Ripple animation, Shared element transition
- Custom Views [AndroidTagView](https://github.com/whilu/AndroidTagView), [ExpandableTextView](https://github.com/Manabu-GT/ExpandableTextView)

## Posting
[Medium - Android MVVM Architecture Components using The Movie Database API](https://medium.com/@skydoves/android-mvvm-architecture-components-using-the-movie-database-api-8fbab128d7)

## Find this library useful? :heart:
Support it by joining __[stargazers](https://github.com/skydoves/TheMovies/stargazers)__ for this repository. :star:

## Supports :coffee:
If you feel like support me a coffee for my efforts, I would greatly appreciate it. <br><br>
<a href="https://www.buymeacoffee.com/skydoves" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/purple_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

# License
```xml
The MIT License (MIT)

Copyright (c) 2018 skydoves

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
