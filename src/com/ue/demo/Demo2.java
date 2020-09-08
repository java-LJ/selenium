package com.ue.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 使用QQ登录百度云，新建一个文件夹，随后再将其删除
 * @author LiJun
 * @Date 2020年09月03日
 * @Time 18:52
 */
public class Demo2 {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(3000);
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //浏览器全屏
        driver.manage().window().maximize();
//        driver.manage().window().setPosition(new Point(100, 50));
        driver.manage().deleteAllCookies();
        //与浏览器同步非常重要，必须等待浏览器加载完毕
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://pan.baidu.com/");
        Thread.sleep(1000);

        //找到“QQ账号”登录按钮
        WebElement qqLoginLink = driver
                .findElement(By.xpath("//*[@id=\"pass_phoenix_btn\"]/ul/li[2]/a"));
        qqLoginLink.click();
        Thread.sleep(1000);

        //获取当前页面句柄
        String handle = driver.getWindowHandle();
        //获取所有页面的句柄，并循环判断不是当前的句柄 然后切换到子窗体
        for (String handles : driver.getWindowHandles()) {
            if (handles.equals(handle))
                continue;
            driver.switchTo().window(handles);
        }

        //由于登录输入框在frame中，需要先切换进入frame，否则找不到输入框的
        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='ptlogin_iframe']")));

        //调试过程中，如果提示找不到元素，不知道是否切换成功了，可以把当前handler的source打印出来看看
//        System.out.println(driver.getPageSource());

        driver.findElement(By.xpath("//*[@id='switcher_plogin']")).click();
        driver.findElement(By.xpath("//*[@id='u']")).sendKeys("1457527234");
        driver.findElement(By.xpath("//*[@id='p']")).sendKeys("qwer1314POIU");
        driver.findElement(By.xpath("//*[@id='login_button']")).click();

        //如果直接关闭，可能被判断为还没完成登录，没有会话，所以稍等片刻
        Thread.sleep(5000);

        //定位到当前最新页面
        Set<String> windows = driver.getWindowHandles();
        driver.switchTo().window((String) windows.toArray()[windows.size() - 1]);

        Thread.sleep(2000);
        //登录后会有一个遮罩层提示，需要先将其关掉
        WebElement kownBtn = driver.findElement(By.xpath("//*[@id=\"dialog1\"]/div[2]/div/div[2]"));
        kownBtn.click();

        Thread.sleep(2000);
        //找到“新建文件夹”按钮
        WebElement newDiskBtm = driver.findElement(By.xpath("//*[@id=\"layoutMain\"]/div[1]/div[2]/div[4]/div[2]/a[1]"));
        newDiskBtm.click();

        Thread.sleep(2000);
        //填入文件夹名称，然后确认新建，完成文件夹新建
        WebElement diskNameInput = driver.findElement(By.xpath("//*[@id=\"layoutMain\"]/div[2]/div[6]/div/input"));
        diskNameInput.sendKeys("我是自动新建的文件夹");
        driver.findElement(By.xpath("//*[@id=\"layoutMain\"]/div[2]/div[6]/div/span[1]")).click();

        Thread.sleep(5000);
        //找到刚刚新建的那个文件夹
        WebElement myAutoDisk = driver.findElement(By.partialLinkText("自动新建"));
        Actions actions = new Actions(driver);
        //鼠标右键单击文件夹
        actions.contextClick(myAutoDisk).perform();

        //在点击右键后弹出来的菜单里找到“删除”，然后点击
        List<WebElement> lis = driver.findElements(By.tagName("li"));
        for (WebElement li : lis) {
            if ("删除".equals(li.getText()))
                li.click();
        }
        Thread.sleep(5000);
        //点击确认框里的“确定”按钮，完成文件夹删除
        driver.findElement(By.linkText("确定")).click();

//        driver.quit();
    }
}
