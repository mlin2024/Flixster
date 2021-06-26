# Project 2 - *Flixster*

**Flixster** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **~18** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* [x] For each movie displayed, user can see the following details:
  * [x] Poster Image, Video Trailer, Title, Average Rating, Overview (Portrait mode)
  * [x] Poster Image, Video Trailer, Title, Average Rating, Overview (Landscape mode)
* [x] Allow user to view details of the movie including ratings within a separate activity

The following **stretch** features are implemented:

* [x] Improved the user interface by experimenting with styling and coloring.
* [x] Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations)
* [x] Apply the popular [View Binding annotation library](http://guides.codepath.org/android/Reducing-View-Boilerplate-with-ViewBinding) to reduce boilerplate code.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView from the details screen.

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='walkthrough.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](https://www.cockos.com/licecap/).

## Notes

When implementing the text wrapping with the FlowTextView library, it initially cut off the text after a certain point rather than extending the view to
encapsulate all of the text. However, I eventually realized that the issue solved itself if the view were unbound and then rebound to a ViewHolder (i.e.,
if the user scrolled away far enough to unbind the view from the ViewHolder, and then scrolled back to the view to bind it to a ViewHolder again). I puzzled
over this bizzare behavior long after logging off that night, even considering that the FlowTextView library may just be too old (it was last updated 7 years
ago). However, the next morning when I logged in, **lo and behold** - the problem had resolved itself. I never did figure out what was causing the strange
behavior, but as a developer I have learned to not look a gift horse in the mouth.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android
- [FlowTextView](https://github.com/deano2390/FlowTextView) - A wrapping TextView for Android

## License

    Copyright [2021] [Meghan Lin]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
