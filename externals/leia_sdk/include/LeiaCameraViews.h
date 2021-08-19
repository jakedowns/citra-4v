/****************************************************************
*
* Copyright 2018 © Leia Inc.  All rights reserved.
*
* NOTICE:  All information contained herein is, and remains
* the property of Leia Inc. and its suppliers, if any.  The
* intellectual and technical concepts contained herein are
* proprietary to Leia Inc. and its suppliers and may be covered
* by U.S. and Foreign Patents, patents in process, and are
* protected by trade secret or copyright law.  Dissemination of
* this information or reproduction of this materials strictly
* forbidden unless prior written permission is obtained from
* Leia Inc.
*
****************************************************************/

#ifndef LEIA_CAMERA_VIEWS_H
#define LEIA_CAMERA_VIEWS_H

#ifdef __cplusplus
extern "C" {
#endif

static const float PI = 3.14159f;
static const float DEGREES = 360.0f;
static const unsigned int MATRIX_WIDTH = 4;
static const float HARDWARE_FACTOR = 0.16f;

typedef struct LeiaCamView {
    float matrix[16];
    float _reserved[16];
}LeiaCameraView;

typedef struct LeiaCamData {
    float mSystemDisparityPixels;
    float mBaselineScaling;
    float mConvergenceDistance;
    float mVerticalFieldOfView;
    float mViewResXPixels;
    float mViewResYPixels;
    float mNear;
    float mFar;
    float mProjectionPlaneHalfWidth;
    float mProjectionPlaneHalfHeight;
    float mBaseline;
    float mDeltaZ;
    float mFPercentHeight;
    int mNumViewsHorizontal;
    int mNumViewsVertical;
    float _reserved[8];
}LeiaCameraData;

void leiaInitializeCameraData(LeiaCameraData* data,
                              int num_horizontal_views, int num_vertical_views,
                              float system_disparity_in_pixels, float baseline_scaling,
							  float convergence_distance, float vertical_field_of_view_degrees,
                              float near, float far, int view_resolution_x_pixels, int view_resolution_y_pixels);
int leiaCalculateViews(LeiaCameraData* data, LeiaCameraView* out_views, int len_views_in_x, int len_views_in_y);

void leiaSetNumberOfViews(LeiaCameraData* data, int num_horizontal_views, int num_vertical_views);
void leiaSetSystemDisparityInPixels(LeiaCameraData* data, float disparity_in_pixels);
void leiaSetApplicationBaselineScale(LeiaCameraData* data, float baseline_scaling);
void leiaSetFieldOfView(LeiaCameraData* data, float fov_in_degrees);
void leiaSetFrustumPlanes(LeiaCameraData* data, float near, float focal_distance, float far);
void leiaSetViewSizeInPixels(LeiaCameraData* data, int resolution_x, int resolution_y);

#ifdef __cplusplus
}
#endif

#endif // LEIA_CAMERA_VIEWS_H
