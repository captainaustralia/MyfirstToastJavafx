package sample;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;

class MyfirstToastFX {


    private static final int length_notification_window = 600;
    private static final String link_notification_sound = "https://wav-library.net/sounds/0-0-1-4573-20";
    private static final int title_font_size = 24;
    private static final int message_font_size = 20;
    private static final int offset_edge_notification = 10;

    public enum Position {
        RIGHT_BOTTOM
    }

    public enum Border {
        CIRCLE
    }

    public enum Durability {
        NEVER
    }

    public enum Animation {
        TRANSPARENT
    }

    public static class Builder {
        private static final ArrayList<String> arrayListComboBox = new ArrayList<>();
        private static final ArrayList<String[]> arrayListmessageLinkBrowser = new ArrayList<>();
        final ComboBox<String> pullBox = new ComboBox<>();
        private final Stage popup = new Stage();
        private final Stage primaryStage;
        private final VBox content = new VBox();
        private final HBox content_visual = new HBox();
        private final VBox msgLayout = new VBox();
        private final VBox content_input = new VBox();
        private final HBox content_actions = new HBox();

        private final double defWidth = length_notification_window;

        private final String musicPathURL = link_notification_sound;

        TextField textField = new TextField();
        private Position pos = Position.RIGHT_BOTTOM;
        private String title;
        private String message = "";
        private String attributiontext;
        private Border iconBorder = Border.CIRCLE;
        private String iconPathURL = "";
        private Animation changeTransition = Animation.TRANSPARENT;
        private String textColorTitle = "#FFFFFF";
        private String textColorMessage = "#b0b0b0";
        private String backgroundColor = "#24383c";
        private double backgroundOpacity = 1;
        private Durability waitTime = Durability.NEVER;
        private Boolean addInputTextBox = false;
        private String mPositiveButtonText;
        private String mNegativeButtonText;
        private EventHandler<ActionEvent> positiveButtonListener;
        private EventHandler<ActionEvent> negativeButtonListener;
        private String main_text;

        private Button mPositiveButton;
        private String newValueTextField;

        public Builder(Stage primaryStage) {
            this.primaryStage = primaryStage;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message += message;
            return this;
        }

        public Builder messageLinkBrowser(String messageLinkBrowserName, String messageLinkBrowser) {
            arrayListmessageLinkBrowser.add(new String[]{messageLinkBrowserName, messageLinkBrowser});
            return this;
        }


        public Builder backgroundOpacity(double backgroundOpacity) {
            this.backgroundOpacity = backgroundOpacity;
            return this;
        }

        public Builder position(Position pos) {
            this.pos = pos;
            return this;
        }

        public Builder iconBorder(Border border) {
            this.iconBorder = border;
            return this;
        }

        public Builder iconPathURL(String iconPathURL) {
            this.iconPathURL = iconPathURL;
            return this;
        }

        public String getValueTextField() {
            return newValueTextField;
        }

        public Builder waitTime(Durability waiting_time) {
            this.waitTime = waiting_time;
            return this;
        }

        public Builder changeTransition(Animation change_transition) {
            this.changeTransition = change_transition;
            return this;
        }

        public Builder addInputTextBox() {
            this.addInputTextBox = true;
            return this;
        }

        public Builder setComboBox(String maintext, String... var) {
            Collections.addAll(arrayListComboBox, var);
            main_text = maintext;
            return this;
        }

