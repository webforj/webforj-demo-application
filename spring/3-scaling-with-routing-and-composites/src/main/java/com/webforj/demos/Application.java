package com.webforj.demos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.webforj.App;
import com.webforj.annotation.AppProfile;
import com.webforj.annotation.AppTheme;
import com.webforj.annotation.Routify;
import com.webforj.annotation.StyleSheet;

@SpringBootApplication
@StyleSheet("ws://css/app.css")
@AppTheme("system")
@Routify(packages = "com.webforj.demos.views")
@AppProfile(name = "DemoApplication", shortName = "DemoApplication")
public class Application extends App {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
