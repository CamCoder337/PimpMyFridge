package View;

import Contract.IController;
import Contract.IView;

public class View implements IView, Runnable {
    private final AppFrame appFrame;
    private IController controller;
//    public View() {
//        this.appFrame = new AppFrame();
//    }
    public View(IController controller){
        this.controller = controller;
        this.appFrame = new AppFrame(this.controller);
    }

    @Override
    public void setController(IController controller) {
        this.controller = controller;
        this.appFrame.setController(controller);
    }

    @Override
    public AppPanel getAppPanel() {
        return this.appFrame.getAppPanel();
    }

    @Override
    public DataPanel getDataPanel() {
        return this.appFrame.getDataPanel();
    }

    @Override
    public void run() {

    }
}
