package comp.jbp.imdbs;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SampleClass {

    public static void main(String[] args) throws Exception {

        System.setProperty("webdriver.chrome.driver","/Users/jek.perpenan/Documents/Installers/chromedriver");
        WebDriver driver = new ChromeDriver();

        //String baseUrl = "http://en.wikipedia.org/wiki/Test/newtours/";
        String imdbBaseUrl = "https://www.imdb.com/title/tt";

        //driver.get(baseUrl);

        //WebElement sub = driver.findElement(By.id("siteSub"));
        //System.out.println(sub.getText());

        //driver.close();

        int ctr = 1;
        //List<String[]> lines = new ArrayList<>();

        for (String[] str: readAllLines()) {

            if (ctr == 1) {
                ctr++;
                continue;
            }

            //System.out.println(imdbBaseUrl+str[1]+"/");
            driver.get(imdbBaseUrl+str[1]+"/");

            //System.out.println(imdbBaseUrl+str[1]+"/");
            //System.out.println(driver.getTitle());
            //str[3]
            //lines.add(str);

            //ctr++;
            //WebElement sub = driver.findElement(By.className("ipc-metadata-list-item__list-content-item"));
            //WebElement sub = driver.findElement(By.className("ipc-metadata-list-item__content-container"));
            //System.out.println(sub.getText());

     /*       String kk = driver.findElement(By.cssSelector("#ipc-metadata-list-item__content-container span.ipc-metadata-list-item__list-content-item"))
                    .getAttribute("innerHTML");*/

            try {
                String kk = driver.findElement(By.xpath(
                        "//*[@id=\"__next\"]/main/div/section[1]/div/section/div/div[1]/section[1]/div/ul/li/div/ul/li/span"))
                        .getAttribute("innerHTML");
                System.out.println(kk.substring(0,2));
            } catch (Exception e) {
                System.out.println(0);
            }
        }
        driver.close();
    }

    public static List<String[]> readAllLines() throws Exception {
        Path path = Paths.get
                (ClassLoader.getSystemResource("links.csv").toURI());
        try (Reader reader = Files.newBufferedReader(path)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }
}
