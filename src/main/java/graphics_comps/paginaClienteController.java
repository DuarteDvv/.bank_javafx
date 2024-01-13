package graphics_comps;

import java.io.IOException;
import javafx.fxml.FXML;

public class paginaClienteController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }
}