package org.citra.citra_emu.view_models;

import org.citra.citra_emu.features.settings.model.Settings;

public interface SettingsListener {
    public void onSettingsSaved(Settings settings);
}
