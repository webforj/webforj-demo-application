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
import com.webforj.exceptions.WebforjException;
import com.webforj.component.optiondialog.OptionDialog;

@SpringBootApplication
@StyleSheet("ws://app.css")
@AppTheme("system")
@AppProfile(name = "DemoApplication", shortName = "DemoApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph demo = new Paragraph("Demo Application!");
    Button btn = new Button("Info");
    mainFrame.addClassName("mainFrame");

    btn.setTheme(ButtonTheme.PRIMARY).addClickListener(e -> OptionDialog.showMessageDialog("This is a demo!", "Info"));
    mainFrame.add(demo, btn);
  }
}
