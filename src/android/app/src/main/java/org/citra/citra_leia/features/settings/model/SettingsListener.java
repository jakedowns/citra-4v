package org.citra.citra_leia.features.settings.model;

public interface SettingsListener {
    public void updatedSettingsAvailable(Settings settings);
    public void onSettingsFileNotFound();
}
