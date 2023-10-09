package user_management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class UserManagement {

	static ArrayList<User> al = new ArrayList();

	static {
			try {
				loadDataFromFileToArrayList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static void userManagement() throws IOException {

		Scanner scanner = new Scanner(System.in); // Creating scanner object to get option from user

		boolean canIKeepRunningTheProgram = true; // created variable to decide should we continue running program or
													// not

		while (canIKeepRunningTheProgram == true) { // Checking if used asked to exiting, if option set as 5

			System.out.println("**** Welcome to User Management *****");
			System.out.println("\n");
			System.out.println("What would you like to do ?");
			System.out.println("1. Add User");
			System.out.println("2. Edit User");
			System.out.println("3. Delete User");
			System.out.println("4. Search User");
			System.out.println("5. Quit");

			int optionSelectedByUser = scanner.nextInt();

			if (optionSelectedByUser == UserOptions.QUIT) {
				File file = new File(
						"/Users/vzodge/Documents/ECLIPSE_WORKSPACES/w22-development/ShopManagement/src/user_management/Users.csv");

				FileWriter fileWriter = new FileWriter(file, false); // Pass true for append mode

				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				for (User user : al) {
					bufferedWriter.write(
							user.loginName + "," + user.password + "," + user.userName + "," + user.userRole + "\n");
				}

				bufferedWriter.close(); // Save
				fileWriter.close();
				file = null;

				System.out.println("!!! Program closed");
				canIKeepRunningTheProgram = false;

			} else if (optionSelectedByUser == UserOptions.ADD_USER) {
				addUser();
				System.out.println("\n");
			} else if (optionSelectedByUser == UserOptions.SEARCH_USER) {
				System.out.print("Enter User Name to search: ");
				scanner.nextLine(); // Consume the newline character left from previous input
				String sn = scanner.nextLine();
				searchUser(sn);
				System.out.println("\n");
			} else if (optionSelectedByUser == UserOptions.DELETE_USER) {
				System.out.print("Enter User Name to delete: ");
				scanner.nextLine(); // Consume the newline character left from previous input
				String deleteUserName = scanner.nextLine();
				deleteUser(deleteUserName);
				System.out.println("\n");
			} else if (optionSelectedByUser == UserOptions.EDIT_USER) {
				System.out.print("Enter User Name to edit: ");
				scanner.nextLine(); // Consume the newline character left from previous input
				String editUserName = scanner.nextLine();
				editUser(editUserName);
				System.out.println("\n");
			}

		}
		System.out.println("\n");

	}

	// ******* Add User Function *****
	public static void addUser() {
		Scanner scanner = new Scanner(System.in);

		User userObject = new User(); // User object

		System.out.print("User Name: ");
		userObject.userName = scanner.nextLine();

		System.out.print("Login Name: ");
		userObject.loginName = scanner.nextLine();

		System.out.print("Password: ");
		userObject.password = scanner.nextLine();

		System.out.print("Confirm Password: ");
		userObject.confirmPassword = scanner.nextLine();

		System.out.print("User Role: ");
		userObject.userRole = scanner.nextLine();

		System.out.println("User Name: " + userObject.userName);
		System.out.println("Login Name: " + userObject.loginName);
		System.out.println("Password: " + userObject.password);
		System.out.println("Confirm Password: " + userObject.confirmPassword);
		System.out.println("User Role: " + userObject.userRole);

		al.add(userObject);

	}

	public static void searchUser(String userName) {
		for (User user : al) { // For-Each
			if (user.userName.equalsIgnoreCase(userName)) {
				System.out.println("User Name: " + user.userName);
				System.out.println("Login Name: " + user.loginName);
				System.out.println("Password: " + user.password);
				System.out.println("User Role: " + user.userRole);
				// break;
				return;
			}
		}

		System.out.println("User not found.");

	}

	// ******* Delete User Function *****
	public static void deleteUser(String userName) {
		Iterator<User> userIterator = al.iterator();

		while (userIterator.hasNext()) {
			User user = userIterator.next();
			if (user.userName.equalsIgnoreCase(userName)) {
				userIterator.remove();
				System.out.println("User " + user.userName + " has been deleted.");
				break;
			}
		}

	}

	// ******* Edit User Function *****
	public static void editUser(String userName) {
		for (User user : al) { // For Each
			if (user.userName.equalsIgnoreCase(userName)) {

				Scanner scanner = new Scanner(System.in);

				System.out.println("Editing user: " + user.userName);

				System.out.print("New User Name: ");
				user.userName = scanner.nextLine();

				System.out.print("New Login Name: ");
				user.loginName = scanner.nextLine();

				System.out.print("New Password: ");
				user.password = scanner.nextLine();

				System.out.print("New Confirm Password: ");
				user.confirmPassword = scanner.nextLine();

				System.out.print("New User Role: ");
				user.userRole = scanner.nextLine();

				System.out.println("User information updated.");

				return;
			}
		}

		System.out.println("User not found.");
	}

	public static void loadDataFromFileToArrayList() throws IOException  {
		
		File file = new File(
				"/Users/vzodge/Documents/ECLIPSE_WORKSPACES/w22-development/ShopManagement/src/user_management/Users.csv");

		
		FileReader fr = new FileReader(file);

		BufferedReader br = new BufferedReader(fr);

		String line = "";

		while ((line = br.readLine()) != null) {
			User user = new User();

			String[] userDataArray = line.split(",");

			if (userDataArray.length > 3) {
				user.loginName = userDataArray[0];
				user.password = userDataArray[1];
				user.userName = userDataArray[2];
				user.userRole = userDataArray[3];

				al.add(user);
			}

		}
		br.close();
		fr.close();
		file = null;

	}

	public static boolean validateUserAndPassword(String loginName, String password) throws IOException {
		Iterator<User> userIterator = al.iterator();

		while (userIterator.hasNext()) {
			User user = userIterator.next();
			if (user.loginName.equalsIgnoreCase(loginName) && user.password.equalsIgnoreCase(password)) {
				return true;
			}
		}
		return false;
	}

}
