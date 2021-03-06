package jdbc.view;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.sun.glass.ui.Screen;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jdbc.exporter.ExportingFormat;
import jdbc.exporter.ExportingOptions;
import jdbc.view.css.CSSStyleable;

class ClientDirectoryChooser implements CSSStyleable {
	
	protected TextField fileName;
	protected TextField directoryChoosen;
	protected Button browse;
	protected ComboBox<ExportingOptions> options;
	protected ComboBox<ExportingFormat> format;
	
	protected ButtonType close;
	protected ButtonType ok;
	protected Path result;
	
	protected VBox rootNode;
	
	private Dialog<Path> dialog;

	public ClientDirectoryChooser(Path oldDirectory, List<ExportingOptions> exportingOptions, List<ExportingFormat> exportingFormats ) {
		
		// checks
		if ( exportingFormats == null || exportingOptions == null || exportingFormats.isEmpty() || exportingOptions.isEmpty() ) 
			throw new IllegalArgumentException();
		
		rootNode = new VBox(10);
		
		dialog = new Dialog<>();
		
		// imposto directoryChoosen
		directoryChoosen = new TextField();
		directoryChoosen.setMinWidth(Screen.getMainScreen().getWidth() / 3);
		directoryChoosen.setEditable(false);
		directoryChoosen.setPromptText("Here There is the path choosen");
		if (oldDirectory != null)
			directoryChoosen.setText(oldDirectory.toString());
		HBox.setHgrow(directoryChoosen, Priority.ALWAYS);
		
		fileName = new TextField();
		fileName.setPromptText("Enter the file name");
		fileName.setMinWidth(Screen.getMainScreen().getWidth() / 3);
		fileName.setMaxWidth(Screen.getMainScreen().getWidth() / 3);
		HBox.setHgrow(fileName, Priority.ALWAYS);
		
		// imposto tasto browse
		browse = new Button("Browse");
		browse.setOnAction( ev -> {

			UserDialogImpl d = new UserDialogImpl();
			Optional<File> result = d.chooseDirectory(oldDirectory != null ? oldDirectory.toFile() : Paths.get(System.getProperty("user.home")).toFile());
			if ( result.isPresent()) {
				this.result = result.get().toPath();
				directoryChoosen.setText(result.get().toString());
			}
		});
		
		HBox browsingBar = new HBox(10);
		browsingBar.getChildren().addAll(directoryChoosen, browse);
		
		// imposto options e format
		options = new ComboBox<>(FXCollections.observableArrayList(exportingOptions));
		options.setMinWidth(Screen.getMainScreen().getWidth() / 3);
		options.getSelectionModel().select(0);
		
		format = new ComboBox<ExportingFormat>(FXCollections.observableArrayList(exportingFormats));
		format.setMinWidth(Screen.getMainScreen().getWidth() / 3);
		format.getSelectionModel().select(0);
		
		close = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ok = new ButtonType("Done", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(ok, close);
		rootNode.getChildren().addAll(browsingBar, fileName,  options, format);
		
		dialog.getDialogPane().setContent(rootNode);
		
		dialog.setResultConverter( button -> {
			if ( button == ok && result != null)
				return result.resolve(fileName.getText());
			else 
				return null;
		});
		
		Node enablerOk = dialog.getDialogPane().lookupButton(ok);
		enablerOk.setDisable(true);
		
		directoryChoosen.textProperty().addListener( (obs, oldV, newV) -> {
			enablerOk.setDisable(directoryChoosen.getText().trim().isEmpty() && fileName.getText().trim().isEmpty());
		});
		
	}
	
	public ClientDirectoryChooser( List<ExportingOptions> exportingOptions, List<ExportingFormat> exportingFormats) {
		this( Paths.get(""), exportingOptions, exportingFormats );
	}
	
	public ExportingOptions getExportingOptionSelected() {
		return options.getSelectionModel().getSelectedItem();
	}
	
	public ExportingFormat getExportingFormatSelected() {
		return format.getSelectionModel().getSelectedItem();
	}
	
	public Optional<Path> getDirectorySelected() {
		return Optional.ofNullable(result);
	}
	
	public String getFileName() {
		return this.fileName.getText();
	}
	
	public void showAndWait() {
		dialog.showAndWait();
	}

	@Override
	public void setCSSStyle(List<String> cssPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCSSStyle(String cssPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetCSSStyle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCSSStyle(String cssPath) {
		// TODO Auto-generated method stub
		
	}


}
