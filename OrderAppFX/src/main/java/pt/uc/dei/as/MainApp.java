/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.uc.dei.as.controller.LoginController;
import pt.uc.dei.as.controller.OrderEditorController;
import pt.uc.dei.as.controller.OrderOverviewController;
import pt.uc.dei.as.data.Login;
import pt.uc.dei.as.entity.Employer;
import pt.uc.dei.as.entity.Order;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class MainApp.
 */
public class MainApp extends Application {

	/** Current employee working*/
	private static Employer employer;

	/** The primary stage. */
	private Stage primaryStage;
	
	/** The root layout. */
	private BorderPane rootLayout;

	/** The em. */
	public static EntityManager em;
	
	/** The pm. */
	private static PersistenceManager pm;

	/** The orders data. */
	private ObservableList<Order> ordersData = FXCollections.observableArrayList();

	/**
	 * Instantiates a new main app.
	 *
	 * @throws Exception the exception
	 */
	public MainApp() throws Exception {

	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public static Employer getEmployer() {
		return employer;
	}

	/**
	 * Gets the orders data.
	 *
	 * @return the orders data
	 */
	public ObservableList<Order> getOrdersData() {
		return ordersData;
	}

	/**
	 * Sets the orders data.
	 *
	 * @param ordersData the new orders data
	 */
	public void setOrdersData(List<Order> ordersData) {
		this.ordersData.setAll(ordersData);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("OrderAppFX");
		this.primaryStage.getIcons().add(new Image("/images/sprout.png"));
		Platform.setImplicitExit(true);

		/*try {
			TypedQuery<Order> query = MainApp.em.createNamedQuery("Order.findAll", Order.class);
			ordersData.setAll(query.getResultList());
		} catch (Exception e) {
			AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
			MainApp.refreshEm();
		}*/


		/*MainApp.em.getTransaction().begin();
        MainApp.em.persist(new Employer("ola", "12@34", "locX", "91"));
        MainApp.em.getTransaction().commit();*/

		//initRootLayout();
		initLoginLayout();
		//showOrderOverview();
		showLogginOverview();

	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public void stop() throws Exception {
		try {
			Login login = new Login();
			login.setPassword(employer.getEmployers_Password());
			login.setUSername(employer.getEmployers_Name());
			Employer e = RestsUtils.doPost("logout", login, Employer.class, HttpURLConnection.HTTP_OK);
			super.stop();
			Platform.exit();
			System.exit(0);
		}catch (RuntimeException ex){
			System.out.println("Erro no registo do logout no servidor!");
		}
	}

	/**
	 * Inits the root layout.
	/* */

	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			rootLayout.getStylesheets().add("/stylesheets/DarkTheme.css");

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initLoginLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/RootLogin.fxml"));
			rootLayout = (BorderPane) loader.load();
			rootLayout.getStylesheets().add("/stylesheets/DarkTheme.css");

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show order overview.
	 */
	/*public void showOrderOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/OrderOverview.fxml"));
			AnchorPane orderOverview = (AnchorPane) loader.load();
			orderOverview.getStylesheets().add("/stylesheets/DarkTheme.css");

			rootLayout.setCenter(orderOverview);
			OrderOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	public void showOrderOverview() {
		try {
			initRootLayout();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/OrderOverview.fxml"));
			AnchorPane orderOverview = (AnchorPane) loader.load();
			orderOverview.getStylesheets().add("/stylesheets/DarkTheme.css");

			rootLayout.setCenter(orderOverview);
			OrderOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showLogginOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/Login.fxml"));
			AnchorPane orderOverview = (AnchorPane) loader.load();
			orderOverview.getStylesheets().add("/stylesheets/DarkTheme.css");

			rootLayout.setCenter(orderOverview);
			LoginController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Show order editor dialog.
	 *
	 * @param order the order
	 * @return true, if successful
	 */
	public boolean showOrderEditorDialog(Order order) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/OrderEditor.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			page.getStylesheets().add("/stylesheets/DarkTheme.css");

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Order");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			OrderEditorController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setOrder(order);

			dialogStage.showAndWait();

			return controller.isSaveClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets the primary stage.
	 *
	 * @return the primary stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Refresh em.
	 */
	public static void refreshEm() {
		MainApp.em.close();
		MainApp.em = MainApp.pm.getEntityManager();
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			if (args != null && args.length == 4) {
				MainApp.pm = new PersistenceManager(args[0], args[1], args[2], args[3]);
			} else if (args != null && args.length == 1) {
				MainApp.pm = new PersistenceManager(args[0]);
			} else {
				MainApp.pm = new PersistenceManager();
			}
			MainApp.em = pm.getEntityManager();
		} catch (Exception e) {
			System.err.println(
					"\nOrderAppFX: Cannot connect to the \"ete_db\" database!. Please check if the database is running and properly configured.\n");
			System.err.println(
					"OrderAppFX: To bypass the default database configuration please use the following command:");
			System.err.println("java -jar OrderAppFX-0.0.1.jar <driver> <url> <username> <password> \n");
			System.err.println("OrderAppFX: Alternatively, just pass the database server IP:");
			System.err.println("java -jar OrderAppFX-0.0.1.jar <db_server_IP> \n");
			System.exit(-1);
			
			
			
		}
		
		
		launch(args);
	}

}
