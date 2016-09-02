package collector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TextCollectorMain {

	private static int index = 1;

	public static void main(String[] args) throws IOException {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\IBM_ADMIN\\Desktop\\chromedriver.exe");

		String URL = "http://www.marca.com/futbol.html?cid=MENUDES3601&s_kw=futbol";
		FileWriter bw = new FileWriter(new File("file/frases_esp.txt"));
		WebDriver driver = new ChromeDriver();
		driver.navigate().to(URL);

		List<WebElement> elements = driver.findElements(By.cssSelector("a"));
		System.out.println(elements.size());
		for (int i = 25; i < elements.size(); i++) {
			if (index == 150)
				break;
			try {
				String link = elements.get(i).getAttribute("href");
				if (link != null && (link.endsWith("html") || link.endsWith("/"))) {
					WebDriver driver2 = new ChromeDriver();
					driver2.navigate().to(link);
					List<WebElement> textEle = driver2.findElements(By.cssSelector("p"));
					bw.write("Source: " + link + "\n");
					System.out.println(link);
					System.out.println("p size: " + textEle.size());
					if (textEle != null && !textEle.isEmpty()) {
						bw.write(index++ + "\n");
						for (WebElement webElement2 : textEle) {
							String s = webElement2.getText();
							if (s != null && !s.isEmpty())
								bw.write(s + "\n");
						}
					}
					driver2.close();
				}

			} catch (Exception e) {
				continue;
			}
		}
		driver.close();
		bw.close();
	}
}
