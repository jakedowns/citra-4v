package org.citra.citra_leia.features.settings.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import org.citra.citra_leia.R;
import org.citra.citra_leia.features.settings.model.view.SettingsItem;
import org.citra.citra_leia.features.settings.ui.SettingsAdapter;
import org.citra.citra_leia.features.settings.ui.SettingsFragmentView;
import org.citra.citra_leia.ui.main.MainActivity;

public final class PremiumViewHolder extends SettingViewHolder {
    private TextView mHeaderName;
    private TextView mTextDescription;
    private SettingsFragmentView mView;

    public PremiumViewHolder(View itemView, SettingsAdapter adapter, SettingsFragmentView view) {
        super(itemView, adapter);
        mView = view;
        itemView.setOnClickListener(this);
    }

    @Override
    protected void findViews(View root) {
        mHeaderName = root.findViewById(R.id.text_setting_name);
        mTextDescription = root.findViewById(R.id.text_setting_description);
    }

    @Override
    public void bind(SettingsItem item) {
        updateText();
    }

    @Override
    public void onClick(View clicked) {
        if (MainActivity.isPremiumActive()) {
            return;
        }

        // Invoke billing flow if Premium is not already active, then refresh the UI to indicate
        // the purchase has completed.
        MainActivity.invokePremiumBilling(() -> updateText());
    }

    /**
     * Update the text shown to the user, based on whether Premium is active
     */
    private void updateText() {
        mHeaderName.setText(R.string.premium_settings_bonus);
//        mTextDescription.setText(R.string.premium_settings_welcome_description);
//        if (MainActivity.isPremiumActive()) {
//            mHeaderName.setText(R.string.premium_settings_welcome);
//            mTextDescription.setText(R.string.premium_settings_welcome_description);
//        } else {
//            mHeaderName.setText(R.string.premium_settings_upsell);
//            mTextDescription.setText(R.string.premium_settings_upsell_description);
//        }
    }
}
