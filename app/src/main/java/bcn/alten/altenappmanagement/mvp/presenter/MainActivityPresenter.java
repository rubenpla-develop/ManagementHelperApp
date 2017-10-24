package bcn.alten.altenappmanagement.mvp.presenter;

import bcn.alten.altenappmanagement.mvp.view.IMainActivityView;

public class MainActivityPresenter implements IMainActivitypresenter {

    private final String TAG = MainActivityPresenter.class.getSimpleName();

    private IMainActivityView view;

    public MainActivityPresenter(IMainActivityView view) {
        this.view = view;
    }

    @Override
    public void showSnackBar(String message) {
        view.showMessage(message);
    }
}
