package com.webforj.demos;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.App;
import com.webforj.annotation.AppTitle;
import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.table.Table;
import com.webforj.component.window.Frame;
import com.webforj.demos.data.Service;
import com.webforj.demos.models.Customer;
import com.webforj.exceptions.WebforjException;

@InlineStyleSheet("context://css/demoApplication.css")
@AppTitle("Demo Step 2")
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Demo Application!");
  Button btn = new Button("Info");
  Table<Customer> table = new Table<>();

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    mainFrame.addClassName("mainFrame");
    buildTable();
    btn.setTheme(ButtonTheme.PRIMARY).setWidth(100)
        .addClickListener(e -> showMessageDialog("This is a demo with a table!", "Info"));

    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    table.setHeight("300px");
    table.setWidth(1000);

    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    table.setRepository(Service.getCurrent().getCustomers());
  }
}