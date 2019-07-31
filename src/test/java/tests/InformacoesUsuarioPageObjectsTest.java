package tests;

import Suporte.Web;
import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;

import static org.junit.Assert.assertEquals;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioPageObjectsTest.csv")
public class InformacoesUsuarioPageObjectsTest {
    private WebDriver navegador;

    @Before
    public void setUp(){
       navegador =  Web.createChrome();
    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(
            @Param(name="login")String login,
            @Param(name="password")String password,
            @Param(name="type")String type,
            @Param(name="contact")String contact,
            @Param(name="message")String message
    ){
        String toastText = new LoginPage(navegador)
                .clickSignIn()
                .makeLogin(login,password)
                .clickMe()
                .clickTabMoreDataAboutYou()
                .clickButtonMoreDataAboutYou()
                .addContact(type,contact)
                .captureToastText();

        assertEquals(message,toastText);
    }

    @After
    public void tearDown(){
        navegador.quit();
    }
}
