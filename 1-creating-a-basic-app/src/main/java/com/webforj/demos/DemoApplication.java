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

/**
 * Demo application showcasing a simple WebforJ UI with a button and a paragraph.
 * The application uses a button that displays a message dialog.
 */
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Demo Step 1")
public class DemoApplication extends App {
  
  /** Paragraph displaying the demo message. */
  Paragraph demo = new Paragraph("Demo Application!");

  /** Button that triggers an info message dialog on click. */
  Button btn = new Button("Info");

  /**
   * Runs the demo application..
   *
   * @throws WebforjException if an error occurs during application execution
   */
  @Override
  public void run() throws WebforjException {
    // Create the main application frame
    Frame mainFrame = new Frame();
    mainFrame.addClassName("mainFrame");

    // Configure the button with a theme and a click event listener
    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("This is a demo!", "Info"));

    // Add components to the main frame
    mainFrame.add(demo, btn);
  }
}
