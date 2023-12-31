package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * class containing functions related to table creation
 * and interactivity
 */
public class TableManager {

    /**
     * creates a new table object
     *
     * @param tableView  the table object that is being built
     * @param columnList a list of the table's columns
     */
    public static <T> void createTable(TableView<T> tableView, ObservableList<String> columnList) {
        ObservableList<TableColumn<T, ?>> columns = FXCollections.observableArrayList();

        // create table columns dynamically
        for (String columnName : columnList) {
            String[] propertyNameArr = columnName.split("[\\W_]+");
            String propertyName = propertyNameArr[0].toLowerCase();

            //for fields names that are longer than one word
            if (propertyNameArr.length > 1) {
                for (int i = 1; i < propertyNameArr.length; i++) {
                    propertyName = propertyName.concat(propertyNameArr[i]);
                }
            }

            TableColumn<T, ?> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
            tableView.getColumns().add(column);
            columns.add(column);
        }
    }

    /**
     * imports data into the given table
     *
     * @param tableView the table object that is receiving the data
     * @param data      the list of table entries that are being imported
     */
    public static <T> void importData(TableView<T> tableView, ObservableList<T> data) {
        // add data to the table dynamically
        tableView.getItems().addAll(data);
    }

    /**
     * Creates a filtered list of the table's entries based on user input
     *
     * @param filteredData    the original list of entries
     * @param searchField     the text field where the input is read
     * @param getTextFunction the function that reads the entity's text value that represents the searchable text
     */
    public static <T> void MakeFilterListForSearch(FilteredList<T> filteredData, TextField searchField,
                                                   Function<T, String> getTextFunction) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(item -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();
            String itemText = getTextFunction.apply(item);
            return itemText.toLowerCase().contains(lowerCaseFilter);
        }));
    }
    /**
     * Adds double-click functionality to a TableView.
     * When a row in the TableView is double-clicked and not empty,
     * the provided setFunctionsFunction is invoked with the relativePath parameter.
     *
     * @param <T> the type of the TableView items
     * @param tableView the TableView to add double-click functionality to
     * @param relativePath the relative path
     * @param setFunctionsFunction the function to invoke with the relativePath parameter
     */
    public static <T> void addDoubleClickFunctionality(TableView<T> tableView, String relativePath, Consumer<String> setFunctionsFunction) {
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty()) { //check whether the event was double click and the row contains a question
                setFunctionsFunction.accept(relativePath);
            }
        });
    }

    /**
     * resizes every column in the table based on given percentage values
     *
     * @param tableView   the table object that is receiving the column size values
     * @param multipliers the column size values
     */
    public static <T> void resizeColumns(TableView<T> tableView, double[] multipliers) {
        ObservableList<TableColumn<T, ?>> columns = tableView.getColumns();
        int index = 0;

        for (TableColumn<T, ?> column : columns) {
            column.prefWidthProperty().bind(tableView.widthProperty().multiply(multipliers[index]));
            index++;
        }
    }
}