package com.ue.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 打开百度首页并模拟搜索
 * @author LiJun
 * @Date 2020年09月03日
 * @Time 16:30
 */
public class Demo1 {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        String url = "http://www.baidu.com";
        driver.get(url);//打开指定的网站
//        driver.navigate().to(url);//打开指定的网站

        //找到搜索框
        WebElement input = driver.findElement(By.xpath("//*[@id=\"kw\"]"));
        //往搜索框填入“selenium”
        input.sendKeys("selenium");
        //找到“百度一下”按钮
        WebElement button = driver.findElement(By.xpath("//*[@id=\"su\"]"));
        //点击按钮
        button.click();

//        driver.close();//浏览器关闭
//        driver.quit();//释放资源
    }
}
