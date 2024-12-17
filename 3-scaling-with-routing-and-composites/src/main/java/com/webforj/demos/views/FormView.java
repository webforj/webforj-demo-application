package com.webforj.demos.views;

import java.util.ArrayList;
import java.util.UUID;

import com.webforj.App;
import com.webforj.annotation.InlineStyleSheet;
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

@InlineStyleSheet("context://css/views/form.css")
@Route("customer/:id?")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements DidEnterObserver {
  Customer customer = new Customer();
  Div self = getBoundComponent();
  TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
  TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
  TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
  ChoiceBox country = new ChoiceBox("Country",
      e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
  Button submit = new Button("Submit", ButtonTheme.PRIMARY);
  Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, e -> Router.getCurrent().navigate(DemoView.class));

  ColumnsLayout columnsLayout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      cancel, submit);

  public FormView() {
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
