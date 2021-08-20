package org.citra.citra_emu.features.settings.model;

import org.citra.citra_emu.features.settings.model.Settings;
import org.citra.citra_emu.features.settings.model.SettingsListener;

public class SettingsListenerClass implements SettingsListener {
    private SettingsListener mParent;

    public SettingsListenerClass(SettingsListener mParent){
        this.mParent = mParent;
    }

    /**
     * This method is called when settings are loaded from disk or saved to disk
     * @param settings
     */
    @Override
    public void updatedSettingsAvailable(Settings settings){
        if(this.mParent != null){
            this.mParent.updatedSettingsAvailable(settings);
        }
    }

    @Override
    public void onSettingsFileNotFound(){
        // call parent so SettingsActivity can know to use defaults instead
        if(this.mParent != null){
            this.mParent.onSettingsFileNotFound();
        }
    }
}
