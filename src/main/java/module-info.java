module com.mycompany.buscaminasmdpf {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.buscaminasmdpf to javafx.fxml;
    exports com.mycompany.buscaminasmdpf;
}
