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

$ echo $VCPKG_ROOT

$ cmake -G Ninja -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX="../install" \
    -DCMAKE_TOOLCHAIN_FILE="$VCPKG_ROOT/scripts/buildsystems/vcpkg.cmake" \
    -DVCPKG_TARGET_TRIPLET="arm64-android-v8a-v30" \
    -DVCPKG_OVERLAY_TRIPLETS="/ABSOLUTE/PATH/TO/cpp/misc/vcpkg/triplets" \
    -DVCPKG_CHAINLOAD_TOOLCHAIN_FILE="${ANDROID_NDK_HOME}/build/cmake/android.toolchain.cmake" \
    -DANDROID_ABI=arm64-v8a -DANDROID_PLATFORM=30 \
    ..
$ cmake --build . --target install

$ cp -an ./vcpkg_installed/arm64-android-v8a-v30/* ../install/
$ ls -L1 ../install/
```

## Running Android application

1. Open `./android` folder in Android Studio;
2. Set `CMAKE_PREFIX_PATH` to `/ABSOLUTE/PATH/TO/cpp/install` in `./android/app/build.gradle.kts`;
3. Make/build the project or just click `Run 'app'`.
