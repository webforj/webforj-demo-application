package com.webforj.demos;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.App;
import com.webforj.annotation.AppTitle;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.window.Frame;
import com.webforj.exceptions.WebforjException;

@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Demo Step 1")
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Demo Application!");
  Button btn = new Button("Info");

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    mainFrame.addClassName("mainFrame");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("This is a demo!", "Info"));

    mainFrame.add(demo, btn);
  }
}