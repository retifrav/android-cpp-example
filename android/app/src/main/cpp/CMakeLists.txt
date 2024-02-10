cmake_minimum_required(VERSION 3.22.1) # it wants an exact version, so 3.22 isn't precise enough

project("cpp-wrapper")

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED 1)

# if you set CMAKE_DEBUG_POSTFIX, then you might need to hardcode the build type to Release,
# otherwise you'll need to add the postfix value to the library name in `System.loadLibrary()`
#set(CMAKE_DEBUG_POSTFIX "d" CACHE STRING "Filename postfix for libraries under DEBUG configuration")
#set(CMAKE_BUILD_TYPE "Release")

add_library(${CMAKE_PROJECT_NAME} SHARED) # it probably makes sense to hardcode it to SHARED right away

target_sources(${CMAKE_PROJECT_NAME}
    PRIVATE
        wrapper.cpp
)

#set(CMAKE_FIND_DEBUG_MODE 1)
find_package(Thingy CONFIG REQUIRED)
#set(CMAKE_FIND_DEBUG_MODE 0)

target_link_libraries(${CMAKE_PROJECT_NAME}
    PRIVATE
        some::Thingy
        # these might not be needed
        #android
        #log
)
