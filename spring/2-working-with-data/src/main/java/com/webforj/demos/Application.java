package com.webforj.demos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.webforj.App;
import com.webforj.annotation.AppProfile;
import com.webforj.annotation.AppTheme;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.window.Frame;
import com.webforj.demos.entity.Customer;
import com.webforj.demos.service.CustomerService;
import com.webforj.exceptions.WebforjException;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.component.table.Table;

@SpringBootApplication
@StyleSheet("ws://css/app.css")
@AppTheme("system")
@AppProfile(name = "DemoApplication", shortName = "DemoApplication")
public class Application extends App {
  private final CustomerService customerService;

  public Application(CustomerService customerService) {
    this.customerService = customerService;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph demo = new Paragraph("Demo Application!");
    Button btn = new Button("Info");
    Table<Customer> table = new Table<>();

    mainFrame.addClassName("mainFrame");
    table.setHeight("294px");
    table.setWidth(1000);

    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    table.setRepository(customerService.getFilterableRepository());
    btn.setTheme(ButtonTheme.PRIMARY).addClickListener(e -> OptionDialog.showMessageDialog("This is a demo!", "Info"));
    mainFrame.add(demo, btn, table);
  }

}
