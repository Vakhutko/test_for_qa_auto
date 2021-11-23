import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TestForQA {
    static WebDriver driver;

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        //Start_Test
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Home_Site
        driver.get("https://lmslite47vr.demo.mirapolis.ru/mira");

        //Home_Button_Check
        WebElement homeButton = driver.findElement(By.xpath("//body/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/a[1]"));
        System.out.println("home_button_displayed: " + homeButton.isDisplayed());
        System.out.println("home_button_enabled: " + homeButton.isEnabled());
        homeButton.click();
        System.out.println(driver.getCurrentUrl().endsWith("type=customloginpage"));

        //Main_Login_Window_Check
        WebElement mainLoginText = driver.findElement(By.xpath("//div[contains(text(),'Вход в систему')]"));
        System.out.println("main_login_text_displayed: " + mainLoginText.isDisplayed());
        System.out.println("main_login_text_enabled: " + mainLoginText.isEnabled());
        System.out.println(mainLoginText.getText().equals("Вход в систему"));

        //Login_Field_Check
        WebElement login = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
        System.out.println("login_displayed: " + login.isDisplayed());
        System.out.println("login_enabled: " + login.isEnabled());
        System.out.println(login.getAttribute("placeholder").equals("Введите ваш логин"));

        //Password_Field_Check
        WebElement password = driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[1]/input[1]"));
        System.out.println("password_displayed: " + password.isDisplayed());
        System.out.println("password_enabled: " + password.isEnabled());
        System.out.println(password.getAttribute("placeholder").equals("Введите ваш пароль"));

        //Password_Visible_Check
        WebElement passwordVisible = driver.findElement(By.xpath("//button[@id='show_password']"));
        System.out.println("password_visible_displayed: " + passwordVisible.isDisplayed());
        System.out.println("password_visible_enabled: " + passwordVisible.isEnabled());
        password.sendKeys("1P73BP4Z");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String first = password.getAttribute("type");
        System.out.println("password_type_check: " + first.equals("password"));
        passwordVisible.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String second = password.getAttribute("type");
        System.out.println("password_type_check: " + second.equals("text"));
        password.clear();

        //Login_Button_Check
        WebElement loginButton = driver.findElement(By.xpath("//button[@id='button_submit_login_form']"));
        System.out.println("login_button_displayed: " + loginButton.isDisplayed());
        System.out.println("login_button_enabled: " + loginButton.isEnabled());
        System.out.println(loginButton.getText().equals("Войти"));

        //Reset_Password_Check
        WebElement resetPasswordButton = driver.findElement(By.xpath("//tbody/tr[1]/td[1]/a[1]/div[1]"));
        System.out.println("reset_password_button_displayed: " + resetPasswordButton.isDisplayed());
        System.out.println("reset_password_button_enabled: " + resetPasswordButton.isEnabled());
        System.out.println(resetPasswordButton.getText().equals("Забыли пароль?"));

        //Happy_Path_Check
        login.sendKeys("fominaelena");
        password.sendKeys("1P73BP4Z");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginButton.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(driver.findElement(By.xpath("//span[contains(text(),'Главная страница')]")).getText().equals("Главная страница"));
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Выйти')]")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Bad_Path_All_Credential_Check
        login = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
        login.sendKeys("Az04!~#$%^&*()_+");
        password = driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[1]/input[1]"));
        password.sendKeys("Az04!~#$%^&*()_+");
        loginButton = driver.findElement(By.xpath("//button[@id='button_submit_login_form']"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginButton.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Alert alert = driver.switchTo().alert();
        System.out.println("wrong_all_credential_check: " + alert.getText().equals("Неверные данные для авторизации"));
        alert.accept();

        //Bad_Path_Without_Credential_Check
        loginButton = driver.findElement(By.xpath("//button[@id='button_submit_login_form']"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginButton.click();
        alert = driver.switchTo().alert();
        System.out.println("wrong_without_credential_check: " + alert.getText().equals("Неверные данные для авторизации."));
        alert.accept();

        //Bad_Path_No_Password_Credential_Check
        login = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
        login.sendKeys("Az04!~#$%^&*()_+");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginButton = driver.findElement(By.xpath("//button[@id='button_submit_login_form']"));
        loginButton.click();
        alert = driver.switchTo().alert();
        System.out.println("wrong_no_password_credential_check: " + alert.getText().equals("Неверные данные для авторизации."));
        alert.accept();

        //Bad_Path_No_Login_Credential_Check
        password = driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[1]/input[1]"));
        password.sendKeys("Az04!~#$%^&*()_+");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginButton = driver.findElement(By.xpath("//button[@id='button_submit_login_form']"));
        loginButton.click();
        alert = driver.switchTo().alert();
        System.out.println("wrong_no_login_credential_check: " + alert.getText().equals("Неверные данные для авторизации."));
        alert.accept();

        //Reset_Password_Check
        resetPasswordButton = driver.findElement(By.xpath("//tbody/tr[1]/td[1]/a[1]/div[1]"));
        resetPasswordButton.click();
        WebElement we = driver.findElement(By.xpath("//button[contains(text(),'Отправить')]"));
        System.out.println(we.getText());
    }
}
