package org.citra.citra_emu.features.settings.ui;

public interface ToastableView {
    /**
     * Display a popup text message on screen.
     *
     * @param message The contents of the onscreen message.
     * @param is_long Whether this should be a long Toast or short one.
     */
    void showToastMessage(String message, boolean is_long);
}
