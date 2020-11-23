package spherejavafx;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;

public class Space extends Application {

    double centerX;
    double centerY;
    final Duration month = Duration.seconds(2);
    final Duration year = month.multiply(13);

    private Image getImageFromResource(String resourcePath) {
        return new Image(this.getClass().getResourceAsStream(resourcePath));
    }

    @Override
    public void start(Stage primaryStage) {
        playSound();

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        centerX = (primaryStage.getWidth() / 2);
        centerY = (primaryStage.getHeight() / 2);


        ArrayList<Sphere> spheres = new ArrayList<>();
        for (int i = 0; i < CelestialBody.values().length; i++) {
            spheres.add(createSphere(CelestialBody.values()[i]));
        }

        Circle moon = new Circle(centerX + 300, centerY, 7.5, Color.IVORY);
        Group earthAndMoon = new Group(spheres.get(1), moon);
        addPivot(CelestialBody.EARTH, earthAndMoon);
        addPivot (CelestialBody.MOON, moon);


        Pane pane = new Pane(spheres.get(0), earthAndMoon);
        pane.setMinSize(bounds.getWidth(), bounds.getHeight());


        ImagePattern background = new ImagePattern(getImageFromResource("/sky.png"));
        primaryStage.setScene( new Scene(pane, background));

        primaryStage.show();
        primaryStage.setFullScreen(true);
    }

    public void rotatePlanet (final Node planet){
        rotatePlanet (planet, 15, Rotate.Y_AXIS, 360, Animation.INDEFINITE, Interpolator.LINEAR);
    }

    public  void rotatePlanet (final Node planet, final double duration, final Point3D axis,
                               final int angle, final int cycleCount, final Interpolator interpolator) {
        final RotateTransition rotation = new RotateTransition();

        rotation.setNode(planet);
        rotation.setDuration(Duration.seconds(duration));
        rotation.setAxis(axis);
        rotation.setByAngle(angle);
        rotation.setCycleCount(cycleCount);
        rotation.setInterpolator(interpolator);
        rotation.play();
    }

    public Sphere createSphere (CelestialBody celestialBody) {
        Sphere sphere = new Sphere(celestialBody.getRadius());
        sphere.setLayoutX((centerX + celestialBody.getDistanceFromXCenter()));
        sphere.setLayoutY((centerY + celestialBody.getDistanceFromYCenter()));
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(getImageFromResource("/"+celestialBody.getPath()+".jpg"));
        sphere.setMaterial(material);

        rotatePlanet(sphere);
        System.out.println("Complete "+celestialBody.getPath());
        return sphere;
    }

    public void addPivot (CelestialBody celestialBody, Group group) {
        if (celestialBody.isPivot()){
            Rotate rotate = new Rotate(celestialBody.getAngle(), centerX + celestialBody.getPivotX(), centerY + celestialBody.getPivotY());
            group.getTransforms().add(rotate);

            Timeline timeline = new Timeline(new KeyFrame(year, new KeyValue(rotate.angleProperty(), 360)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    public void addPivot (CelestialBody celestialBody, Circle circle) {
        if (celestialBody.isPivot()){
            Rotate rotate = new Rotate(celestialBody.getAngle(), centerX + celestialBody.getPivotX(), centerY + celestialBody.getPivotY() );
            circle.getTransforms().add(rotate);

            Timeline timeline = new Timeline(new KeyFrame(month, new KeyValue(rotate.angleProperty(), 360)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    public static void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Theme song.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(-1);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
