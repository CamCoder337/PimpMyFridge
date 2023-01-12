package Contract;

import View.AppPanel;
import View.DataPanel;

public interface IView {
    void setController(IController controller);

    AppPanel getAppPanel();
    DataPanel getDataPanel();
}
