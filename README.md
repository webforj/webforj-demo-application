# **webforJ Customer Management Tutorial App**

This project demonstrates how to build a customer management app using the **webforJ framework**. The app includes features for viewing, adding, and editing customer data and is structured as a step-by-step progression through key webforJ concepts.

For a complete step-by-step guide to building this app, see the **[webforJ Tutorial](https://docs.webforj.com/docs/introduction/tutorial/overview)**.

The project compiles into a **WAR file**, which can be deployed to any Java web server. For local development, the **Maven Jetty plugin** is used to quickly run the app and view changes in real time.

---

## **Prerequisites**

To run the app, ensure the following tools are installed on your machine:

- Java 17 or higher
- Maven
- A Java IDE (e.g., IntelliJ IDEA, Eclipse, VSCode)
- Web browser
- Git (recommended)

---

## **Project structure**

```bash
webforj-demo-application
│   .gitignore
│   LICENSE
│   README.md
│   tree.txt
│
├───1-creating-a-basic-app         # Step 1: Basic app with a button and dialog
├───2-working-with-data            # Step 2: Table integration and data handling
├───3-scaling-with-routing-and-composites  # Step 3: Routing and reusable components
└───4-validating-and-binding-data  # Step 4: Data validation and bindings
```

## **Running the App**

Follow these steps to run the app locally at any stage:

1) **Navigate to a Step Directory:**
Choose the directory for the desired step, e.g., `1-creating-a-basic-app`.
```bash
cd 1-creating-a-basic-app
```
2) **Run the app using Maven Jetty:**
Use the Maven Jetty plugin to deploy the app locally:
```bash
mvn jetty:run
```
3) **View the app**
Open your browser and go to http://localhost:8080. Repeat this process for each step as needed to explore the app's progression.

## **License**
This project is licensed under the MIT License. See the LICENSE file for details.