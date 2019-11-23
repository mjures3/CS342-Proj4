
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RPLS extends Application {
	
	Text askPort, errorPort;
	TextField portNumber;
	Button bt1;
	HBox portBox;
	VBox serverBox;
	Scene serverStart, gameScene;
	Server theServer;
	BorderPane pane;
	
	ListView<String> gameInfo;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("RPLS!!!");
		askPort = new Text("Input Port Number: ");
		errorPort = new Text();
		portNumber = new TextField();
		bt1 = new Button("Connect");
		
		gameInfo = new ListView<String>();
		
		bt1.setOnAction(e-> {
			try {
				int pNum = Integer.parseInt(portNumber.getText());
				if (pNum < 0 || pNum > 65535) {
					errorPort.setText("Error: Invalid range for port number");
					return;
				}
				primaryStage.setScene(gameScene);
				primaryStage.setTitle("Game Info");
				theServer = new Server(data -> {
					Platform.runLater(() -> {
						gameInfo.getItems().add(data.toString());
					});
				}, pNum);

			}catch (NumberFormatException error) {
				errorPort.setText("Error: input valid number");
			} 
		});
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		//--------------First connect scene----------------------//
		portBox = new HBox(askPort, portNumber, errorPort);
		portBox.setAlignment(Pos.CENTER);
		serverBox = new VBox(200, portBox, bt1);
		serverBox.setAlignment(Pos.CENTER);
		serverBox.setStyle("-fx-background-color: lightgreen");
		serverStart = new Scene(serverBox, 500, 500);
		//-------------------------------------------------------//
		
		//--------------Game info scene--------------------------//
	    pane = new BorderPane();
		pane.setStyle("-fx-background-color: coral");
		pane.setPadding(new Insets(70));
		pane.setCenter(gameInfo);
		gameScene = new Scene(pane, 500, 500);
		//-------------------------------------------------------//
		
		primaryStage.setScene(serverStart);
		primaryStage.show();
	}

}
