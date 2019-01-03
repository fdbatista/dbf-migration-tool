/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.AppBrain;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;
import dbfmigrationtool.DBFMigrationTool;
import javafx.fxml.Initializable;

/**
 *
 * @author FD
 */
public class GenericController implements Initializable {

    AppBrain appBrain;

    @FXML
    Label lblError;

    protected DBFMigrationTool appPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*InputStream font = DBFMigrationTool.class.getResourceAsStream("/libreria/recursos/fonts/fontawesome-webfont.ttf");
        Font fontAwesome = Font.loadFont(font, 10);
        String ICON_ANDROID = "\uf17b";
        this.lblError = new Label(ICON_ANDROID);
        lblError.setFont(fontAwesome);
        lblError.setStyle("-fx-font-family: 'FontAwesome'");*/
    }

    public void setApp(DBFMigrationTool appPrincipal) {
        this.appPrincipal = appPrincipal;
    }

    protected void changeOpacity(Node elemento, double valInicial, double valFinal, double duracion) {
        FadeTransition ft = new FadeTransition(Duration.millis(duracion), elemento);
        ft.setFromValue(valInicial);
        ft.setToValue(valFinal);
        ft.play();
    }

    protected void showMessage(String mensaje, String colorInicial, String colorFinal) {
        if (appBrain.isAutoConvert()) {
            System.out.println(mensaje);
        } else {
            lblError.setText(mensaje);
            Stop[] stops = {new Stop(0, Color.web(colorInicial)), new Stop(1, Color.web(colorFinal))};
            RadialGradient rg = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.REFLECT, stops);
            lblError.setTextFill(rg);
            changeOpacity(lblError, 0.0, 1, 500);
        }
    }

    protected void hideError() {
        changeOpacity(lblError, lblError.getOpacity(), 0.0, 500);
    }
}
