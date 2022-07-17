
What is this project?
This course will replace my old java mvvm introduction: https://codingwithmitch.com/courses/rest-api-mvvm-retrofit2/.

This is a beginner course.

Main Features
Kotlin
MVVM
Jetpack Compose
MutableState
Simpler than LiveData or Flow with respect to composables.
Hilt
Navigation Components
Retrofit2
Single Activity Architecture
App Themes
Custom Fonts
Light and Dark theme
Toggle between themes
Compose features
Snackbars
Dialogs
Theming
Fonts
Colors
Animations introduction
creating a 'shimmering' loading animation
ConstraintLayout
Rows
Columns
Scaffold
AppBar
Circular Progress Indicator
Why not Flow or LiveData?
LiveData
LiveData has always been a love/hate thing for me. It's great for some things but can be very annoying for others. For example you MUST have an observer for livedata to emit anything. Making it pretty useless in a repository or anywhere except viewmodel->view communication.
Flow
Flow is great. I use flows for my use cases when building out clean architecture. You don't need an "android observer" because flow lives in the coroutine world and there's even support for operators like RxJava has.
Easily converted from livedata with a single function call.
Tons of other useful properties. Check out the flow docs.
StateFlow
StateFlow is the newest addition to kotlinx.coroutines.flow. Simply put StateFlow is a flow with special properties.
Originally I had planned to use StateFlow in this course but it's really just totally unnecessary. I don't want to show you an unrealistic example of how to use something so I'm stinking with the very simple MutableState in viewmodels.
References
https://github.com/android/compose-samples
https://developer.android.com/jetpack/compose
https://developer.android.com/jetpack/compose/state
Color system
https://material.io/design/color/the-color-system.html
