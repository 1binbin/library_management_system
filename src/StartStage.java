import Stages.MainStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author hongxiaobin
 * @create 2021/12/11-23:59
 * @function 启动界面的类
 */
public class StartStage extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainStage mainStage = new MainStage();
        mainStage.mainstage().show();
    }
}