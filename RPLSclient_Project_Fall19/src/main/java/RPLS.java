
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class RPLS extends Application {
	
	Text askPort, errorPort, askIP, errorIP, client1, client2, scoreClient1, scoreClient2, winnerText;
	TextField portNumber, ipInput;
	Button bt1, replay, quit;
	HBox portBox, ipBox, choiceBox, scoreBox, continueBox;
	VBox clientBox, resultBox;
	Scene clientStart, gameScene, gameResult;
	Client theClient;
	BorderPane pane;
	InetAddress ipNum;
	Image rock, paper, scissor, lizard, spock;
	ImageView rockView, paperView, scissorView, lizardView, spockView;
	StackPane scoreDisplay1, scoreDisplay2;
	Rectangle scoreRect1, scoreRect2;
	PauseTransition gameDone = new PauseTransition(Duration.seconds(1));
	
	
	ListView<String> gameInfo;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	public void disableButtons(boolean val) {
		rockView.setDisable(val);
		paperView.setDisable(val);
		scissorView.setDisable(val);
		lizardView.setDisable(val);
		spockView.setDisable(val);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("RPLS!!!");
		askPort = new Text("Input Port Number: ");
		errorPort = new Text();
		portNumber = new TextField();
		askIP = new Text("Input IP Address: ");
		errorIP = new Text();
		ipInput = new TextField();
		bt1 = new Button("Connect");
		winnerText = new Text();
		winnerText.setFont(Font.font(50));
		replay = new Button("replay");
		quit = new Button("quit");
		client1 = new Text("Player 1 :  ");
		client2 = new Text("  : Player 2");
		scoreClient1 = new Text("0");
		scoreClient1.setFont(Font.font(30));
		scoreClient2 = new Text("0");
		scoreClient2.setFont(Font.font(30));
		scoreRect1 = new Rectangle(75, 75);
		scoreRect1.setArcHeight(20);
		scoreRect1.setArcWidth(20);
		scoreRect1.setFill(Color.ORANGE);
		scoreRect2 = new Rectangle(75, 75);
		scoreRect2.setArcHeight(20);
		scoreRect2.setArcWidth(20);
		scoreRect2.setFill(Color.BLUE);
		
		rock = new Image("rock.jpg");
		paper = new Image("paper.jpg");
		scissor = new Image("scissors.jpg");
		lizard = new Image("lizard.jpg");
		spock = new Image("spock.jpg");
		
		rockView = new ImageView(rock);
		rockView.setFitHeight(75);
		rockView.setFitWidth(75);
		rockView.setPreserveRatio(true);
		//rockView.setDisable(true);
		
		paperView = new ImageView(paper);
		paperView.setFitHeight(75);
		paperView.setFitWidth(75);
		paperView.setPreserveRatio(true);
		//paperView.setDisable(true);
		
		scissorView = new ImageView(scissor);
		scissorView.setFitHeight(75);
		scissorView.setFitWidth(75);
		scissorView.setPreserveRatio(true);
		//scissorView.setDisable(true);
		
		lizardView = new ImageView(lizard);
		lizardView.setFitHeight(75);
		lizardView.setFitWidth(75);
		lizardView.setPreserveRatio(true);
		//lizardView.setDisable(true);
		
		spockView = new ImageView(spock);
		spockView.setFitHeight(75);
		spockView.setFitWidth(75);
		spockView.setPreserveRatio(true);
		//spockView.setDisable(true);
		
		gameInfo = new ListView<String>();
		
		bt1.setOnAction(e-> {
			try {
				int pNum = Integer.parseInt(portNumber.getText());
				if (pNum < 0 || pNum > 65535) {
					errorPort.setText("Error: Invalid range for port number");
					return;
				}
				try {
					ipNum = InetAddress.getByName(ipInput.getText());
				}catch (UnknownHostException ipError) {
					errorIP.setText("Error: Invalid IP Address");
				}
				primaryStage.setScene(gameScene);
				primaryStage.setTitle("Game Info");
				theClient = new Client(data -> {
					Platform.runLater(() -> {
//						gameInfo.getItems().add(data.toString());
						gameInfo.getItems().add(theClient.info.message);
						if(theClient.info.has2Players == false) {
							disableButtons(true);
						}
						else disableButtons(false);
						scoreClient1.setText(Integer.toString(theClient.info.p1Points));
						scoreClient2.setText(Integer.toString(theClient.info.p2Points));
						if(theClient.info.p1Points == 3 || theClient.info.p2Points == 3) {
							gameDone.play();
						}
					});
				}, ipNum, pNum);
				theClient.start();
				//check2Players();

			}catch (NumberFormatException error) {
				errorPort.setText("Error: input valid number");
			} 
		});
		
		rockView.setOnMouseClicked(e-> {
			//theClient.info.message = "rock";
			//theClient.send(theClient.info);
			theClient.send("rock");
		});
		
		paperView.setOnMouseClicked(e-> {
			//theClient.info.message = "paper";
			//theClient.send(theClient.info);
			theClient.send("paper");
		});
		
		scissorView.setOnMouseClicked(e-> {
			//theClient.info.message = "scissor";
			//theClient.send(theClient.info);
			theClient.send("scissor");
		});
		
		lizardView.setOnMouseClicked(e-> {
			//theClient.info.message = "lizard";
			//theClient.send(theClient.info);
			theClient.send("lizard");
		});
		
		spockView.setOnMouseClicked(e-> {
			//theClient.info.message = "spock";
			//theClient.send(theClient.info);
			theClient.send("spock");
		});
		
		replay.setOnAction(e-> {
			theClient.info.message = "true";
			//theClient.send(theClient.info);
		});
		
		quit.setOnAction(e-> {
			Platform.exit();
			System.exit(0);
		});
		
		gameDone.setOnFinished(e-> {
			if(theClient.info.p1Points == 3) winnerText.setText("Player 1 Won!");
			else winnerText.setText("Player 2 Won!");
			primaryStage.setScene(gameResult);
			primaryStage.setTitle("Quit or Replay");
		});
		
//		Platform.runLater(() -> {
//			if (theClient.info.has2Players == true) {
//				rockView.setDisable(false);
//				paperView.setDisable(false);
//				scissorView.setDisable(false);
//				lizardView.setDisable(false);
//				spockView.setDisable(false);
//			}
//			scoreClient1.setText(Integer.toString(theClient.info.p1Points));
//			scoreClient2.setText(Integer.toString(theClient.info.p2Points));
//		});
		
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
		ipBox = new HBox(askIP, ipInput, errorIP);
		ipBox.setAlignment(Pos.CENTER);
		clientBox = new VBox(100, portBox, ipBox, bt1);
		clientBox.setAlignment(Pos.CENTER);
		clientBox.setStyle("-fx-background-color: lightblue");
		clientStart = new Scene(clientBox, 500, 500);
		//-------------------------------------------------------//
		
		//--------------Game info scene--------------------------//
	    scoreDisplay1 = new StackPane(scoreRect1, scoreClient1);
	    scoreDisplay2 = new StackPane(scoreRect2, scoreClient2);
	    scoreBox = new HBox(client1, scoreDisplay1, scoreDisplay2, client2);
	    scoreBox.setAlignment(Pos.TOP_CENTER);
		choiceBox = new HBox(10, rockView, paperView, scissorView, lizardView, spockView);
	    choiceBox.setAlignment(Pos.BOTTOM_CENTER);
		pane = new BorderPane();
		pane.setStyle("-fx-background-color: darkgrey");
		pane.setPadding(new Insets(70));
		pane.setTop(scoreBox);
		pane.setCenter(gameInfo);
		pane.setBottom(choiceBox);
		gameScene = new Scene(pane, 500, 500);
		//-------------------------------------------------------//
		
		//--------------Play again scene-------------------------//
		continueBox = new HBox(100, replay, quit);
		continueBox.setAlignment(Pos.CENTER);
		resultBox = new VBox(100, winnerText, continueBox);
		resultBox.setAlignment(Pos.CENTER);
		resultBox.setStyle("-fx-background-color: red");
		gameResult = new Scene(resultBox, 500, 500);
		//-------------------------------------------------------//
		
		primaryStage.setScene(clientStart);
		primaryStage.show();
	}
	
//	void check2Players() {
//		while(true) {
//			if (theClient.info.has2Players == true) {
//				rockView.setDisable(false);
//				paperView.setDisable(false);
//				scissorView.setDisable(false);
//				lizardView.setDisable(false);
//				spockView.setDisable(false);
//				break;
//			}
//			
//		}
//	}

}
