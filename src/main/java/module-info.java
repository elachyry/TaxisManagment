module com.gestion_des_taxis.gestion_des_taxis {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires AnimateFX;
    requires MaterialFX;
    requires poi;
    requires poi.ooxml;
    requires poi.ooxml.schemas;
    requires javax.mail.api;
    requires fonts.fontawesome;
    requires fontawesomefx;
    requires com.jfoenix;

    opens com.gestion_des_taxis.gestion_des_taxis to javafx.fxml;
    exports com.gestion_des_taxis.gestion_des_taxis;
    exports com.gestion_des_taxis.gestion_des_taxis.Controllers;
    opens com.gestion_des_taxis.gestion_des_taxis.Controllers to javafx.fxml;
    opens com.gestion_des_taxis.gestion_des_taxis.Models to javafx.base;

}