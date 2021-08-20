package org.citra.citra_emu.features.settings.model;

import android.app.Activity;
import java.util.ArrayList;

public class SettingsViewModel {
    private final Activity mActivity;
    private Settings mSettings;
    private ArrayList<SettingsListener> listenerActivities = new ArrayList<SettingsListener>();

    public SettingsViewModel(Activity activity) {
        mActivity = activity;
    }

    public Settings getSettings(){
        return mSettings;
    }

    public void setSettings(Settings settings){
        mSettings = settings;
        int i;
        for(i = 0; i < this.listenerActivities.size(); i++){
            if(this.listenerActivities.get(i) != null){
                // invoke the callback method
                this.listenerActivities.get(i).updatedSettingsAvailable(settings);
            }
        }
    }

    public void loadSettingsFile(SettingsListener listener){
        mSettings = new Settings();
        // listener will get a callback to updatedSettingsAvailable
        // when the async file load is complete
        mSettings.loadSettings(listener);
    }

    public SettingsViewModelListenerID registerListenerActivity(SettingsListener listenerActivity){
        this.listenerActivities.add(listenerActivity);
        SettingsViewModelListenerID id = new SettingsViewModelListenerID(this.listenerActivities.size() - 1);
        return id;
    }

    public void deregisterListenerActivity(SettingsViewModelListenerID id){
        this.listenerActivities.set(id.getID(), null);
    }
}
