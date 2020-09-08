package com.ue.demo;

import com.ue.entity.Article;
import com.ue.util.DateUtil;
import com.ue.util.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度云链接内部数据获取
 * 链接地址：https://pan.baidu.com/s/1rVgdoLTqsfz9mXZlUqYjSg
 * 提取密码：lt5k
 * @author LiJun
 * @Date 2020年09月03日
 * @Time 18:43
 */
public class Demo3 {
    public static void main(String[] args) {
        String shareUrl = "https://pan.baidu.com/s/1rVgdoLTqsfz9mXZlUqYjSg";
        String password = "lt5k";
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");

        //排除掉图片的加载，提升性能
        Map<String, Object> preferences = new HashMap<String, Object>();
        ChromeOptions options = new ChromeOptions();
        preferences.put("profile.managed_default_content_settings.images", 2);
        options.setExperimentalOption("prefs", preferences);
        WebDriver driver = new ChromeDriver(options);
        driver.get(shareUrl);

        //超时等待
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = d.findElement(By.tagName("body")).isDisplayed();
                return loadcomplete;
            }
        });

        boolean hasPassword = false;//是否有密码
        String title = driver.findElement(By.cssSelector(".pickpw.clearfix")).getText();
        if (StringUtil.isNotEmpty(title) && title.contains("请输入提取码")) {
            hasPassword = true;
        }
        if (hasPassword) {
            WebElement pInput = driver.findElement(By.cssSelector(".QKKaIE.LxgeIt"));
            WebElement btn = driver.findElement(By.cssSelector(".g-button-right"));
            pInput.sendKeys(password);
            btn.click();
        }

        Article article = new Article();
        article.setShare_url(shareUrl);
        article.setPassword(password);
        try {
            genPageData(driver, article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(article);
//        driver.close();//浏览器关闭
//        driver.quit();//释放资源
    }

    /**
     * 生成基本数据
     */
    public static void genPageData(WebDriver driver, Article article) throws Exception {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebElement fileNameEle = driver.findElement(By.cssSelector(".filename"));
        article.setName(fileNameEle.getText());
        WebElement shareDateEle = driver.findElement(By.cssSelector(".share-file-info span"));
        article.setShare_date(shareDateEle.getText());
        WebElement shareUserEle = driver.findElement(By.cssSelector(".share-person-data-top a.share-person-username.global-ellipsis"));
        article.setShare_user(shareUserEle.getAttribute("textContent"));
        article.setContent(fileNameEle.getText());//预先设置
        article.setState(1);
        article.setInclude_date(DateUtil.getCurrentDateStr());
    }
}
