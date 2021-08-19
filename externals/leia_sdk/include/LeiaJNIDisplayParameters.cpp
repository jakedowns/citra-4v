
#include "LeiaJNIDisplayParameters.h"
#include <android/native_activity.h>

float LeiaJNIDisplayParameters::mViewSharpeningParams[2] = {0.0f, 0.0f};
int LeiaJNIDisplayParameters::mSystemDisparity = 0;
int LeiaJNIDisplayParameters::mAlignmentOffset = 0;
int LeiaJNIDisplayParameters::mScreenResolution[2] = {0, 0};
int LeiaJNIDisplayParameters::mNumAvailableViews[2] = {0, 0};
int LeiaJNIDisplayParameters::mViewResolution[2] = {0, 0};
std::string LeiaJNIDisplayParameters::mMethodNames[NUMBER_OF_SYSTEM_PARAMETERS] =
        {
                "GetViewSharpening",
                "GetAlignmentOffset",
                "GetSystemDisparity",
                "GetScreenResolution",
                "GetNumAvailableViews",
                "GetViewResolution"
        };

std::string LeiaJNIDisplayParameters::mMethodSignatures[NUMBER_OF_SYSTEM_PARAMETERS] =
        {
                "()[F",
                "()F",
                "()I",
                "()[I",
                "()[I",
                "()[I"
        };

bool LeiaJNIDisplayParameters::ReadSystemParameters(ANativeActivity* activity) {
    bool act = ReadSystemViewSharpeningParameters(activity);
    bool offset = ReadSystemAlignmentOffset(activity);
    bool disparity = ReadSystemDisparity(activity);
    bool res = ReadSystemScreenResolution(activity);
    bool views = ReadSystemNumAvailableViews(activity);
    bool dims = ReadSystemViewResolution(activity);

    bool param_read = false;
    if (act && offset && disparity && res && views && dims) {
        param_read = true;
    }
    return param_read;
}

bool LeiaJNIDisplayParameters::ReadSystemViewSharpeningParameters(ANativeActivity* activity) {
    bool act_retreived = false;

    JNIEnv* jni;
    activity->vm->AttachCurrentThread(&jni, NULL);
    jmethodID methodID = GetMethod(activity, jni, LEIA_VIEW_SHARPENING);
    jfloatArray params = (jfloatArray)jni->CallObjectMethod(activity->clazz, methodID);

    jfloat* p = jni->GetFloatArrayElements(params, 0);
    if (p) {
        for (int i = 0; i < 2; ++i) {
            mViewSharpeningParams[i] = p[i];
        }
        act_retreived = true;
    }
    activity->vm->DetachCurrentThread();
    return act_retreived;
}

bool LeiaJNIDisplayParameters::ReadSystemAlignmentOffset(ANativeActivity* activity) {
    bool calib_retreived = true;

    JNIEnv* jni;
    activity->vm->AttachCurrentThread(&jni, NULL);
    jmethodID methodID = GetMethod(activity, jni, LEIA_ALIGNMENT_OFFSET);
    mAlignmentOffset = (int)jni->CallFloatMethod(activity->clazz, methodID);
    activity->vm->DetachCurrentThread();
    return calib_retreived;
}

bool LeiaJNIDisplayParameters::ReadSystemDisparity(ANativeActivity* activity) {
    bool disparity_retreived = true;

    JNIEnv* jni;
    activity->vm->AttachCurrentThread(&jni, NULL);
    jmethodID methodID = GetMethod(activity, jni, LEIA_DISPARITY);
    mSystemDisparity = (int)jni->CallIntMethod(activity->clazz, methodID);
    activity->vm->DetachCurrentThread();
    return disparity_retreived;
}

bool LeiaJNIDisplayParameters::ReadSystemScreenResolution(ANativeActivity* activity) {
    bool screen_resolution_received = false;

    JNIEnv* jni;
    activity->vm->AttachCurrentThread(&jni, NULL);
    jmethodID methodID = GetMethod(activity, jni, LEIA_SCREEN_RESOLUTION);
    jintArray params = (jintArray)jni->CallObjectMethod(activity->clazz, methodID);

    jint* p = jni->GetIntArrayElements(params, 0);
    if (p) {
        for (int i = 0; i < 2; ++i) {
            mScreenResolution[i] = p[i];
        }
        screen_resolution_received = true;
    }
    activity->vm->DetachCurrentThread();
    return screen_resolution_received;
}

bool LeiaJNIDisplayParameters::ReadSystemNumAvailableViews(ANativeActivity* activity) {
    bool view_dims_received = false;

    JNIEnv* jni;
    activity->vm->AttachCurrentThread(&jni, NULL);
    jmethodID methodID = GetMethod(activity, jni, LEIA_NUM_AVAILABLE_VIEWS);
    jintArray params = (jintArray)jni->CallObjectMethod(activity->clazz, methodID);

    jint* p = jni->GetIntArrayElements(params, 0);
    if (p) {
        for (int i = 0; i < 2; ++i) {
            mNumAvailableViews[i] = p[i];
        }
        view_dims_received = true;
    }
    activity->vm->DetachCurrentThread();
    return view_dims_received;
}

bool LeiaJNIDisplayParameters::ReadSystemViewResolution(ANativeActivity* activity) {
    bool view_dims_received = false;

    JNIEnv* jni;
    activity->vm->AttachCurrentThread(&jni, NULL);
    jmethodID methodID = GetMethod(activity, jni, LEIA_VIEW_RESOLUTION);
    jintArray params = (jintArray)jni->CallObjectMethod(activity->clazz, methodID);

    jint* p = jni->GetIntArrayElements(params, 0);
    if (p) {
        for (int i = 0; i < 2; ++i) {
            mViewResolution[i] = p[i];
        }
        view_dims_received = true;
    }
    activity->vm->DetachCurrentThread();
    return view_dims_received;
}

jmethodID LeiaJNIDisplayParameters::GetMethod(ANativeActivity* activity,
                    JNIEnv* jni,
                    LEIA_SYSTEM_PARAMETERS system_query_type) {
    jclass clazz = jni->GetObjectClass(activity->clazz);
    return jni->GetMethodID(clazz, mMethodNames[system_query_type].c_str(),
                            mMethodSignatures[system_query_type].c_str());
}