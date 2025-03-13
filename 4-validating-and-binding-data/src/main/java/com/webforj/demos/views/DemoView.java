package com.webforj.demos.views;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.demos.data.Service;
import com.webforj.demos.models.Customer;
import com.webforj.component.table.Table;
import com.webforj.component.table.event.item.TableItemClickEvent;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;

/**
 * Represents the main demo view of the application.
 *
 * This view displays a customer table with sorting capabilities and provides
 * navigation to a customer form view for adding or editing customer details.
 */
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {

  /** Table displaying customer data. */
  private Table<Customer> table = new Table<>();

  /** Root component of the view. */
  private Div self = getBoundComponent();

  /**
   * Constructs the demo view.
   */
  public DemoView() {
    // Create and configure the "Add Customer" button
    Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
        e -> Router.getCurrent().navigate(FormView.class, ParametersBag.of("mode=add")));
    add.setStyle("margin", "var(--dwc-space-l)").setWidth(200);

    // Build and configure the table
    buildTable();

    // Create a vertical layout containing the table and button
    FlexLayout layout = FlexLayout.create(table, add)
        .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");

    // Add layout to the root component
    self.add(layout);

    // Add item click listener for editing customers
    table.addItemClickListener(this::editCustomer);
  }

  /**
   * Configures the customer table by defining columns and setting data.
   *
   * This method sets the table height, defines the columns for customer attributes,
   * enables sorting, and assigns a data repository for populating the table.
   */
  private void buildTable() {
    // Set table height
    table.setHeight("294px");

    // Define columns for customer attributes
    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    // Enable sorting on all columns
    table.getColumns().forEach(column -> column.setSortable(true));

    // Bind the table to the customer repository
    table.setRepository(Service.getCurrent().getCustomers());
  }

  /**
   * Navigates to the customer form view for editing a selected customer.
   *
   * When a customer row in the table is clicked, the application navigates to the
   * form view with the selected customer's ID as a parameter.
   *
   * @param e the table item click event containing the selected customer's key
   */
  private void editCustomer(TableItemClickEvent<Customer> e) {
    Router.getCurrent().navigate(FormView.class,
        ParametersBag.of("id=" + e.getItemKey()));
  }
}
