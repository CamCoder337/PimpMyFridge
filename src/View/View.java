package View;

import Contract.IController;
import Contract.IView;

public class View implements IView, Runnable {
    private final AppFrame appFrame;
    private IController controller;
    public View() {
        this.appFrame = new AppFrame();
    }

    @Override
    public void setController(IController controller) {

    }

    @Override
    public void run() {

    }
}
