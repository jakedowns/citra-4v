package org.citra.citra_leia.features.settings.ui;

import android.content.IntentFilter;

import org.citra.citra_leia.features.settings.model.Settings;
import org.citra.citra_leia.features.settings.model.SettingsListener;
import org.citra.citra_leia.utils.DirectoryStateReceiver;

/**
 * Abstraction for the Activity that manages SettingsFragments.
 */
public interface SettingsActivityView extends ToastableView {
    public SettingsListener mSettingsListener = null;

    /**
     * Show a new SettingsFragment.
     *
     * @param menuTag    Identifier for the settings group that should be displayed.
     * @param addToStack Whether or not this fragment should replace a previous one.
     */
    void showSettingsFragment(String menuTag, boolean addToStack, String gameId);

    /**
     * Called by a contained Fragment to get access to the Setting HashMap
     * loaded from disk, so that each Fragment doesn't need to perform its own
     * read operation.
     *
     * @return A possibly null HashMap of Settings.
     */
    Settings getSettings();

    /**
     * Used to provide the Activity with Settings HashMaps if a Fragment already
     * has one; for example, if a rotation occurs, the Fragment will not be killed,
     * but the Activity will, so the Activity needs to have its HashMaps resupplied.
     *
     * @param settings The ArrayList of all the Settings HashMaps.
     */
    void setSettings(Settings settings);

    /**
     * Called when an asynchronous load operation completes.
     *
     * @param settings The (possibly null) result of the ini load operation.
     */
    void onSettingsFileLoaded(Settings settings);

    /**
     * Called when settings are loaded/saved
     */
    void updatedSettingsAvailable(Settings settings);

    /**
     * Called when an asynchronous load operation fails.
     */
    void onSettingsFileNotFound();

    /**
     * Show the previous fragment.
     */
    void popBackStack();

    /**
     * End the activity.
     */
    void finish();

    /**
     * Called by a containing Fragment to tell the Activity that a setting was changed;
     * unless this has been called, the Activity will not save to disk.
     */
    void onSettingChanged();

    /**
     * Show loading dialog while loading the settings
     */
    void showLoading();

    /**
     * Hide the loading the dialog
     */
    void hideLoading();

    /**
     * Show a hint to the user that the app needs write to external storage access
     */
    void showPermissionNeededHint();

    /**
     * Show a hint to the user that the app needs the external storage to be mounted
     */
    void showExternalStorageNotMountedHint();

    /**
     * Start the DirectoryInitialization and listen for the result.
     *
     * @param receiver the broadcast receiver for the DirectoryInitialization
     * @param filter   the Intent broadcasts to be received.
     */
    void startDirectoryInitializationService(DirectoryStateReceiver receiver, IntentFilter filter);

    /**
     * Stop listening to the DirectoryInitialization.
     *
     * @param receiver The broadcast receiver to unregister.
     */
    void stopListeningToDirectoryInitializationService(DirectoryStateReceiver receiver);
}
