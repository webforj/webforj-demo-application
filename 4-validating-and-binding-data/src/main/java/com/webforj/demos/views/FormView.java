package com.webforj.demos.views;

import java.util.ArrayList;
import java.util.UUID;

import com.webforj.component.Composite;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.ListItem;
import com.webforj.data.binding.BindingContext;
import com.webforj.data.validation.server.ValidationResult;
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
 * This view provides a form with input fields for customer details such as
 * first name, last name, company, and country. It uses data binding and
 * validation mechanisms to ensure input integrity.
 */
@StyleSheet("ws://css/views/form.css")
@Route("customer/:id?")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements DidEnterObserver {

  /** Binding context for handling form data and validation. */
  private BindingContext<Customer> context;

  /** Customer instance representing the form data. */
  private Customer customer = new Customer();

  /** Customer ID used to determine if the form is in edit mode. */
  private String customerId = "";

  /** Root container for the form view. */
  private Div self = getBoundComponent();

  /** Text field for entering the first name. */
  private TextField firstName = new TextField("First Name");

  /** Text field for entering the last name. */
  private TextField lastName = new TextField("Last Name");

  /** Text field for entering the company name. */
  private TextField company = new TextField("Company");

  /** Choice box for selecting the country. */
  private ChoiceBox country = new ChoiceBox("Country");

  /** Submit button for saving the customer data. */
  private Button submit = new Button("Submit", ButtonTheme.PRIMARY, e -> submitCustomer());

  /** Cancel button for navigating back to the demo view. */
  private Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, 
      e -> Router.getCurrent().navigate(DemoView.class));

  /** Layout organizing the form fields and buttons. */
  private ColumnsLayout columnsLayout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      cancel, submit);

  /**
   * Constructs the customer form view and initializes the UI components.
   *
   * Sets up the binding context for managing customer data and validation.
   * Populates the country selection dropdown and configures the form layout.
   */
  public FormView() {
    context = BindingContext.of(this, Customer.class, true);
    context.onValidate(e -> submit.setEnabled(e.isValid()));

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
   * Handles the submission of the customer form.
   *
   * Validates the input using the binding context. If valid, adds the
   * customer to the repository and navigates back to the demo view.
   */
  private void submitCustomer() {
    ValidationResult results = context.write(customer);
    if (results.isValid()) {
      if (customerId.isEmpty()) {
        Service.getCurrent().addCustomer(customer);
      }
      Router.getCurrent().navigate(DemoView.class);
    }
  }

  /**
   * Handles route navigation and pre-fills the form if an existing customer is being edited.
   *
   * When navigating to the form with a customer ID, the corresponding customer
   * data is loaded and displayed in the form fields.
   *
   * @param event      The event triggered when entering the route.
   * @param parameters The parameters extracted from the route.
   */
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresent(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      customerId = id;
    });
    context.read(customer);
  }
}
