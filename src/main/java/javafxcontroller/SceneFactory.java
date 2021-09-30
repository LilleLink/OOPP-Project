package javafxcontroller;

import javafx.scene.Scene;

public class SceneFactory {

    public static Scene CreateMainScene() {
        return new Scene(new MainScene());
    }

    public static Scene CreateSecondaryScene() {
        return new Scene(new SecondaryScene());
    }

}
