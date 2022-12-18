package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CallbackTest {
    WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
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
        String actualTitle = driver.findElement(By.xpath("//h2[contains(text(),'Заявка на дебетовую карту')]")).getText().trim();
        assertEquals(actualTitle, expectedTitle);
    }

    @Test
    void shouldTestErrorMessageName() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79126178980");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("span[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(actual, expected);
    }

    @Test
    void shouldTestErrorMessageNullName() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys(" ");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79126178980");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("span[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(actual, expected);
    }

    @Test
    void shouldTestErrorMessagePhone() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+374091105218");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("span[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(actual, expected);
    }

    @Test
    void shouldTestErrorMessageNullPhone() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys(" ");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("span[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(actual, expected);
    }

    @Test
    void shouldTestErrorOfCheckbox() throws InterruptedException, ClassNotFoundException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79126178980");
        driver.findElement(By.className("button")).click();
        driver.findElement(By.cssSelector("label[data-test-id=agreement].input_invalid")).isDisplayed();
    }
}
