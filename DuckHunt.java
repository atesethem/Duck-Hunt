
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.Node;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.*;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * A Duck Hunt game implementation using JavaFX.
 */
public class DuckHunt extends Application {


    /**
     * Game resources
     */

    public String favicon = "assets/favicon/1.png";
    public String optionscreen = "assets/background/1.png";
    public static final String title = "HUBBM Duck Hunt";
    public double scale = 3;
    public int horizontal = 1;
    public int vertical = 1;
    public boolean win = false;
    public MediaPlayer intro = mediaPlayer("assets/effects/Intro.mp3");
    public MediaPlayer shot = mediaPlayer("assets/effects/Gunshot.mp3");
    public MediaPlayer fall = mediaPlayer("assets/effects/DuckFalls.mp3");
    public MediaPlayer gameover = mediaPlayer("assets/effects/GameOver.mp3");
    public MediaPlayer levelcomplete = mediaPlayer("assets/effects/LevelCompleted.mp3");
    public Stage openingwindow;
    public ImageView crosshairicon;
    public ImageView windowBackground;
    public Stage optionswindow;
    public Stage gamewindow;
    private static final String openwingversion = "assets/duck_black/4.png";
    private static final String closedwingversion = "assets/duck_black/6.png";
    private static final String midpositionwing = "assets/duck_black/5.png";
    private ImageView duckImageView;
    public int level = 1;

