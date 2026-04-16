package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class App {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/product_details/1");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-default.cart")));
        addToCartButton.click();

        WebElement closeModalButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-close")));
        closeModalButton.click();

        WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".shopping_cart a")));
        cartLink.click();

        System.out.println("Product has been added to the cart successfully. Enjoy Shopping");

        driver.quit();
    }
}
