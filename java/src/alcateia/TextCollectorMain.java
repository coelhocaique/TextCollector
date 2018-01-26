package alcateia;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Pegar troféu de 150 acessos no alcatéia date 03/03/2017
 * 
 * @author Caique Coelho
 */

public class TextCollectorMain {

	public static void main(String[] args) throws IOException {

		File file = new File("C:/chromedriver.exe");

		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

		final String URL = "https://painel.alcateiainvestimentos.com/Painel/login";

		JLabel jUserName = new JLabel("User Name");
		JTextField userName = new JTextField();
		JLabel jPassword = new JLabel("Password");
		JTextField password = new JPasswordField();
		JLabel jCount = new JLabel("Número de acessos:");
		JTextField tCount = new JTextField();
		Object[] ob = { jUserName, userName, jPassword, password,jCount,tCount};

		int result = JOptionPane.showConfirmDialog(null, ob, "Please input password for JOptionPane showConfirmDialog",
				JOptionPane.OK_CANCEL_OPTION);

		String user = null, pass = null;
		int count = 0;
		if (result == JOptionPane.OK_OPTION) {
			user = userName.getText();
			pass = password.getText();
			count = Integer.parseInt(tCount.getText());
		}
		
		WebDriver driver = new ChromeDriver();

		if (user != null && pass != null) {
			while (count-- > 0) {
				
				driver.navigate().to(URL);

				List<WebElement> elements = driver.findElements(By.tagName("input"));

				for (int i = 0; i < elements.size(); i++) {
					try {
						if (elements.get(i).getAttribute("type").equalsIgnoreCase("text")) {
							elements.get(i).sendKeys(user);
						}
						if (elements.get(i).getAttribute("type").equalsIgnoreCase("password")) {

							elements.get(i).sendKeys(pass);
						}
						if (elements.get(i).getAttribute("type").equalsIgnoreCase("submit")) {

							elements.get(i).submit();
						}

					} catch (Exception e) {
						e.printStackTrace();
						driver.close();
					}
				}
			}
		}
		driver.close();
	}
}
