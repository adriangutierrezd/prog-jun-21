module prog.jun.prog_jun_2021 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens prog.jun.prog_jun_2021 to javafx.fxml;
    exports prog.jun.prog_jun_2021;
}