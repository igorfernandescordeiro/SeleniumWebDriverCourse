package Suporte;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Web {
    public static final String USERNAME = "igorfernandescor1";
    public static final String AUTOMATE_KEY = "KRKpydRASyxXxhvJ5AYR";
    public static final String SITE = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static WebDriver createChrome(){
        // Abrindo o navegador
        System.setProperty("webdriver.chrome.driver","C:\\Users\\ifcig\\Driver\\chromedriver.exe");
        WebDriver navegador = new ChromeDriver();
        // Maximizando o navegador
        navegador.manage().window().maximize();
        // Tempo de espera, com isso ele encontra o elemento class me
        navegador.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Navegando para a página do Taskit!
        navegador.get("http://www.juliodelima.com.br/taskit");

        return navegador;
    }
    public static WebDriver createBrowserStack(){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "76.0 beta");
        caps.setCapability("os", "OS X");
        caps.setCapability("os_version", "Mojave");
        caps.setCapability("resolution", "1920x1080");
        caps.setCapability("browserstack.debug","true");

        WebDriver navegador = null;
        try {
            navegador = new RemoteWebDriver(new URL(SITE), caps);
            navegador.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            // Navegando para a página do Taskit!
            navegador.get("http://www.juliodelima.com.br/taskit");
        } catch (MalformedURLException e) {
            System.out.println("Houveram problemas com a URL: " + e.getMessage());
        }

        return navegador;
    }
}
