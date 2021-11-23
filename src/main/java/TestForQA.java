import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestForQA {
    static WebDriver driver;
    static WebElement login;
    static WebElement password;
    static WebElement loginButton;
    static WebElement resetPasswordButton;
    static Alert alert;
    static boolean isSuccess = true;
    static boolean isCorrect;

    private void isDisplayedAndEnable(String name, WebElement element) {
        boolean isDisplayed = element.isDisplayed();
        boolean isEnabled = element.isEnabled();

        System.out.println(name + "_displayed: " + isDisplayed);
        System.out.println(name + "_enabled: " + isEnabled);
        if (isSuccess) isSuccess = isDisplayed && isEnabled;
    }

    private boolean checkItAll() {
        //Start_Test
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        //Home_Site_QA_Login_Test
        driver.get("https://lmslite47vr.demo.mirapolis.ru/mira");
        if (!driver.getCurrentUrl().endsWith("type=customloginpage")) {
            System.out.println("Incorrect URL!");
            return false;
        }

        //Home_Button_Check
        try {
            WebElement homeButton = driver.findElement(By.xpath("//body/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/a[1]"));
            isDisplayedAndEnable("home_button", homeButton);
            homeButton.click();
            isCorrect = driver.getCurrentUrl().endsWith("type=customloginpage");
            System.out.println("home_button_work: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
        } catch (WebDriverException wde) {
            System.out.println("home_button_not_found");
            isSuccess = false;
        }

        //Main_Login_Window_Check
        try {
            WebElement mainLoginText = driver.findElement(By.xpath("//div[contains(text(),'Вход в систему')]"));
            isDisplayedAndEnable("main_login_text", mainLoginText);
            isCorrect = mainLoginText.getText().equals("Вход в систему");
            System.out.println("main_login_text_exist: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
        } catch (WebDriverException wde) {
            System.out.println("main_login_text_not_found");
            isSuccess = false;
        }

        //Login_Field_Check
        try {
            login = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
            isDisplayedAndEnable("login_field", login);
            isCorrect = login.getAttribute("placeholder").equals("Введите ваш логин");
            System.out.println("login_field_exist: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
        } catch (WebDriverException wde) {
            System.out.println("login_field_not_found");
            isSuccess = false;
        }

        //Password_Field_Check
        try {
            password = driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[1]/input[1]"));
            isDisplayedAndEnable("password_field", password);
            isCorrect = password.getAttribute("placeholder").equals("Введите ваш пароль");
            System.out.println("password_field_exist: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
        } catch (WebDriverException wde) {
            System.out.println("password_field_not_found");
            isSuccess = false;
        }

        //Password_Visible_Button_Check
        try {
            WebElement passwordVisible = driver.findElement(By.xpath("//button[@id='show_password']"));
            isDisplayedAndEnable("password_visible_button", passwordVisible);
            password.sendKeys("1P73BP4Z");
            isCorrect = password.getAttribute("type").equals("password");
            System.out.println("password_type_check: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
            passwordVisible.click();
            isCorrect = password.getAttribute("type").equals("text");
            System.out.println("password_text_type_check: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
            password.clear();
        } catch (WebDriverException wde) {
            System.out.println("password_type_button_not_found");
            isSuccess = false;
        }

        //Login_Button_Check
        try {
            loginButton = driver.findElement(By.xpath("//button[@id='button_submit_login_form']"));
            isDisplayedAndEnable("login_button", loginButton);
            isCorrect = loginButton.getText().equals("Войти");
            System.out.println("login_button_exist: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
        } catch (WebDriverException wde) {
            System.out.println("login_button_not_found");
            isSuccess = false;
        }

        //Reset_Password_Check
        try {
            resetPasswordButton = driver.findElement(By.xpath("//tbody/tr[1]/td[1]/a[1]/div[1]"));
            isDisplayedAndEnable("reset_password_button", resetPasswordButton);
            isCorrect = resetPasswordButton.getText().equals("Забыли пароль?");
            System.out.println("reset_password_button_exist: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
        } catch (WebDriverException wde) {
            System.out.println("reset_password_button_not_found");
            isSuccess = false;
        }

        //Happy_Path_Check
        try {
            login.sendKeys("fominaelena");
            password.sendKeys("1P73BP4Z");
            loginButton.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isCorrect = driver.findElement(By.xpath("//span[contains(text(),'Главная страница')]")).getText().equals("Главная страница");
            System.out.println("login_success: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
            driver.findElement(By.xpath("//tbody/tr[1]/td[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]")).click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//span[contains(text(),'Выйти')]")).click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (WebDriverException wde) {
            System.out.println("happy_path_failed!");
            System.out.println(wde.getMessage());
            isSuccess = false;
        }

        //Bad_Path_All_Credential_Check
        try {
            login = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
            login.sendKeys("Az04!~#$%^&*()_+");
            password = driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[1]/input[1]"));
            password.sendKeys("Az04!~#$%^&*()_+");
            loginButton = driver.findElement(By.xpath("//button[@id='button_submit_login_form']"));
            loginButton.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            alert = driver.switchTo().alert();
            isCorrect = alert.getText().equals("Неверные данные для авторизации");
            System.out.println("wrong_all_credential_check: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
            alert.accept();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (WebDriverException wde) {
            System.out.println("bad_path_failed!");
            System.out.println(wde.getMessage());
            isSuccess = false;
        }

        //Bad_Path_Without_Credential_Check
        try {
            loginButton = driver.findElement(By.xpath("//button[@id='button_submit_login_form']"));
            loginButton.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            alert = driver.switchTo().alert();
            isCorrect = alert.getText().equals("Неверные данные для авторизации.");
            System.out.println("wrong_without_credential_check: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
            alert.accept();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (WebDriverException wde) {
            System.out.println("bad_path_failed!");
            System.out.println(wde.getMessage());
            isSuccess = false;
        }

        //Bad_Path_No_Password_Credential_Check
        try {
            login = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
            login.sendKeys("Az04!~#$%^&*()_+");
            loginButton = driver.findElement(By.xpath("//button[@id='button_submit_login_form']"));
            loginButton.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            alert = driver.switchTo().alert();
            isCorrect = alert.getText().equals("Неверные данные для авторизации.");
            System.out.println("wrong_no_password_credential_check: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
            alert.accept();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (WebDriverException wde) {
            System.out.println("bad_path_failed!");
            System.out.println(wde.getMessage());
            isSuccess = false;
        }

        //Bad_Path_No_Login_Credential_Check
        try {
            password = driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[1]/input[1]"));
            password.sendKeys("Az04!~#$%^&*()_+");
            loginButton = driver.findElement(By.xpath("//button[@id='button_submit_login_form']"));
            loginButton.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            alert = driver.switchTo().alert();
            isCorrect = alert.getText().equals("Неверные данные для авторизации.");
            System.out.println("wrong_no_login_credential_check: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
            alert.accept();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (WebDriverException wde) {
            System.out.println("bad_path_failed!");
            System.out.println(wde.getMessage());
            isSuccess = false;
        }

        //Reset_Password_Check
        try {
            resetPasswordButton = driver.findElement(By.xpath("//tbody/tr[1]/td[1]/a[1]/div[1]"));
            resetPasswordButton.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement loginOrEmailField = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
            isDisplayedAndEnable("login_or_email", loginOrEmailField);
            isCorrect = loginOrEmailField.getAttribute("placeholder").equals("Введите логин или email");
            System.out.println("login_or_email_exist: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
            WebElement sendButton = driver.findElement(By.xpath("//button[contains(text(),'Отправить')]"));
            isDisplayedAndEnable("send_button", sendButton);
            isCorrect = sendButton.getText().equals("Отправить");
            System.out.println("send_button_exist: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
            WebElement backToHome = driver.findElement(By.xpath("//div[contains(text(),'Назад к входу в систему')]"));
            isDisplayedAndEnable("back_home", backToHome);
            isCorrect = backToHome.getText().equals("Назад к входу в систему");
            System.out.println("back_home_button_exist: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
            backToHome.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isCorrect = driver.getCurrentUrl().endsWith("type=customloginpage");
            System.out.println("back_home_button_work: " + isCorrect);
            if (isSuccess) isSuccess = isCorrect;
        } catch (WebDriverException wde) {
            System.out.println("reset_password_failed!");
            System.out.println(wde.getMessage());
            isSuccess = false;
        }

        return isSuccess;
    }

    public static void main(String[] args) {
        if (new TestForQA().checkItAll()) {
            System.out.println("Test_Success");
        } else System.out.println("Test_Fail");
    }
}