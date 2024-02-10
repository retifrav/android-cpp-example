#include <jni.h>
#include <string>

// include the external library public header
#include <Thingy/thingy.h>

// that's a wrapper's "own" function
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_some_MainActivity_wrapperMessage(
    JNIEnv *env,
    jobject
)
{
    std::string msg = "some message directly from wrapper";
    return env->NewStringUTF(msg.c_str());
}

// and this one is a wrapped call to an external library function
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_some_MainActivity_doThingy(
    JNIEnv *env,
    jobject
)
{
    std::string msg = dpndnc::doThingy();
    return env->NewStringUTF(msg.c_str());
}
