package Contract;

import Controller.AppState;

public interface IController {
    void control();
    void start();
    AppState getAppState();
    void setAppState(AppState appState);
}
