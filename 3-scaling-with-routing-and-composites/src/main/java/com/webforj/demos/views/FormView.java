package com.webforj.demos.views;

import java.util.ArrayList;
import java.util.UUID;

import com.webforj.App;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.ListItem;
import com.webforj.demos.data.Service;
import com.webforj.demos.models.Customer;
import com.webforj.demos.models.Customer.Country;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.observer.DidEnterObserver;

/**
 * Represents the customer form view for adding or editing a customer.
 *
 * This view displays a form with fields for entering customer details such as
 * first name, last name, company, and country. It supports both adding a new
 * customer and editing an existing one, based on URL parameters.
 */
@StyleSheet("ws://css/views/form.css")
@Route("customer/:id?")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements DidEnterObserver {

  /** Customer instance representing the form data. */
  private Customer customer = new Customer();

  /** Root container for the form view. */
  private Div self = getBoundComponent();

  /** Text field for entering the first name. */
  private TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));

  /** Text field for entering the last name. */
  private TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));

  /** Text field for entering the company name. */
  private TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));

  /** Choice box for selecting the country. */
  private ChoiceBox country = new ChoiceBox("Country",
      e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));

  /** Button to submit the form (adds or edits a customer). */
  private Button submit = new Button("Submit", ButtonTheme.PRIMARY);

  /** Button to cancel the form and navigate back to the demo view. */
  private Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, 
      e -> Router.getCurrent().navigate(DemoView.class));

  /** Layout organizing the form fields and buttons. */
  private ColumnsLayout columnsLayout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      cancel, submit);

  /**
   * Constructs the customer form view.
   */
  public FormView() {
    fillCountries();

    self.setMaxWidth("600px");
    self.setHeight("100dvh");
    self.addClassName("form");
    self.add(columnsLayout);
  }

  /**
   * Populates the country selection box with available countries.
   */
  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
  }

  /**
   * Handles the submission of the form based on the mode (add or edit).
   *
   * @param mode The mode of submission: "add" for adding a new customer, 
   *             "edit" for updating an existing customer.
   */
  private void submit(String mode) {
    switch (mode) {
      case "edit":
        Service.getCurrent().editCustomer(customer);
        break;
      case "add":
        Service.getCurrent().addCustomer(customer);
        break;
      default:
        App.console().log("Invalid mode");
        break;
    }
    Router.getCurrent().navigate(DemoView.class);
  }

  /**
   * Handles route navigation and pre-fills the form if an existing customer is being edited.
   *
   * If a customer ID is present in the URL parameters, the corresponding customer data
   * is loaded into the form fields. Otherwise, the form is set up for adding a new customer.
   *
   * @param event      The event triggered when entering the route.
   * @param parameters The parameters extracted from the route.
   */
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresentOrElse(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      firstName.setValue(customer.getFirstName());
      lastName.setValue(customer.getLastName());
      company.setValue(customer.getCompany());
      country.selectKey(customer.getCountry());
      submit.addClickListener(e -> submit("edit"));
    }, () -> submit.addClickListener(e -> submit("add")));
  }
}
