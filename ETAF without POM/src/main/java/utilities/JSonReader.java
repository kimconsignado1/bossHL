package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import com.google.common.io.Resources;
import com.google.gson.Gson;

public class JSonReader {
	public class UpriseLoginCredentials {
		private String appEnvironment = "Regression";
		private String userRole = "Provider";
		private String url;
		private String userName;
		private String password;
		private String insurancePrimary;
		private String insuranceSecondary;
		private String dropToPaper;

		public UpriseLoginCredentials() {
			try {
				File jsonFile = new File(Resources.getResource("upriseLoginCredentials.json").getPath());
				FileReader jsonFileReader = new FileReader(jsonFile);
				Gson gson = new Gson();
				Root environment = gson.fromJson(jsonFileReader, Root.class);
				this.url = environment.Environment.get(appEnvironment).URL;
				List<EnvironmentVariables> credentials = environment.Environment.get(appEnvironment).Role;
				for (int i = 0; i < credentials.size(); i++) {
					if (credentials.get(i).UserRole.equalsIgnoreCase(userRole)) {
						this.userName = credentials.get(i).UserName;
						this.password = credentials.get(i).Password;
						this.insurancePrimary = credentials.get(i).Insurance;
						this.insuranceSecondary = credentials.get(i).InsuranceSecondary;
						this.dropToPaper = credentials.get(i).DropToPaper;
						break;
					}
				}
				
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("Environment: " + this.appEnvironment);
				System.out.println("URL: " + this.url);
				System.out.println("User Name: " + this.userName);
				System.out.println("User Role: " + this.userRole);
				System.out.println("Password: " + this.password);
				System.out.println("Primary Insurance: " + this.insurancePrimary);
				System.out.println("Secondary Insurance: " + this.insuranceSecondary);
				System.out.println("Drop to Paper: " + this.dropToPaper);
				System.out.println("-----------------------------------------------------------------------");
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		private class EnvironmentVariables {
			private String UserRole;
			private String UserName;
			private String Password;
			private String Insurance;
			private String InsuranceSecondary;
			private String DropToPaper;
		}

		private class AppEnvironment {
			private String URL;
			private List<EnvironmentVariables> Role;
		}

		private class Root {
			public Map<String, AppEnvironment> Environment;
		}
	}
}
