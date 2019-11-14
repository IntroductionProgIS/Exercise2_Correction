package application;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class Controller 
{
	@FXML
    private Button btnCancel;

    @FXML
    private Button btnOpen;

    @FXML
   private  ListView listViewFile;

    @FXML
   private ComboBox<String> comboxRep;
    
    private FileSelector fileSelector = new FileSelector();
    
    @FXML
    public void initialize()
    {
    	//Obtain the path to the "home" directory of the user
    	String path = System.getProperty("user.home");

    	//Update the ComboBox
        majComboBox(path);
        
    	//Update the ListView
    	majListView(path);
    	
    	//Create an EventListener for the directory selection in the ComboBox
    	comboxRep.setOnAction(event ->  
    	{
    		majListView(comboxRep.getSelectionModel().getSelectedItem());
        });
    	
    	//Create an EventListener for the item selection in the ListView
    	listViewFile.setOnMouseClicked(event ->  
    	{
    		//Activate the button "Open"
    		btnOpen.setDisable(false);
    		
    		//If the user double-clicked
    		if(event.getClickCount() == 2)
    		{
    			//Simulate an event on the "Open" button
                btnOpen.fire();
            }
        });
    	
    	//Create an EventListener for the click on the "Cancel" button
    	btnCancel.setOnAction(event ->  
    	{
    		//Quit the application
    		System.exit(0);
        });
    	
    	//Create an EventFistener for the "click on the "Open" button
    	btnOpen.setOnAction(event ->  
    	{
    		//Get the element the user wants to open
    		File element = new File(comboxRep.getValue() + listViewFile.getSelectionModel().getSelectedItem());
    		
    		//If the element is a file
    		if(element.isFile())
    		{
    			//Do something
    		}
    		//If the element is a directory
    		else if(element.isDirectory())
    		{
    			//Update the ComboBox with the element's path
    			majComboBox(element.getAbsolutePath());
    		}
    		//If the element is a neither a file nor a directory
    		else
    		{
    			//Do something
    		}
        });
    }
    
    //Update the ComboBox
    public void majComboBox(String path)
    {
    	//Get the directories to display in the ComboBox
		ObservableList<String> itemsComboRep =  FXCollections.observableArrayList(fileSelector.getListRepParent(path));
		
		//Change the items of the ComboBox
		comboxRep.setItems(itemsComboRep);
		
		//Select an item for the initialization
		comboxRep.getSelectionModel().selectLast();
    }
    
    //Update the ListView
    public void majListView(String path)
    {
    	if (comboxRep.getSelectionModel().getSelectedItem() != null )
    	{
	    	//Get the elements to display in the ListView
			ObservableList<String> itemsListView = FXCollections.observableArrayList(fileSelector.getListFile(path));
			
			//Change the items of the ListView
			listViewFile.setItems(itemsListView);
			
			//Disable the "Open" button
	    	btnOpen.setDisable(true);
    	}	
    }

}