    /**
     * The starting point for the JavaFX application.
     */
    public void start(Stage opening) throws Exception {
        creatingopeningwindow();
        this.openingwindow.show();
    }
    /**
     * Creates the opening window of the game.
     */
    public void creatingopeningwindow() {
        this.openingwindow = new Stage();
        this.openingwindow.setTitle(title);
        this.openingwindow.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(favicon)).toExternalForm()));
        ImageView windowbackround = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(favicon)).toExternalForm()));
        windowbackround.setFitHeight(240 * scale);
        windowbackround.setFitWidth(270 * scale);
        StackPane Pane = new StackPane();
        Pane.getChildren().add(windowbackround);
        //openingwindow.show();
        Text thetext = new Text("PRESS ENTER TO PLAY\nPRESS ESC TO EXIT");
        thetext.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        thetext.setFill(Color.ORANGE);
        thetext.setTextAlignment(TextAlignment.CENTER);
        thetext.setTranslateY(40 * scale);
        StackPane.setAlignment(thetext, Pos.CENTER);
        VisibleText(thetext);
        Pane.getChildren().addAll(thetext);
        MediaPlayer themediplayer = mediaPlayer("assets/effects/Title.mp3");
        musicplayer(themediplayer, -1);
        Scene background = new Scene(Pane);
        openingwindow.setScene(background);
        openingwindow.setOnCloseRequest(event -> stopthemusic(themediplayer));
        background.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    stopthemusic(themediplayer);
                    openingwindow.close();
                    creatingoptionswindow();
                    break;
                case ESCAPE:
                    stopthemusic(themediplayer);
                    openingwindow.close();
                    break;
            }
        });
    }
    /**
     * Creates the options window of the game
     */
    public void creatingoptionswindow() {
        this.optionswindow = new Stage();
        this.optionswindow.setTitle("Options");
        this.optionswindow.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(optionscreen)).toExternalForm()));
        this.windowBackground = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(optionscreen)).toExternalForm()));
        this.crosshairicon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("assets/crosshair/1.png")).toExternalForm()));
        windowBackground.setFitHeight(240 * scale);
        windowBackground.setFitWidth(270 * scale);
        crosshairicon.setFitHeight(crosshairicon.getImage().getHeight() * scale);
        crosshairicon.setFitWidth(crosshairicon.getImage().getWidth() * scale);
        StackPane.setAlignment(crosshairicon, Pos.CENTER);
        StackPane pane = new StackPane();
        pane.getChildren().add(windowBackground);
        pane.getChildren().add(crosshairicon);

        Text instructionText1 = new Text("USE ARROW KEYS TO NAVIGATE");
        instructionText1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        instructionText1.setFill(Color.ORANGE);
        Text instructionText2 = new Text("PRESS ENTER TO START");
        instructionText2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        instructionText2.setFill(Color.ORANGE);
        Text instructionText3 = new Text("PRESS ESC TO EXIT");
        instructionText3.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        instructionText3.setFill(Color.ORANGE);

        VBox instructions = new VBox(10);
        instructions.getChildren().addAll(instructionText1, instructionText2, instructionText3);
        instructions.setAlignment(Pos.CENTER);
        instructions.setTranslateY(-100 * scale);
        pane.getChildren().addAll(instructions);

        MediaPlayer mediaPlayer = mediaPlayer("assets/effects/Title.mp3");
        musicplayer(mediaPlayer, -1);
        MediaPlayer.Status status = mediaPlayer.getStatus();
        Scene scene = new Scene(pane);
        this.optionswindow.setScene(scene);
        this.optionswindow.setOnCloseRequest(event -> stopthemusic(mediaPlayer));

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    stopthemusic(mediaPlayer);
                    musicplayer(intro,1);
                    intro.setOnEndOfMedia(() -> {
                        gamewindow();
                        optionswindow.close();
                    });
                    break;
                case ESCAPE:
                    stopthemusic(mediaPlayer);
                    openingwindow.show();
                    this.optionswindow.close();
                    break;
                case UP:
                    if(horizontal>=6){
                        horizontal = (1);
                    }
                    else{
                    horizontal++;}
                    break;
                case DOWN:
                    if(horizontal<=1){
                        horizontal=(6);
                    }
                    else{
                    horizontal--;}
                    break;
                case LEFT:
                    if(vertical<=1){
                        vertical=(6);}
                    else{
                    vertical--;}
                    break;
                case RIGHT:
                    if(vertical>=6){
                        vertical=(1);
                    }
                    else{
                    vertical++;}
                    break;
            }
            windowBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource("assets/background/" + String.valueOf(horizontal) + ".png")).toExternalForm()));
            crosshairicon.setImage(new Image(Objects.requireNonNull(getClass().getResource("assets/crosshair/" + String.valueOf(vertical) + ".png")).toExternalForm()));
        });

        this.optionswindow.show();
    }
    /**
     * Creates the game window
     */
    public void gamewindow() {
        ImageView background = this.windowBackground;
        AtomicInteger ammo = new AtomicInteger(3);
        this.gamewindow = new Stage();
        this.gamewindow.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(optionscreen)).toExternalForm()));
        background.setFitHeight(240 * scale);
        background.setFitWidth(270 * scale);
        crosshairicon.setFitHeight(10*scale);
        crosshairicon.setFitWidth(10*scale);
        duckImageView = new ImageView(new Image(openwingversion));
        duckImageView.setFitWidth(30*scale);
        duckImageView.setFitHeight(30*scale);
        ImageView midduckImageView = new ImageView(new Image(midpositionwing));
        midduckImageView.setFitWidth(30*scale);
        midduckImageView.setFitHeight(30*scale);
        ImageView endduckImageView = new ImageView(new Image(closedwingversion));
        endduckImageView.setFitWidth(30*scale);
        endduckImageView.setFitHeight(30*scale);
        Group root = new Group(background,crosshairicon);
        Text thetext = new Text("Ammo Left: " + ammo);
        thetext.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        thetext.setFill(Color.ORANGE);
        StackPane.setAlignment(thetext, Pos.TOP_RIGHT);
        Text secondtext = new Text("LEVEL " +  level);
        secondtext.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(secondtext, Pos.CENTER);
        StackPane.setMargin(secondtext, new Insets(0, 0, 230*scale, 0));
        secondtext.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        secondtext.setFill(Color.ORANGE);
        ImageView foreground = new ImageView(new Image("assets/foreground/" + horizontal + ".png"));
        foreground.setFitHeight(240*scale);
        foreground.setFitWidth(270*scale);
        StackPane stackPane = new StackPane(root,thetext,secondtext, duckImageView,foreground);
        Scene scene = new Scene(stackPane, 270 * scale, 240 * scale);
        crosshairicon.setMouseTransparent(true);
        scene.setCursor(Cursor.NONE);
        scene.setOnMouseMoved(event -> {
            double mousex = event.getX();
            double mousey = event.getY();
            double zerox = crosshairicon.getBoundsInParent().getWidth() / 2;
            double maxx = scene.getWidth() - crosshairicon.getBoundsInParent().getWidth() / 2;
            double zeroy = crosshairicon.getBoundsInParent().getHeight() / 2;
            double maxy = scene.getHeight() - crosshairicon.getBoundsInParent().getHeight() / 2;
            double pointx = Math.max(zerox, Math.min(mousex, maxx));
            double pointy = Math.max(zeroy, Math.min(mousey, maxy));
            crosshairicon.relocate(pointx - crosshairicon.getBoundsInParent().getWidth() / 2, pointy - crosshairicon.getBoundsInParent().getHeight() / 2);
        });
        gamewindow.setTitle("Duck Animation");
        gamewindow.setScene(scene);

        Timeline wingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    duckImageView.setImage(new Image(openwingversion));
                }),
                new KeyFrame(Duration.seconds(0.25), event -> {
                    duckImageView.setImage(midduckImageView.getImage());
                }),
                new KeyFrame(Duration.seconds(0.5), event -> {
                    duckImageView.setImage(new Image(closedwingversion));
                }),
                new KeyFrame(Duration.seconds(0.75), event -> {
                    duckImageView.setImage(midduckImageView.getImage());
                })
        );
       // wingTimeline.setAutoReverse(false);
        wingTimeline.setCycleCount(Timeline.INDEFINITE);
        wingTimeline.play();
        wingTimeline.setCycleCount(Timeline.INDEFINITE);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(4), duckImageView);
        translateTransition.setFromX(-130*scale);
        translateTransition.setFromY(-70*scale);
        translateTransition.setToX(130 * scale);
        translateTransition.setCycleCount(Timeline.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double konum = duckImageView.getTranslateX();
            if (konum <= -130 * scale || konum >= 130 * scale) {
                if (duckImageView.getScaleX() == 1) {
                    duckImageView.setScaleX(-1);
                } else {
                    duckImageView.setScaleX(1);
                }
            }
        });
        /**
         * Updates ammo amounts and checks for game over condition.
         *
         *
         */
        scene.setOnMouseClicked(event -> {
            musicplayer(shot, 1);
            if (ammo.get() == 1) {
                Node target = (Node) event.getTarget();
                if((ImageView) target != duckImageView) {
                ammo.getAndDecrement();
                musicplayer(gameover, 1);
                thetext.setText("Ammo Left: " + ammo);
                Text endtext = new Text("GAME OVER!");
                StackPane.setAlignment(endtext, Pos.CENTER);
                endtext.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
                endtext.setTranslateY(-70);
                endtext.setFill(Color.ORANGE);
                Text commandtext = new Text("Press ENTER to play again \n \t Press ESC to exit");
                commandtext.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
                commandtext.setFill(Color.ORANGE);
                StackPane.setAlignment(commandtext, Pos.CENTER);
                VisibleText(commandtext);
                stackPane.getChildren().addAll(endtext, commandtext);}
            } else if (ammo.get() > 0) {
                Node target = (Node) event.getTarget();
                if (target instanceof ImageView) {
                    musicplayer(shot, 1);
                    ammo.getAndDecrement();
                    thetext.setText("Ammo Left: " + ammo);
                }
            }
        });
        translateTransition.play();
        duckImageView.setOnMouseClicked(event -> {
            musicplayer(fall,1);
            win = true;
            musicplayer(levelcomplete,1);
            wingTimeline.stop();
            translateTransition.stop();
            Text winningtext = new Text("YOU WIN!");
            StackPane.setAlignment(winningtext, Pos.CENTER);
            winningtext.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
            winningtext.setTranslateY(-50);
            winningtext.setFill(Color.ORANGE);
            Text entertext = new Text("Press ENTER to play next level");
            entertext.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
            entertext.setFill(Color.ORANGE);
            StackPane.setAlignment(entertext, Pos.CENTER);
            VisibleText(entertext);
            stackPane.getChildren().addAll(winningtext,entertext);
            Timeline fallanimation = new Timeline(
                    new KeyFrame(Duration.seconds(0), event1 -> {
                    duckImageView.setImage(new Image("assets/duck_black/7.png"));
                }),
                    new KeyFrame(Duration.seconds(0.50), event1 -> {
                        duckImageView.setImage(new Image("assets/duck_black/8.png"));
                        TranslateTransition falltransition = new TranslateTransition(Duration.seconds(1), duckImageView);
                        falltransition.setToY(50 * scale);
                        falltransition.setOnFinished(fallFinishEvent -> {
                            duckImageView.setVisible(false);
                        });
                        falltransition.play();
                    })
            );
            fallanimation.play();
        });
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    /**
                     * Resets the game.
                     */
                    this.gamewindow.close();
                   gamewindow.getScene().setRoot(new Group());
                   gamewindow();
                    //stopthemusic(mediaPlayer);
                    break;
                case ESCAPE:
                    openingwindow.show();
                    this.gamewindow.close();
                    break;
            }

        });
        gamewindow.show();
    }

    public void stopthemusic(MediaPlayer mediaplayer){
                mediaplayer.stop();
            }
    /**
     * Plays a sound effect
     *
     */
            public MediaPlayer mediaPlayer(String musicpath){
                Media thesound = new Media(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource(musicpath)).toExternalForm()));
                return new MediaPlayer(thesound);
            }
            public void musicplayer(MediaPlayer mediaplayer, Integer numberofloop){
                mediaplayer.setCycleCount(numberofloop);
                mediaplayer.play();
            }
            public void VisibleText(Text text) {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(500), event -> {
                          text.setVisible(!text.isVisible());
                      })
             );
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            }

}
