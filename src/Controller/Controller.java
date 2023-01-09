package Controller;

import Contract.IController;
import Contract.IModel;
import Contract.IView;

public class Controller implements IController {

    private IView view;
    private IModel model;
    private AppState appState;
    public void setView(IView view) {
        this.view = view;
    }

    public void setModel(IModel model) {
        this.model = model;
    }


    public Controller(final IView view, final IModel model){
        this.setView(view);
        this.setModel(model);
        this.appState = AppState.main;
    }
    @Override
    public void control() {

    }

    @Override
    public void start() {

    }

    @Override
    public AppState getAppState() {
        return this.appState;
    }

    @Override
    public void setAppState(AppState appState) {
        this.appState = appState;
    }
}
