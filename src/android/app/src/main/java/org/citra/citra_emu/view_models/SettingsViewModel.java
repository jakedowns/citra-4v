package org.citra.citra_emu.view_models;

import android.app.Activity;

import org.citra.citra_emu.features.settings.model.Settings;
import org.citra.citra_emu.view_models.SettingsListener;

public class SettingsViewModel {
    private final Activity mActivity;
    private Settings mSettings;
    private SettingsListener listenerActivity;

    public SettingsViewModel(Activity activity) {
        mActivity = activity;
    }

    public Settings getSettings(){
        return mSettings;
    }

    public void setSettings(Settings settings){
        mSettings = settings;
        // check if listener is set.
        if (this.listenerActivity != null) {
            // invoke the callback method
            this.listenerActivity.onSettingsSaved(settings);
        }
    }

    public void registerListenerActivity(SettingsListener listenerActivity){
        this.listenerActivity = listenerActivity;
    }
}
