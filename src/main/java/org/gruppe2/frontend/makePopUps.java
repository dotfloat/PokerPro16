package org.gruppe2.frontend;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class makePopUps {
	
	public void setStartValues(TestSimulator simulator){
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Edit Person");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		
		
		
		
		// Set the person into the controller
//		PersonEditDialogController controller = loader.getController();
//		controller.setDialogStage(dialogStage);
//		controller.setPerson(person);
		
		// Show the dialog and wait until the user closes it
		dialogStage.showAndWait();
		
		simulator.listOfPlayers.add(new Player("hans",500));
		
	}
	
	

}
