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

// this one is a wrapped call to an external library function
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_some_MainActivity_doThingy(
    JNIEnv *env,
    jobject
)
{
    std::string msg = dpndnc::doThingy();
    return env->NewStringUTF(msg.c_str());
}

// and this one is a wrapped call to a different function of the same external library
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_some_MainActivity_whoHasTheBestBoobs(
    JNIEnv *env,
    jobject,
    jstring grils // don't forget to add this as an argument to the function in MainActivity class
)
{
    // apparently, that becomes true (JNI_TRUE) at some point
    jboolean isCopy;
    // pointer to an array of bytes representing the string in modified UTF-8 encoding
    // https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#GetStringUTFChars
    const char *utf = (env)->GetStringUTFChars(grils, &isCopy);

    // only now we can pass the string to C++ side
    std::string gril = dpndnc::whoHasTheBestBoobs(utf);

    // after the string is done being used, it needs to be "released" (to prevent memory leak?)
    // https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#ReleaseStringUTFChars
    if (isCopy == JNI_TRUE) { env->ReleaseStringUTFChars(grils, utf); }

    return env->NewStringUTF(gril.c_str());
}
