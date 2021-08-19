
#ifndef LEIA_JNI_CAMERA_PARAMETERS_H
#define LEIA_JNI_CAMERA_PARAMETERS_H

#include <string>
#include <jni.h>
#include <android/native_activity.h>

class LeiaJNIDisplayParameters {
public:
    static bool ReadSystemParameters(ANativeActivity* activity);
    static bool ReadSystemViewSharpeningParameters(ANativeActivity* activity);
    static bool ReadSystemAlignmentOffset(ANativeActivity* activity);
    static bool ReadSystemDisparity(ANativeActivity* activity);
    static bool ReadSystemScreenResolution(ANativeActivity* activity);
    static bool ReadSystemNumAvailableViews(ANativeActivity* activity);
    static bool ReadSystemViewResolution(ANativeActivity* activity);

    static float mViewSharpeningParams[2];
    static int mSystemDisparity;
    static int mAlignmentOffset;
    static int mScreenResolution[2];
    static int mNumAvailableViews[2];
    static int mViewResolution[2];
private:
    enum LEIA_SYSTEM_PARAMETERS {
        LEIA_VIEW_SHARPENING,
        LEIA_ALIGNMENT_OFFSET,
        LEIA_DISPARITY,
        LEIA_SCREEN_RESOLUTION,
        LEIA_NUM_AVAILABLE_VIEWS,
        LEIA_VIEW_RESOLUTION,

        NUMBER_OF_SYSTEM_PARAMETERS
    };

    static jmethodID GetMethod(ANativeActivity* activity,
                               JNIEnv* jni,
                               LEIA_SYSTEM_PARAMETERS system_query_type);

    static std::string mMethodNames[NUMBER_OF_SYSTEM_PARAMETERS];
    static std::string mMethodSignatures[NUMBER_OF_SYSTEM_PARAMETERS];
};

#endif // LEIA_JNI_CAMERA_PARAMETERS_H
