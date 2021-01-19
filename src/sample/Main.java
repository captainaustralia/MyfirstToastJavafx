package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    private Object Window;
    public String a;
    public String b;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MyfirstToastFX.Builder notifyJava = new MyfirstToastFX.Builder(primaryStage);

        notifyJava.title("My javafx Toast") // title
                .message("If u don't choose, and write text in field , pc give u this text in new window") // title
                .messageLinkBrowser("My github link:", "https://github.com/physrow") // link
                .addAttributionText("TASK 2")
                .position(MyfirstToastFX.Position.RIGHT_BOTTOM)
                .waitTime(MyfirstToastFX.Durability.NEVER)
                .backgroundOpacity(1)
                .addInputTextBox()
                .setComboBox("ChooseProgram", "MyMediaPlayer", "DjVuReader")
                .setPositiveButton("Ok", event -> {
                    a = notifyJava.getValueComboBox();
                    b = notifyJava.getValueTextField();
                    System.out.println(a);
                    System.out.println(b);
                    if (a.equals("ChooseProgram")){
                        primaryStage.setTitle("Hello");
                        primaryStage.setWidth(300);
                        primaryStage.setHeight(100);
                        Label HelloLabel = new Label("asd");
                        HelloLabel.setAlignment(Pos.CENTER);
                        Scene primaryScene = new Scene(HelloLabel);
                        primaryStage.setScene(primaryScene);
                        primaryStage.show();

                    }
                    if (a.equals("MyMediaPlayer")){
                        Runtime rt = Runtime.getRuntime();
                        try {
                            Process p2 = rt.exec("C:\\PLAYERXXXXXX.exe");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (a.equals("DjVuReader")){
                        Runtime rt = Runtime.getRuntime();
                        try{
                            Process p2 = rt.exec("C:\\DjVuReader.exe");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                })
                .setNegativeButton("Cancel", event -> System.out.println("close"))
                .iconPathURL("https://icon-library.com/images/java-icon-images/java-icon-images-11.jpg")
                .changeTransition(MyfirstToastFX.Animation.TRANSPARENT)
                .build();
    }


}