import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencsv.CSVWriter;

import junit.framework.Assert;

public class mainPagePOM {
	WebDriver driver;
	List<String[]> l = new ArrayList<>();
	
	
	By searchInputField = By.id("twotabsearchtextbox");
	By searchLink = By.id("nav-search-submit-button");
	By itemsList = By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/div/span[3]/div[2]/div");	                           
	By nextResPage = By.cssSelector(".a-last > a:nth-child(1)");
	
	
    public mainPagePOM(WebDriver driver) {		
		this.driver = driver;		
	}	
	
	
	public void searchAnItem(String itemName) {		
		driver.findElement(searchInputField).sendKeys(itemName);
		driver.findElement(searchLink).click();
	}
	
	public void moveToNextResultPage() {
		driver.findElement(nextResPage).click();		
	}
	
	public void openCSVfile() throws IOException {
	
		try (CSVWriter writer = new CSVWriter(new FileWriter("details.csv"))) {  
			String[] header = {"item title", "price", "number of stars"};
			l.add(header);
			  writer.writeAll(l);			 

	    } catch (FileNotFoundException e) {
	      System.out.println(e.getMessage());
	    }		
	}
	
	
	public void writeIntoCSVfile() throws IOException {
		List<WebElement> list =driver.findElements(itemsList);
		int i=0;
		String stars="", title= "", price = "";		
		CSVWriter writer = new CSVWriter(new FileWriter("details.csv"));
	
		for (WebElement el : list) {			
			if(i<48) {
				try {	
					title = el.findElement(By.xpath("./div/span/div/div/div[2]/h2/a")).getText();
				}catch (Exception e) {					
					title = "No title!";
				}
				try {					
					stars = el.findElement(By.xpath("./div/span/div/div/div[3]/div/span[1]/span/a/i[1]/span")).getAttribute("innerHTML");
				}catch (Exception e) {					
					stars = "No stars!";
				}
				try {							
					price = el.findElement(By.xpath("./div/span/div/div/div[4]/div[2]/div/div/a/span[1]/span[1]")).getAttribute("innerHTML");						
				}catch (Exception e) {					
					price = "No price!";
				}
				String[] record1 = {title, price, stars};
				l.add(record1);			
			}
			i++;
		}
		writer.writeAll(l);
		writer.close();	   
	}
	
	

	public void csvFileValidations(String titleToBe, String priceToCheck) throws IOException {
		BufferedReader bfr = new BufferedReader(new FileReader("details.csv"));

		String titleRow = null, priceRow = null;
	    String  l= "";
	   
	  
	    while((l = bfr.readLine()) != null){

	        //break each line of the csv file to its elements
	        String[] elements=l.split(",");	
	        if(elements[0].contains(titleToBe)) {
	        	titleRow = l;
	            break;
	        }
	        if(elements[1].equals(priceToCheck)) {
	        	priceRow = l;
	            break;
	        }
	    }
	        
	        Assert.assertTrue("Verification Failed: The csv file does not contain the title: " + titleToBe ,titleRow!=null);
	        Assert.assertTrue("Verification Failed: The csv file contains item with price: " + priceToCheck, priceRow!=null);
	}

}
