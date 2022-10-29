package org.citra.citra_leia.features.settings.model;

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
