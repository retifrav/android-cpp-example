# Android C++ example

An example of using a pre-build C++ library in Android application with Kotlin.

More details in the [following article](https://decovar.dev/) (*not published yet*).

<!-- MarkdownTOC -->

- [Building C++ library](#building-c-library)
- [Running Android application](#running-android-application)

<!-- /MarkdownTOC -->

## Building C++ library

``` sh
$ cd ./cpp
$ mkdir build && cd $_

$ export ANDROID_NDK_VERSION='25.1.8937393'
$ export ANDROID_NDK_HOME="$HOME/Library/Android/sdk/ndk/$ANDROID_NDK_VERSION"

$ cmake -G Ninja -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX="../install" \
    -DCMAKE_TOOLCHAIN_FILE="${ANDROID_NDK_HOME}/build/cmake/android.toolchain.cmake" \
    -DANDROID_ABI=arm64-v8a -DANDROID_PLATFORM=30 \
    -DBUILD_SHARED_LIBS=1 \
    ..
$ cmake --build . --target install
```

## Running Android application

1. Copy `./cpp/install/lib/libThingy.so` to `./android/app/jni/libs/arm64-v8a/`;
2. Open `./android` folder in Android Studio and click Run.
