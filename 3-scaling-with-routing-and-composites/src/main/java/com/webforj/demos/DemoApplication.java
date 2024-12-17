package com.webforj.demos;

import com.webforj.App;
import com.webforj.annotation.AppTitle;
import com.webforj.annotation.Routify;

@Routify(packages = "com.webforj.demos.views", debug = true)
@AppTitle("Demo Step 3")
public class DemoApplication extends App {
}