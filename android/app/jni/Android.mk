LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := thingy
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libThingy.so
include $(PREBUILT_SHARED_LIBRARY)
