package org.citra.citra_emu.features.settings.model;

public class SettingsViewModelListenerID {
    private int value;
    SettingsViewModelListenerID(){
    }
    SettingsViewModelListenerID(int value){
        this.value = value;
    }
    public int getID(){
        return this.value;
    }
}
