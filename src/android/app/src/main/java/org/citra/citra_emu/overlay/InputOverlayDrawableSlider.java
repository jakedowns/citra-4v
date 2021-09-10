package org.citra.citra_emu.overlay;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;

import org.citra.citra_emu.R;
import org.citra.citra_emu.NativeLibrary;
import org.citra.citra_emu.activities.EmulationActivity;
import org.citra.citra_emu.features.settings.model.Setting;
import org.citra.citra_emu.features.settings.model.SettingSection;
import org.citra.citra_emu.features.settings.model.Settings;
import org.citra.citra_emu.features.settings.model.view.SliderSetting;
import org.citra.citra_emu.ui.main.MainActivity;
import org.citra.citra_emu.utils.EmulationMenuSettings;
import org.citra.citra_emu.utils.Log;
import org.citra.citra_emu.features.settings.utils.SettingsFile;

public final class InputOverlayDrawableSlider {

    private int sliderId;
    private int mControlPositionX, mControlPositionY;
    private int mPreviousTouchX, mPreviousTouchY;
    private int mWidth;
    private int mHeight;
    private int mAlpha;
    private float mSliderPositionY;
    private float mSliderRelativePosition;
    private Rect mVirtBounds;
    private Rect mOrigBounds;
    private BitmapDrawable mOuterBitmap;
    private BitmapDrawable mDefaultStateInnerBitmap;
    private BitmapDrawable mPressedStateInnerBitmap;
    private BitmapDrawable mBoundsBoxBitmap;
    private boolean mPressedState = false;

    /**
     * Constructor
     * @param res                {@link Resources} instance.
     * @param bitmapOuter        {@link Bitmap} which represents the outer non-movable part of the slider.
     * @param bitmapInnerDefault {@link Bitmap} which represents the default inner movable part of the slider.
     * @param bitmapInnerPressed {@link Bitmap} which represents the pressed inner movable part of the slider.
     * @param rectOuter          {@link Rect} which represents the outer slider bounds.
     * @param rectInner          {@link Rect} which represents the inner slider bounds.
     * @param sliderId           Identifier for which slider this is.
     * @param alpha           0-255 alpha int
     */
    public InputOverlayDrawableSlider(Resources res, Bitmap bitmapOuter, Bitmap bitmapInnerDefault,
                                      Bitmap bitmapInnerPressed, Rect rectOuter,
                                      Rect rectInner, int sliderId, int alpha) {
        mOuterBitmap = new BitmapDrawable(res, bitmapOuter);
        mDefaultStateInnerBitmap = new BitmapDrawable(res, bitmapInnerDefault);
        mPressedStateInnerBitmap = new BitmapDrawable(res, bitmapInnerPressed);
        mBoundsBoxBitmap = new BitmapDrawable(res, bitmapOuter);
        mWidth = bitmapOuter.getWidth();
        mHeight = bitmapOuter.getHeight();

        mAlpha = alpha;

        mOuterBitmap.setAlpha(alpha);
        mDefaultStateInnerBitmap.setAlpha(alpha);
        mPressedStateInnerBitmap.setAlpha(alpha);

        // TODO: feed relative value in at init time
        // for now: fake it:
        mSliderRelativePosition = 0.3f;
        // reverse calculate px value from relative value
        mSliderPositionY = (int) (mSliderRelativePosition * rectOuter.height());

        setBounds(rectOuter);
        mDefaultStateInnerBitmap.setBounds(rectInner);
        mPressedStateInnerBitmap.setBounds(rectInner);
        mVirtBounds = getBounds();
        mOrigBounds = mOuterBitmap.copyBounds();
        mBoundsBoxBitmap.setAlpha(0);
        mBoundsBoxBitmap.setBounds(getVirtBounds());
        SetInnerBounds();
    }

    public int getId() {
        return sliderId;
    }

