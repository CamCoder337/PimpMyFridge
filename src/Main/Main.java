package Main;

import Contract.IController;
import Contract.IModel;
import Contract.IView;
import Controller.Controller;
import Model.Model;
import View.View;

public class Main {
    public static void main(String[] args) {
        IModel model = new Model();
        IView view = new View(new Controller());
        IController controller = new Controller(view, model);
        controller.start();
    }
}
