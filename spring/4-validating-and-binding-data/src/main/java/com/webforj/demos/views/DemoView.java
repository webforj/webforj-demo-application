package com.webforj.demos.views;

import com.webforj.App;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Table;
import com.webforj.component.table.event.item.TableItemClickEvent;
import com.webforj.demos.entity.Customer;
import com.webforj.demos.service.CustomerService;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;

@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {

  final CustomerService customerService;

  Table<Customer> table = new Table<>();
  Div self = getBoundComponent();
  Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
      e -> Router.getCurrent().navigate(FormView.class));

  public DemoView(CustomerService customerService) {
    this.customerService = customerService;

    add.setStyle("margin", "var(--dwc-space-l)").setWidth(200);
    buildTable();
    table.addItemClickListener(this::editCustomer);
    FlexLayout layout = FlexLayout.create(table, add)
        .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");
    self.add(layout);
  }

  private void buildTable() {
    table.setHeight("294px");

    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getFilterableRepository());
  }

  private void editCustomer(TableItemClickEvent<Customer> e) {
    //App.console().log(e.getItemKey());
    Router.getCurrent().navigate(FormView.class,
        ParametersBag.of("id=" + e.getItemKey()));
  }
}