        public Builder setPositiveButton(String name, final EventHandler<ActionEvent> listener) {
            this.mPositiveButtonText = name;
            this.positiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(String name, final EventHandler<ActionEvent> listener) {
            this.mNegativeButtonText = name;
            this.negativeButtonListener = listener;
            return this;
        }

        public String getValueComboBox() {
            return pullBox.getValue();
        }



        public void build() {
            Rectangle2D screenRect = Screen.getPrimary().getBounds();

            create_element();

            Task<Object> task = new Task<Object>() {
                @Override
                protected Object call() throws Exception {
                    Media sound = new Media(musicPathURL);
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if (waitTime == Durability.NEVER) {
                        Thread.sleep(10000000);
                    }
                    return null;
                }
            };

            Thread th1 = new Thread(task);
            th1.start();

            StringProperty heigth = new SimpleStringProperty();
            textField.textProperty().bindBidirectional(heigth);
            content.addEventFilter(MouseEvent.MOUSE_ENTERED, MouseEvent -> popup.setOpacity(1));
            content.addEventFilter(MouseEvent.MOUSE_EXITED, MouseEvent -> popup.setOpacity(this.backgroundOpacity));

            Scene scene = new Scene(content);
            scene.setFill(Color.TRANSPARENT);

            popup.setScene(scene);
            popup.setWidth(defWidth);
            popup.setAlwaysOnTop(true);
            popup.initOwner(primaryStage);
            popup.initStyle(StageStyle.TRANSPARENT);
            popup.setOpacity(this.backgroundOpacity);
            popup.show();


            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice defaultScreenDevice = ge.getDefaultScreenDevice();
            GraphicsConfiguration defaultConfiguration = defaultScreenDevice.getDefaultConfiguration();
            java.awt.Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(defaultConfiguration);


            double shift = offset_edge_notification;

            switch (this.pos) {
                case RIGHT_BOTTOM:
                    popup.setX(screenRect.getWidth() - defWidth - shift - shift - screenInsets.right);
                    popup.setY(screenRect.getHeight() - screenInsets.bottom - shift - content.getHeight());
                    break;
            }

            openAnim(this.changeTransition);
        }

        private void create_element() {
            content.setStyle("-fx-background-color:" + this.backgroundColor);
            content.setPadding(new Insets(5));
            content_visual.setPadding(new Insets(5));
            content_visual.setSpacing(10.0);

            ImageAdd();
            LabelAdd();

            content_input.setSpacing(10);
            content_input.setPadding(new Insets(5));
            if (addInputTextBox)
                textFieldAdd();
            if (!arrayListComboBox.isEmpty())
                comboBox();
            content.getChildren().add(content_input);
            ButtonAdd();
        }

        private void ImageAdd() {
            String path = this.iconPathURL;

            if (!this.iconPathURL.isEmpty()) {
                if (!this.iconPathURL.startsWith("http")) {
                    try {
                        path = new File(this.iconPathURL).toURI().toURL().toString();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                Circle iconBorderCircle;
                Rectangle iconBorderRectangle;
                Image backgroundImage = new Image(path);

                if (this.iconBorder == Border.CIRCLE) {
                    iconBorderCircle = new Circle(50, 50, 25);
                    iconBorderCircle.setFill(new ImagePattern(backgroundImage, 1, 1, 1, 1, true));
                    content_visual.getChildren().add(iconBorderCircle);
                } else {
                    iconBorderRectangle = new Rectangle(0, 0, 50, 50);
                    iconBorderRectangle.setFill(new ImagePattern(backgroundImage, 0, 0, 1, 1, true));
                    content_visual.getChildren().add(iconBorderRectangle);
                }
            }
        }

        private void LabelAdd() {
            if (this.title != null) {
                Label title = new Label(this.title);
                title.setFont(Font.font(title_font_size));
                title.setStyle("-fx-font-weight: bold; -fx-text-fill:" + this.textColorTitle);
                msgLayout.getChildren().add(title);
            }
            if (this.message != null) {
                Label message = new Label(this.message);
                message.setMaxWidth(defWidth - 100);
                message.setWrapText(true);
                message.setFont(Font.font(message_font_size));
                message.setStyle("-fx-text-fill:" + this.textColorMessage);
                msgLayout.getChildren().add(message);
            }

            if (arrayListmessageLinkBrowser.size() != 0) {
                Desktop desktop;
                try {
                    desktop = Desktop.getDesktop();
                } catch (Exception ex) {
                    System.err.println("Error...");
                    return;
                }
                if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                    System.err.println("Error...");
                    return;
                }
            }


            content_visual.getChildren().add(msgLayout);
            content.getChildren().add(content_visual);
        }

        private void textFieldAdd() {
            content_input.getChildren().add(textField);
        }

        private void comboBox() {
            pullBox.getItems().addAll(arrayListComboBox);
            new AutoCompleteComboBoxListener<>(pullBox);
            pullBox.setValue(main_text);
            pullBox.setVisibleRowCount(5);
            pullBox.setValue(main_text);
            pullBox.setPrefWidth(defWidth);
            pullBox.setStyle("-fx-font-weight: bold; -fx-background-color: #ffffff; -fx-text-fill: #24383c");
            pullBox.setPadding(new Insets(0, 5, 0, 5));
            content_input.getChildren().addAll(pullBox);
        }

        private void ButtonAdd() {
            content_actions.setPrefWidth(defWidth);
            content_actions.setSpacing(10.0);
            content_actions.setPadding(new Insets(5));

            if (this.positiveButtonListener != null) {
                mPositiveButton = new Button(this.mPositiveButtonText);
                mPositiveButton.setPrefWidth(content_actions.getPrefWidth());
                mPositiveButton.setStyle("-fx-font-weight: bold; -fx-background-color: #76cd43; -fx-text-fill: #ffffff");
                mPositiveButton.setOnAction(this.positiveButtonListener);
                mPositiveButton.addEventFilter(MouseEvent.MOUSE_PRESSED, MouseEvent -> closeAnim(changeTransition));
                content_actions.getChildren().add(mPositiveButton);
            }

            if (this.negativeButtonListener != null) {
                Button mNegativeButton = new Button(this.mNegativeButtonText);
                mNegativeButton.setPrefWidth(content_actions.getPrefWidth());
                mNegativeButton.setStyle("-fx-font-weight: bold; -fx-background-color: #cd3838; -fx-text-fill: white");
                mNegativeButton.setOnAction(this.negativeButtonListener);
                mNegativeButton.addEventFilter(MouseEvent.MOUSE_PRESSED, MouseEvent -> closeAnim(changeTransition));
                content_actions.getChildren().add(mNegativeButton);
            }
            content.getChildren().add(content_actions);
        }

        private void openAnim(Animation transition) {
            if (transition == Animation.TRANSPARENT) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(600), content);
                if (this.pos == Position.RIGHT_BOTTOM ) {
                    tt.setByX(-defWidth);
                    tt.setFromX(defWidth);
                } else {
                    tt.setByX(defWidth);
                    tt.setFromX(-defWidth);
                }
                tt.play();
            } else {
                FadeTransition ft = new FadeTransition(Duration.millis(600), content);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
            }

        }

