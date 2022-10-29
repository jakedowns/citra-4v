package org.citra.citra_leia.features.settings.ui;

import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import org.citra.citra_leia.NativeLibrary;
import org.citra.citra_leia.features.settings.model.Settings;
import org.citra.citra_leia.features.settings.utils.SettingsFile;
import org.citra.citra_leia.ui.main.MainActivity;
import org.citra.citra_leia.utils.DirectoryInitialization;
import org.citra.citra_leia.utils.DirectoryInitialization.DirectoryInitializationState;
import org.citra.citra_leia.utils.DirectoryStateReceiver;
import org.citra.citra_leia.utils.Log;
import org.citra.citra_leia.utils.ThemeUtil;

import java.io.File;

public final class SettingsActivityPresenter {
    private static final String KEY_SHOULD_SAVE = "should_save";

    private SettingsActivityView mView;

    private Settings mSettings = new Settings();

    private int mStackCount;

    private boolean mShouldSave;

    private DirectoryStateReceiver directoryStateReceiver;

    private String menuTag;
    private String gameId;

    public SettingsActivityPresenter(SettingsActivityView view) {
        mView = view;
    }

    public void onCreate(Bundle savedInstanceState, String menuTag, String gameId) {
        if (savedInstanceState == null) {
            this.menuTag = menuTag;
            this.gameId = gameId;
        } else {
            mShouldSave = savedInstanceState.getBoolean(KEY_SHOULD_SAVE);
        }
    }

    public void onStart() {
        this.mStackCount = 0;
        prepareCitraDirectoriesIfNeeded();
    }

    void loadSettingsUI() {
        if (mSettings.isEmpty()) {
            if (!TextUtils.isEmpty(gameId)) {
                mSettings.loadSettings(gameId, mView);
            } else {
                mSettings.loadSettings(mView);
            }
        }

        mView.showSettingsFragment(menuTag, false, gameId);
        mView.onSettingsFileLoaded(mSettings);
    }

    private void prepareCitraDirectoriesIfNeeded() {
        File configFile = new File(DirectoryInitialization.getUserDirectory() + "/config/" + SettingsFile.FILE_NAME_CONFIG + ".ini");
        if (!configFile.exists()) {
            Log.error("Citra config file could not be found!");
        }
        if (DirectoryInitialization.areCitraDirectoriesReady()) {
            loadSettingsUI();
        } else {
            mView.showLoading();
            IntentFilter statusIntentFilter = new IntentFilter(
                    DirectoryInitialization.BROADCAST_ACTION);

            directoryStateReceiver =
                    new DirectoryStateReceiver(directoryInitializationState ->
                    {
                        if (directoryInitializationState == DirectoryInitializationState.CITRA_DIRECTORIES_INITIALIZED) {
                            mView.hideLoading();
                            loadSettingsUI();
                        } else if (directoryInitializationState == DirectoryInitializationState.EXTERNAL_STORAGE_PERMISSION_NEEDED) {
                            mView.showPermissionNeededHint();
                            mView.hideLoading();
                        } else if (directoryInitializationState == DirectoryInitializationState.CANT_FIND_EXTERNAL_STORAGE) {
                            mView.showExternalStorageNotMountedHint();
                            mView.hideLoading();
                        }
                    });

            mView.startDirectoryInitializationService(directoryStateReceiver, statusIntentFilter);
        }
    }

    public void setSettings(Settings settings) {
        mSettings = settings;
    }

    public Settings getSettings() {
        return mSettings;
    }

    public void onStop(boolean finishing) {
        if (directoryStateReceiver != null) {
            mView.stopListeningToDirectoryInitializationService(directoryStateReceiver);
            directoryStateReceiver = null;
        }

        MainActivity.getSettingsViewModel().setSettings(mSettings);

        if (mSettings != null && finishing && mShouldSave) {
            Log.debug("[SettingsActivity] Settings activity stopping. Saving settings to INI...");
            mSettings.saveSettings(mView);
        }


        ThemeUtil.applyTheme();

        NativeLibrary.ReloadSettings();
    }

    public void addToStack() {
        mStackCount++;
    }

    public void onBackPressed() {
        if (mStackCount > 0) {
            mView.popBackStack();
            mStackCount--;
        } else {
            mView.finish();
        }
    }

    public void onSettingChanged() {
        mShouldSave = true;
    }

    public void saveState(Bundle outState) {
        outState.putBoolean(KEY_SHOULD_SAVE, mShouldSave);
    }
}
