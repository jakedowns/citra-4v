package org.citra.citra_leia.ui.platform;


import org.citra.citra_leia.Citra4VApplication;
import org.citra.citra_leia.model.GameDatabase;
import org.citra.citra_leia.utils.Log;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class PlatformGamesPresenter {
    private final PlatformGamesView mView;

    public PlatformGamesPresenter(PlatformGamesView view) {
        mView = view;
    }

    public void onCreateView() {
        loadGames();
    }

    public void refresh() {
        Log.debug("[PlatformGamesPresenter] : Refreshing...");
        loadGames();
    }

    private void loadGames() {
        Log.debug("[PlatformGamesPresenter] : Loading games...");

        GameDatabase databaseHelper = Citra4VApplication.databaseHelper;

        databaseHelper.getGames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(games ->
                {
                    Log.debug("[PlatformGamesPresenter] : Load finished, swapping cursor...");

                    mView.showGames(games);
                });
    }
}