        private void closeAnim(Animation transition) {
            if (transition == Animation.TRANSPARENT) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(600), content);
                if (this.pos == Position.RIGHT_BOTTOM) {
                    tt.setByX(defWidth);
                } else {
                    tt.setByX(-defWidth);
                }
                tt.setOnFinished(event -> {
                    popup.close();
                });
                tt.play();
            } else {
                FadeTransition ft = new FadeTransition(Duration.millis(600), content);
                ft.setFromValue(this.backgroundOpacity);
                ft.setToValue(0);
                ft.setCycleCount(1);
                ft.setOnFinished(event -> {
                    popup.close();
                });
                ft.play();
            }
        }

        private static class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

            private final ComboBox comboBox;

            private final ObservableList<T> data;
            private boolean moveCaretToPos = false;
            private int caretPos;

            public AutoCompleteComboBoxListener(final ComboBox comboBox) {
                this.comboBox = comboBox;
                data = comboBox.getItems();

                this.comboBox.setEditable(true);
                this.comboBox.setOnKeyPressed(t -> comboBox.hide());
                this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
            }

            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.UP) {
                    caretPos = -1;
                    moveCaret(comboBox.getEditor().getText().length());
                    return;
                } else if (event.getCode() == KeyCode.DOWN) {
                    if (!comboBox.isShowing()) {
                        comboBox.show();
                    }
                    caretPos = -1;
                    moveCaret(comboBox.getEditor().getText().length());
                    return;
                } else if (event.getCode() == KeyCode.BACK_SPACE) {
                    moveCaretToPos = true;
                    caretPos = comboBox.getEditor().getCaretPosition();
                } else if (event.getCode() == KeyCode.DELETE) {
                    moveCaretToPos = true;
                    caretPos = comboBox.getEditor().getCaretPosition();
                }

                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                        || event.isControlDown() || event.getCode() == KeyCode.HOME
                        || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                    return;
                }

                ObservableList<T> list = FXCollections.observableArrayList();
                for (T datum : data) {
                    if (datum.toString().toLowerCase().startsWith(
                            AutoCompleteComboBoxListener.this.comboBox
                                    .getEditor().getText().toLowerCase())) {
                        list.add(datum);
                    }
                }
                String t = comboBox.getEditor().getText();

                comboBox.setItems(list);
                comboBox.getEditor().setText(t);
                if (!moveCaretToPos) {
                    caretPos = -1;
                }
                moveCaret(t.length());
                if (!list.isEmpty()) {
                    comboBox.show();
                }
            }

            private void moveCaret(int textLength) {
                if (caretPos == -1) {
                    comboBox.getEditor().positionCaret(textLength);
                } else {
                    comboBox.getEditor().positionCaret(caretPos);
                }
                moveCaretToPos = false;
            }

        }
    }
    public static class MyPlayer{
        private String path;
        private MediaPlayer media_player;

        public static MyPlayer MyPlayer;
        public Slider equalize;

        public static MyPlayer getMyPlayer() {
            return MyPlayer;
        }

        public static void setMyPlayer(MyPlayer MyPlayer) {
            MyPlayer.MyPlayer = MyPlayer;
        }

        public void initialize() {
            MyPlayer.setMyPlayer(this);
            MyPlayer.set_playlist();
        }

        private MediaPlayer mediaPlayer;
        private MediaPlayer _mediaPlayer;

        @FXML
        private MediaView mediaView;

        @FXML
        private Slider progressBar;

        @FXML
        private VBox vBox;

        @FXML
        private Slider volumeSlider;

        @FXML
        private Label song_name;

        @FXML
        private Label l_name;
        @FXML
        private Label l_artist;
        @FXML
        private Label l_listeners;
        @FXML
        private Label l_duration;

        @FXML
        private final Slider[] sliders = new Slider[10];



        String playlist_file = "C:\\Users\\Alexander\\Desktop\\playlist.txt";
        FileWriter playlist_file_writer;
        FileReader playlist_file_reader = new FileReader("C:\\Users\\Alexander\\Desktop\\playlist.txt");

        @FXML
        private TableColumn<String, String> name;

        @FXML
        private TableColumn<String, Integer> duration;

        static public ObservableList<File> selected_files;

        @FXML
        private ListView<File> list_of_songs;

        @FXML
        public final AudioEqualizer equalizer() {
            AudioEqualizer eq = media_player.getAudioEqualizer();

            ObservableList<EqualizerBand> band_list = eq.getBands();
            for (char n = 0; n < 10; n++) {
                EqualizerBand eq_band = band_list.get(n);
                eq_band.setGain(EqualizerBand.MAX_GAIN);
                band_list.set(n, eq_band);

            }
            return eq;
        }

        public MyPlayer() throws IOException {
        }

        public void http(String song_full_name, String artist, String album, String query) {
            Formatter f = new Formatter();
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(query).openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                StringBuilder sb = new StringBuilder();

                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    BufferedReader in = new BufferedReader((new InputStreamReader(connection.getInputStream())));
                    String line;
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    if (!sb.toString().split(",")[0].equals("{\"error\":6")) {
                        JsonObject jsonObject = new JsonParser().parse(sb.toString()).getAsJsonObject();
                        JsonElement duration_ = jsonObject.getAsJsonObject("album").getAsJsonObject("tracks").getAsJsonArray("track").get(0).getAsJsonObject().get("duration");
                        JsonElement listeners_ = jsonObject.getAsJsonObject("album").get("listeners");
                        JsonElement name_ = jsonObject.getAsJsonObject("album").get("name");
                        JsonElement artist_ = jsonObject.getAsJsonObject("album").get("artist");
                        Integer duration_min = duration_.getAsInt() / 60;
                        Integer duration_sec = duration_.getAsInt() - duration_min * 60;
                        String tererere = f.format("%d:%d", duration_min, duration_sec).toString();
                        l_name.setText(name_.getAsString());
                        l_artist.setText(artist_.getAsString());
                        l_listeners.setText(listeners_.getAsString());
                        l_duration.setText(tererere);

                    }


                } else {
                    System.out.println("Error " + connection.getResponseCode() + "," + connection.getResponseMessage());
                }
            } catch (Throwable cause) {
                cause.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        public void set_playlist() {
            try {
                ObservableList<File> list = FXCollections.observableArrayList();
                BufferedReader reader = new BufferedReader(playlist_file_reader);
                String line = reader.readLine();
                StringBuilder sb = new StringBuilder();
                sb.append(line);
                int line_ = sb.toString().split(",").length;
                for (int i = 0; i < line_; ++i) {
                    File a = new File(sb.toString().split(",")[i].replaceAll("\\[", "").replaceAll("]", ""));
                    list.add(a);
                }
                list_of_songs.setItems(list);
                path = list.get(0).toURI().toString();
                Media media = new Media(path);
                mediaPlayer = new MediaPlayer(media);
                song_name.setText(path.split("/")[path.split("/").length - 1].replaceAll("%20", " "));

                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        progressBar.setValue(newValue.toSeconds());

                    }
                });


                progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                    }
                });

                progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                    }
                });

                mediaPlayer.setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        Duration time = media.getDuration();
                        progressBar.setMax(time.toSeconds());
                    }
                });
                volumeSlider.setValue(mediaPlayer.getVolume() * 100);
                volumeSlider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                    }
                });
            } catch (Throwable cause) {
                cause.printStackTrace();
                System.out.println("tyt gg");
            }


        }


        public void choose_method(javafx.event.ActionEvent event) throws IOException {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            ObservableList<File> list = FXCollections.observableArrayList();
            FileChooser fileChooser = new FileChooser();
            list.addAll(fileChooser.showOpenMultipleDialog(null));
            list_of_songs.setItems(list);
            path = list.get(0).toURI().toString();
            playlist_file_writer = new FileWriter("C:\\Users\\Alexander\\Desktop\\playlist.txt", false);
            playlist_file_writer.write(list.toString());
            playlist_file_writer.close();


            if (list_of_songs != null) {
                Media media = new Media(path);
                mediaPlayer = new MediaPlayer(media);
                song_name.setText(path.split("/")[path.split("/").length - 1].replaceAll("%20", " "));


                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        progressBar.setValue(newValue.toSeconds());

                    }
                });

                progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                    }
                });

                progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                    }
                });

                mediaPlayer.setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        Duration time = media.getDuration();
                        progressBar.setMax(time.toSeconds());
                    }
                });
                volumeSlider.setValue(mediaPlayer.getVolume() * 100);
                volumeSlider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                    }
                });


            }
        }

        public void set_song() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            path = list_of_songs.getSelectionModel().getSelectedItem().toURI().toString().replaceAll("C:/Users/Alexander/IdeaProjects/PLAYERXXXXXX/%20", "");
            System.out.println(path);
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            song_name.setText(path.split("/")[path.split("/").length - 1].replaceAll("%20", " "));
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    progressBar.setValue(newValue.toSeconds());

                }
            });

            progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });

            progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });

            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    Duration time = media.getDuration();
                    progressBar.setMax(time.toSeconds());
                }
            });
            volumeSlider.setValue(mediaPlayer.getVolume() * 100);
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                }
            });

            String song_full_name = path.split("/")[path.split("/").length - 1].replaceAll("%20", " ");
            String artist = song_full_name.split(" -")[0].replaceAll(" ", "%20");
            String album = song_full_name.split("-")[1].split("\\.")[0].replaceFirst(" ", "").replaceAll(" ", "%20");
            String query = "https://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=3b5b2c649ba6affaea95dc5e7d91963b&artist=" + artist + "&album=" + album + "&format=json";
            http(song_full_name, artist, album, query);

        }


        public void play(javafx.event.ActionEvent event) {
            if (_mediaPlayer != null) {
                mediaPlayer.stop();
                _mediaPlayer = mediaPlayer;
            }
            mediaPlayer.play();


            progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });

            progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                    progressBar.setValue(mediaPlayer.getCurrentTime().toSeconds());
                }
            });
        }


        public void pause(javafx.event.ActionEvent event) {
            mediaPlayer.pause();
        }

        public void stop(javafx.event.ActionEvent event) {
            mediaPlayer.stop();
        }

        public void open(javafx.event.ActionEvent event) throws IOException {
            Runtime.getRuntime().exec("c:\\Program Files\\Dolby\\Dolby DAX2\\DAX2_APP\\DolbyDAX2DesktopUI.exe", null, new File("c:\\program files\\test\\"));
        }

        private int clicked_slow = 0;

        public void slow(javafx.event.ActionEvent event) {
            ++clicked_slow;
            if (clicked_slow % 2 == 0) {
                mediaPlayer.setRate(1);
            } else {
                mediaPlayer.setRate(0.5);
            }
        }

        private int clicked_fast = 0;

        public void fast(javafx.event.ActionEvent event) {
            ++clicked_fast;
            if (clicked_fast % 2 == 0) {
                mediaPlayer.setRate(1);
            } else {
                mediaPlayer.setRate(2);
            }
        }

    }
}
