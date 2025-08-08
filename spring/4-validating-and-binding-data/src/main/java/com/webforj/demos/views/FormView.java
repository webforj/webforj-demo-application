package com.webforj.demos.views;

import java.util.ArrayList;

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
import com.webforj.data.binding.BindingContext;
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.demos.entity.Customer;
import com.webforj.demos.entity.Customer.Country;
import com.webforj.demos.service.CustomerService;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.observer.DidEnterObserver;

@StyleSheet("ws://css/views/form.css")
@Route("customer/:id?")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements DidEnterObserver {
  private final CustomerService customerService;
  private BindingContext<Customer> context;
  Customer customer = new Customer();
  Long customerId = Long.parseLong("0");
  Div self = getBoundComponent();
  TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
  TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
  TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
  ChoiceBox country = new ChoiceBox("Country",
      e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
  Button submit = new Button("Submit", ButtonTheme.PRIMARY, e -> submitCustomer());
  Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, e -> Router.getCurrent().navigate(DemoView.class));

  ColumnsLayout columnsLayout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      cancel, submit);

  public FormView(CustomerService customerService) {
    this.customerService = customerService;

    context = BindingContext.of(this, Customer.class, true);
    context.onValidate(e -> submit.setEnabled(e.isValid()));

    fillCountries();

    self.setMaxWidth("600px");
    self.setHeight("100dvh");
    self.addClassName("form");
    self.add(columnsLayout);
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
  }

  private void submitCustomer() {
    ValidationResult results = context.write(customer);
    if (results.isValid()) {
      if (customerId.intValue() == 0) {
        customerService.createCustomer(customer);
      }
      Router.getCurrent().navigate(DemoView.class);
    }
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresent(id -> {
      customer = customerService.getCustomerByKey(Long.parseLong(id));
      customerId = Long.parseLong(id);
    });
    context.read(customer);
  }
}
