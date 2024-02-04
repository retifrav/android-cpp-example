#include <sstream>
#include <jni.h>

#include <Thingy/thingy.h>
#include "stuff.h"

namespace dpndnc
{
    extern "C" JNIEXPORT jstring JNICALL
    Java_com_example_some_MainActivity_doThingy(JNIEnv *env, jobject) // functions has to have this prefix
    {
        std::stringstream someThing;
        someThing << "a string from C++: " << thingyString;
        return env->NewStringUTF(someThing.str().c_str());
    }
}
