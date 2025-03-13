package com.webforj.demos;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.App;
import com.webforj.annotation.AppTitle;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.table.Table;
import com.webforj.component.window.Frame;
import com.webforj.demos.data.Service;
import com.webforj.demos.models.Customer;
import com.webforj.exceptions.WebforjException;

/**
 * A demo WebforJ application showcasing a UI with a table and a button.
 *
 * This application includes a table displaying customer data and a button
 * that, when clicked, displays an informational message.
 */
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Demo Step 2")
public class DemoApplication extends App {

  /** Paragraph displaying the demo message. */
  Paragraph demo = new Paragraph("Demo Application!");

  /** Button that triggers an info message dialog when clicked. */
  Button btn = new Button("Info");

  /** Table displaying customer data. */
  Table<Customer> table = new Table<>();

  /**
   * Runs the demo application.
   *
   * @throws WebforjException if an error occurs during application execution
   */
  @Override
  public void run() throws WebforjException {
    // Create the main application frame
    Frame mainFrame = new Frame();
    mainFrame.addClassName("mainFrame");

    // Build and configure the table
    buildTable();

    // Configure the button with a theme, width, and click event listener
    btn.setTheme(ButtonTheme.PRIMARY).setWidth(100)
        .addClickListener(e -> showMessageDialog("This is a demo with a table!", "Info"));

    // Add components to the main frame
    mainFrame.add(demo, btn, table);
  }

  /**
   * Configures the customer table by defining columns and setting data.
   *
   */
  private void buildTable() {
    // Set table dimensions
    table.setHeight("294px");
    table.setWidth(1000);

    // Define columns for customer attributes
    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    // Bind the table to the customer repository
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
