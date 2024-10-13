package scenes;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import mampos.Utils;

/**
 * FXML Controller class
 *
 * @author diego
 */
public class B_ModeChooserController implements Initializable {

    
    public void changeToGuidedDesign(ActionEvent e) throws Exception{
        Utils.changeScene(getClass(), e, "C2_A_Elevation_Requirements.fxml");
    };
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
}