    public boolean onConfigureTouch(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int fingerPositionX = (int) event.getX(pointerIndex);
        int fingerPositionY = (int) event.getY(pointerIndex);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreviousTouchX = fingerPositionX;
                mPreviousTouchY = fingerPositionY;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = fingerPositionX - mPreviousTouchX;
                int deltaY = fingerPositionY - mPreviousTouchY;
                mControlPositionX += deltaX;
                mControlPositionY += deltaY;
                setBounds(new Rect(mControlPositionX, mControlPositionY,
                        mOuterBitmap.getIntrinsicWidth() / mControlPositionX,
                        mOuterBitmap.getIntrinsicHeight() / mControlPositionY));
                setVirtBounds(new Rect(mControlPositionX, mControlPositionY,
                        mOuterBitmap.getIntrinsicWidth() / mControlPositionX,
                        mOuterBitmap.getIntrinsicHeight() / mControlPositionY));
                SetInnerBounds();
                setOrigBounds(new Rect(new Rect(mControlPositionX, mControlPositionY,
                        mOuterBitmap.getIntrinsicWidth() / mControlPositionX,
                        mOuterBitmap.getIntrinsicHeight() / mControlPositionY)));
                mPreviousTouchX = fingerPositionX;
                mPreviousTouchY = fingerPositionY;
                break;

        }
        return true;
    }

    public void setPosition(int x, int y) {
        mControlPositionX = x;
        mControlPositionY = y;
    }

    private void SetInnerBounds() {
//        int X = getVirtBounds().centerX() + (int) ((getVirtBounds().width() / 2));
//        int Y = getVirtBounds().centerY() + (int) ((getVirtBounds().height() / 2));
//
//        if (X > getVirtBounds().centerX() + (getVirtBounds().width() / 2))
//            X = getVirtBounds().centerX() + (getVirtBounds().width() / 2);
//        if (X < getVirtBounds().centerX() - (getVirtBounds().width() / 2))
//            X = getVirtBounds().centerX() - (getVirtBounds().width() / 2);
//        if (Y > getVirtBounds().centerY() + (getVirtBounds().height() / 2))
//            Y = getVirtBounds().centerY() + (getVirtBounds().height() / 2);
//        if (Y < getVirtBounds().centerY() - (getVirtBounds().height() / 2))
//            Y = getVirtBounds().centerY() - (getVirtBounds().height() / 2);

        int width = mPressedStateInnerBitmap.getBounds().width();
        int height = mPressedStateInnerBitmap.getBounds().height();
        int X = mControlPositionX; //mPressedStateInnerBitmap.getBounds().left;
        mDefaultStateInnerBitmap.setBounds(X,
                (int)mSliderPositionY - (height/2),
                X + width,
                (int)mSliderPositionY + (height/2));
        mPressedStateInnerBitmap.setBounds(mDefaultStateInnerBitmap.getBounds());
    }

    public Rect GetInnerBounds(){
        return mDefaultStateInnerBitmap.getBounds();
    }

    public void draw(Canvas canvas) {
        mOuterBitmap.draw(canvas);
        getCurrentStateBitmapDrawable().draw(canvas);
        mBoundsBoxBitmap.draw(canvas);
    }

    public void TrackEvent(MotionEvent event, View view) {
        int pointerIndex = event.getActionIndex();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (GetInnerBounds().contains((int) event.getX(pointerIndex), (int) event.getY(pointerIndex))) {
                    mPressedState = true;
                    mOuterBitmap.setAlpha(0);
                    mBoundsBoxBitmap.setAlpha(mAlpha);
//                    if (EmulationMenuSettings.getJoystickRelCenter()) {
//                        getVirtBounds().offset((int) event.getX(pointerIndex) - getVirtBounds().centerX(),
//                                (int) event.getY(pointerIndex) - getVirtBounds().centerY());
//                    }
                    mBoundsBoxBitmap.setBounds(getVirtBounds());
//                    trackId = event.getPointerId(pointerIndex);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mPressedState = false;
                mOuterBitmap.setAlpha(mAlpha);
                mBoundsBoxBitmap.setAlpha(0);
                setVirtBounds(new Rect(mOrigBounds.left, mOrigBounds.top, mOrigBounds.right,
                        mOrigBounds.bottom));
                setBounds(new Rect(mOrigBounds.left, mOrigBounds.top, mOrigBounds.right,
                        mOrigBounds.bottom));
                SetInnerBounds();
                break;
        }

        if (GetInnerBounds().contains((int) event.getX(pointerIndex), (int) event.getY(pointerIndex))) {
            for (int i = 0; i < event.getPointerCount(); i++) {
                float touchY = event.getY(i);
                float minY = getVirtBounds().top;
                float maxY = getVirtBounds().bottom;
                mSliderPositionY = Math.min(maxY, Math.max(minY, touchY));
                mSliderRelativePosition = 1.f - ((mSliderPositionY - minY) / getVirtBounds().height());

                Log.info(String.valueOf(mSliderPositionY) + " " + String.valueOf(mSliderRelativePosition));

                Settings mSettings = MainActivity.getSettingsViewModel().getSettings();
                if (mSettings != null) {
//                     this is going to take some thinking, cause we probably don't want to
//                     recreate ini file during drag events, we should debounce it
//                     but I DO want to pass the new value to the emulator in real time or near real time
                    // trying it the naive way first
                    SettingSection rendererSection =
                            mSettings.getSection(Settings.SECTION_RENDERER);
                    Setting factor3d = rendererSection.getSetting(SettingsFile.KEY_FACTOR_3D);
                    // updated setting is a new instance
                    SliderSetting factor3DSettingUpdated = new SliderSetting(
                            SettingsFile.KEY_FACTOR_3D,
                            Settings.SECTION_RENDERER,
                            R.string.factor3d,
                            R.string.factor3d_description,
                            0,
                            100,
                            "%",
                            0,
                            factor3d);
                    Log.debug("factor 3d "+String.valueOf((int)(mSliderRelativePosition * 100)));
                    factor3DSettingUpdated.setSelectedValue((int)(mSliderRelativePosition * 100));
                    rendererSection.putSetting(factor3DSettingUpdated.getSetting());
                    try {
                        mSettings.saveSettingsSilent();
                        NativeLibrary.ReloadSettings();
                    } catch (Exception e){

                    }
                }

                // This library method is not implemented :(
//                    NativeLibrary.SetUserSetting("",
//                            Settings.SECTION_RENDERER,
//                            SettingsFile.KEY_FACTOR_3D,
//                            String.valueOf(mSliderRelativePosition));
                SetInnerBounds();

                // TODO: here is where i need to update depth in INI (or out in EmuActivity where we call this)
                // and... make sure to fire whatever is needed to trigger the value
                // update within the native process
                //            }
            }
        }
    }

    private BitmapDrawable getCurrentStateBitmapDrawable() {
        return mPressedState ? mPressedStateInnerBitmap : mDefaultStateInnerBitmap;
    }

    public void setBounds(Rect bounds) {
        mOuterBitmap.setBounds(bounds);
    }

    public Rect getBounds() {
        return mDefaultStateInnerBitmap.getBounds();
    }

    private void setOrigBounds(Rect bounds) {
        mOrigBounds = bounds;
    }

    private Rect getVirtBounds() {
        return mVirtBounds;
    }

    private void setVirtBounds(Rect bounds) {
        mVirtBounds = bounds;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setPressedState(boolean isPressed) {
        mPressedState = isPressed;
    }
}
