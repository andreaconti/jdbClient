package jdbc.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

class ValuesListView extends ResizableStage {
	
	private Button delete;
	private Button add;
	private ListView<? extends Object> valuesView;

	public ValuesListView(ObservableList<? extends Object> values) {
		
		valuesView = new ListView<>(values);
		VBox.setVgrow(valuesView, Priority.ALWAYS);
		
		VBox rootNode = new VBox(10);
		rootNode.setPadding(new Insets(10,10,10,10));
		Scene scene = new Scene(rootNode, 300, 500);
		this.setScene(scene);
		
		delete = new Button("-");
		HBox.setHgrow(delete, Priority.ALWAYS);
		delete.setMaxWidth(Double.MAX_VALUE);
		add = new Button("+");
		HBox.setHgrow(add, Priority.ALWAYS);
		add.setMaxWidth(Double.MAX_VALUE);
		HBox bar = new HBox(10);
		bar.getChildren().addAll(delete, add);
		
		rootNode.getChildren().addAll(valuesView, bar);
		
		super.close.setOnAction( ev -> this.close() );
		super.setAllScreenButtonVisible(false);
		super.setMinimizeButtonVisible(false);
		
	}
	
	public void setOnActionAddButton(EventHandler<ActionEvent> handler) {
		this.add.setOnAction(handler);
	}
	
	public void setOnActionDeleteButton(EventHandler<ActionEvent> handler) {
		this.delete.setOnAction(handler);
	}
	
	public ListView<? extends Object> getListView() {
		return this.valuesView;
	}

}
