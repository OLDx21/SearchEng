import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample1.fxml"));
        primaryStage.setTitle("Поиск заказа");
        primaryStage.getIcons().add(new Image("prom2.ico"));
        primaryStage.setScene(new Scene(root, 900, 550));
        primaryStage.show();
        primaryStage.setResizable(false);
        String f;

    }



    public static void main(String[] args) {
       // launch(args);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Controller controller = new Controller();

            }
        });

    }
}