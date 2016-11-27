package com.chato.dawanda;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class daWandaReg_Login {
/*        WebDriver fd;
    I could not define wd as global,
    because I got really strong exceptions that I could not find any solutions in stackOverFlow or etc.)

    Because of this issue, I could not use @Before, @Test, or @After annotations well.
    Here is the exception that I faced with:
    http://stackoverflow.com/questions/40462355/whether-selenium-standalone-server-2-50-not-support-in-fire-fox-browser-49-0-ver*/

    private FirefoxProfile fP = new FirefoxProfile();
    private WebDriverWait wait;

    private String name;
    private String surName;
    private String userName;
    private String password;
    private String userID;
    private String eMail;

    private String baseURL;
    private String[] nation = new String[]{"de", "en", "fr", "pl", "es", "nl", "it"};

    public String getName() {
        this.name = "Johnny";
        return this.name;
    }

    public String getSurName() {
        this.surName = "Wilkins";
        return this.surName;
    }

    public String getPassword() {
        this.password = "Ck1234,";
        return this.password;
    }

    public String getEngBaseURL() {
        this.baseURL = "http://" + nation[1] + ".dawanda.com";
        return this.baseURL;
    }

    public void setUserID() {
        this.userID = String.valueOf(Math.random() * ((Math.pow(10, 10)) - 1) + (Math.pow(10, 9)));
    }

    public String getUserID() {
        return this.userID;
    }


    public String getUserName() {
        this.userName = "frontend-test-user-" + this.getUserID();
        return this.userName;
    }

    public String geteMail() {
        this.eMail = "frontend-tests+-" + this.getUserID() + "@dawandamail.com";
        return this.eMail;
    }

    public void firefoxOptions() {
        // Downloaded "geckodriver.exe" from https://github.com/mozilla/geckodriver/releases
        System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver.exe");
        fP.setEnableNativeEvents(true);
    }

    @Before
    public void setUp() throws Exception {
        this.firefoxOptions();
        this.setUserID();

    }

    @Test
    public void daWandaReg_LoginEng() throws Exception {
        try {
            //1. Open a Firefox browser (the browser is already installed, so you can use the selenium native Firefox driver)
            WebDriver fd = new FirefoxDriver();
            Actions action1 = new Actions(fd);
            fd.manage().window().maximize();
            fd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

            //2. Navigate to „dawanda.com“ (take care for different languages)
            fd.navigate().to(this.getEngBaseURL());
            WebElement hoverMenu = fd.findElement(By.cssSelector(".header-user-toggle"));
            WebElement buttonLoginHP = fd.findElement(By.cssSelector("#shared_header_user_dropdown > ul:nth-child(1) > li:nth-child(1) > a:nth-child(1)"));
            WebElement buttonRegisterHP = fd.findElement(By.cssSelector("#shared_header_user_dropdown > ul:nth-child(1) > li:nth-child(2) > a:nth-child(1)"));

            //3. Click register and
            action1.moveToElement(hoverMenu).perform();
            wait.until(ExpectedConditions.visibilityOf(buttonRegisterHP));
            action1.moveToElement(hoverMenu).moveToElement(buttonRegisterHP).click().perform();
            wait = new WebDriverWait(fd, 2);

            assertTrue(getEngBaseURL() + "/account/register" == fd.getCurrentUrl());

            //register a user (use this format: username: frontend-test-user-1032090980
            //eMail address: frontend-tests+-1032090980@dawandamail.com) - the number is randomized
            WebElement userNameWE = fd.findElement(By.id("username"));
            WebElement passWordWE = fd.findElement(By.id("password"));
            fd.findElement(By.id("firstname")).clear();
            fd.findElement(By.id("firstname")).sendKeys(this.getName());
            fd.findElement(By.id("lastname")).clear();
            fd.findElement(By.id("lastname")).sendKeys(this.getSurName());
            userNameWE.clear();
            userNameWE.sendKeys(this.getUserName());
            fd.findElement(By.id("email")).clear();
            fd.findElement(By.id("email")).sendKeys(this.geteMail());
            passWordWE.clear();
            passWordWE.sendKeys(this.getPassword());
            if (!fd.findElement(By.id("accept_privacy")).isSelected()) {
                fd.findElement(By.id("accept_privacy")).click();
            }
            fd.findElement(By.id("register_submit")).click();
            wait = new WebDriverWait(fd, 2);

            //4. After successful registering,
            assertEquals(this.getEngBaseURL() + "/account/validate", fd.getCurrentUrl());

            //check the message that the user has to validate his account and
            assertTrue(fd.findElement(By.id("validate_email_hint")).getText().contains(this.geteMail()));

            //logout
            WebElement buttonLogOut = fd.findElement(By.cssSelector(".logout-link"));
            action1.moveToElement(hoverMenu).perform();
            wait.until(ExpectedConditions.visibilityOf(buttonLogOut));
            action1.moveToElement(hoverMenu).moveToElement(buttonLogOut).click().perform();
            wait = new WebDriverWait(fd, 2);

            assertEquals(this.getEngBaseURL() + "/account/logout", fd.getCurrentUrl());

            //5. Open the start page again
            fd.get(this.getEngBaseURL());
            wait = new WebDriverWait(fd, 2);

            //6. Login the user
            action1.moveToElement(hoverMenu).perform();
            wait.until(ExpectedConditions.visibilityOf(buttonLoginHP));
            action1.moveToElement(hoverMenu).moveToElement(buttonLoginHP).click().perform();
            wait = new WebDriverWait(fd, 2);

            assertTrue(getEngBaseURL() + "/account/login" == fd.getCurrentUrl());

            userNameWE.clear();
            userNameWE.sendKeys(this.getUserName());
            passWordWE.clear();
            passWordWE.sendKeys(this.getPassword());

            WebElement buttonLoginLP = fd.findElement(By.id("login_submit"));
            buttonLoginLP.click();
            wait = new WebDriverWait(fd, 2);

            assertTrue(getEngBaseURL() + "/mydawanda" == fd.getCurrentUrl());

            //7. Logout the user
            action1.moveToElement(hoverMenu).perform();
            wait.until(ExpectedConditions.visibilityOf(buttonLogOut));
            action1.moveToElement(hoverMenu).moveToElement(buttonLogOut).click().perform();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


/*    @After
    public void tearDown() {
        fd.quit();
    }*/
}
