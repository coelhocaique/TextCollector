package download_video;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


/**
 * 
 * @author Caique dos Santos Coelho
 * @version 1.0.0
 * This program searches for a specified song on Youtube, 
 * and download the first match in mp3 
 */
public class DownloadMp3Video {

	public static void main(String[] args) throws IOException {

		File file = new File("C:/chromedriver.exe");
		WebDriver driver = null;
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		
		/*
		 * URL of the website for downloading the video directly in MP3
		 */
		final String downloadUrl = "http://www.youtube-mp3.org/pt";
		/*
		 * Youtube URL 
		 */
		final String youtubeUrl = "https://www.youtube.com";
		/*
		 * this will hold the complete youtubeUrl
		 */
		String completeYoutubeUrl = ""; 

		BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
		
		/*
		 * Read Data
		 */
		String s = "";
		while(true){
			String aux = scan.readLine();
			if(aux == null || aux.equals("-1"))
				break;
			if(aux != null && !aux.contains("VEVO"))
				s+= aux + "\n";
		}
		
		/*
		 * variable that carries all the songs name
		 */
		String[] searches = s.split("\n");
		
		/*
		 * Opens a new Chrome window
		 */
		driver = new ChromeDriver();
		
		for (String search : searches) {
			driver.navigate().to(youtubeUrl);
			try {
				
				/*
				 * searches for the first input on youtube website 
				 * then sends the name of the songs to be searched
				 */
				
				List<WebElement> elements = driver.findElements(By.tagName("input"));
				
				for (int i = 0; i < elements.size(); i++) {
					if (elements.get(i).getAttribute("type").equalsIgnoreCase("text")) {
						elements.get(i).sendKeys(search);
						break;
					}
					
				}
				/*
				 * this is how youtube's submit button works 
				 */
				elements = driver.findElements(By.id("masthead-search-term"));
				elements.get(0).submit();
				
				/*
				 * looks for the first href attribute that contains watch 
				 * which means it finds the best match 
				 */
				elements = driver.findElements(By.tagName("a"));
				for (WebElement webElement : elements) {
					if(webElement.getAttribute("href").contains("watch")){
						completeYoutubeUrl = webElement.getAttribute("href");
						System.out.println("Url of the video being downloaded: "+ completeYoutubeUrl);
						break;
					}
				}
				
				/*
				 * opens the website to download the video
				 */
				driver.navigate().to(downloadUrl);
				
				/*
				 * the download website only has a text field and a button
				 * so it searches the first input text, send the youtube url to download 
				 * then click on submit
				 */
				
				elements = driver.findElements(By.tagName("input"));
				
				for (int i = 0; i < elements.size(); i++) {
					if (elements.get(i).getAttribute("type").equalsIgnoreCase("text")) {
						elements.get(i).clear();
						elements.get(i).sendKeys(completeYoutubeUrl);
					}
					if (elements.get(i).getAttribute("type").equalsIgnoreCase("submit")) {
						elements.get(i).submit();
						break;
					}
				}
				
				/*
				 * waits for the video to download
				 */
				Thread.sleep(3000);
				
				/*
				 * click on download link, to download mp3 song
				 */
				elements = driver.findElements(By.tagName("a"));
				for (WebElement webElement : elements) {
					if(webElement.getText().equalsIgnoreCase("download")){
						webElement.click();
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//driver.close();
	}
}
