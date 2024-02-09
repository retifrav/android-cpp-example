#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_some_MainActivity_doThingy(
    JNIEnv *env,
    jobject
)
{
    std::string some = "some string of text from wrapper";
    return env->NewStringUTF(some.c_str());
}
