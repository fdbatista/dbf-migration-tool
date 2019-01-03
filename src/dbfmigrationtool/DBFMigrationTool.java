package dbfmigrationtool;

import controllers.frmMainController;
import classes.Static;
import java.io.InputStream;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class DBFMigrationTool extends Application {

    private Stage scenario;
    private double posX = 0;
    private double posY = 0;

    @Override
    public void init() throws Exception {
        super.init();
        Static.setAppArguments(getParameters().getNamed().entrySet());
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.setScenario(stage);
        showMainScene();
        getScenario().initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void showMainScene() {
        try {
            frmMainController obj = (frmMainController) changeScene("/views/frmMain.fxml");
            obj.setApp(this);
        } catch (Exception ex) {
            Static.Error(ex.getClass().getName(), ex.getMessage());
        }
    }

    private Initializable changeScene(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = DBFMigrationTool.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(DBFMigrationTool.class.getResource(fxml));
        AnchorPane rootNode;
        try {
            rootNode = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }

        rootNode.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                posX = event.getSceneX();
                posY = event.getSceneY();
            }
        });

        rootNode.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getScenario().setX(event.getScreenX() - posX);
                getScenario().setY(event.getScreenY() - posY);
            }
        });

        Scene scene = new Scene(rootNode);
        getScenario().setScene(scene);
        getScenario().sizeToScene();
        changeOpacity(rootNode, 0, 1, 1000);
        return (Initializable) loader.getController();
    }

    protected void changeOpacity(Node elemento, double valInicial, double valFinal, double duracion) {
        FadeTransition ft = new FadeTransition(Duration.millis(duracion), elemento);
        ft.setFromValue(valInicial);
        ft.setToValue(valFinal);
        ft.play();
    }

    /**
     * @return the scenario
     */
    public Stage getScenario() {
        return scenario;
    }

    /**
     * @param scenario the scenario to set
     */
    public void setScenario(Stage scenario) {
        this.scenario = scenario;
    }

}
