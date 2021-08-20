package org.citra.citra_emu.features.settings.model;

import org.citra.citra_emu.features.settings.model.Settings;

public interface SettingsListener {
    public void updatedSettingsAvailable(Settings settings);
    public void onSettingsFileNotFound();
}
