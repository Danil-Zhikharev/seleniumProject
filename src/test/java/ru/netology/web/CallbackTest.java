package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestSuccessMessage() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79126178980");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("p[data-test-id=order-success]")).getText().trim();
        assertEquals(actual, expected);
        String expectedTitle = "Заявка на дебетовую карту";
        String actualTitle = driver.findElement(By.xpath("//h2[@class='heading heading_size_l heading_theme_alfa-on-white']")).getText().trim();
        assertEquals(actualTitle, expectedTitle);
    }

    @Test
    void shouldTestErrorMessageName() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Ivanov Ivan");
        driver.findElement(By.className("button")).click();
        List<WebElement> input__sub = driver.findElements(By.xpath(".//span[@class='input__sub']"));
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = input__sub.get(0).getText().trim();
        assertEquals(actual, expected);
    }

    @Test
    void shouldTestErrorMessagePhone() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+374091105218");
        driver.findElement(By.className("button")).click();
        List<WebElement> input__sub = driver.findElements(By.xpath(".//span[@class='input__sub']"));
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = input__sub.get(1).getText().trim();
        assertEquals(actual, expected);
    }

    @Test
    void shouldTestErrorColorOfCheckbox() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79126178980");
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.className("checkbox__text")).getCssValue("color");
        String expected = "rgba(255, 92, 92, 1)";
        assertEquals(actual, expected);
    }
}
