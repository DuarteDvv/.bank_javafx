module graphics_comps {
    requires javafx.controls;
    requires javafx.fxml;

    opens graphics_comps to javafx.fxml;
    exports graphics_comps;
}
