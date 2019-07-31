package tests;

import static org.junit.Assert.*;

import Suporte.Generator;
import Suporte.Screenshot;
import Suporte.Web;
import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTest.csv")
public class InformacoesUsuarioTest {
    private WebDriver navegador;
    private WebDriverWait wait;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp(){
        navegador = Web.createChrome();

        wait = new WebDriverWait(navegador, 10);
        // Clicar no link que possui o texto "Sign in"
        navegador.findElement(By.linkText("Sign in")).click();

        // Identificando o formulário de Login
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        // Digitar no campo com name "login" que está dentro do formulário de id "signinbox" o texto "julio0001"
        formularioSignInBox.findElement(By.name("login")).sendKeys("julio0001");

        // Digitar no campo com name "password" que está dentro do formulário de id "signinbox" o texto "123456"
        formularioSignInBox.findElement(By.name("password")).sendKeys("123456");

        // Clicar no link com o texto "SIGN IN"
        WebElement linkTextoSignIn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//body//div[@id='signinbox']//a")));
        linkTextoSignIn.click();

        //navegador.findElement(By.xpath("//body//div[@id='signinbox']//a")).click();



        // Clicar em um link que possui a class me
        //WebElement linkClassMe = navegador.findElement(By.xpath("//div//ul[1]//a[text()='Hi, Julio']"));
        WebElement linkClassMe = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div//ul[1]//a[text()='Hi, Julio']")));
        linkClassMe.click();

        // Clicar no link com texto More data about you
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(@Param(name="tipo")String tipo, @Param(name="contato")String contato, @Param(name="mensagem")String mensagemEsperada){

        // Clicar no botao  + ADD MORE DATA usando XPath //div[@id='moredata']/div/button
        navegador.findElement(By.xpath("//div[@id='moredata']/div/button")).click();

        // Identificar a pop up onde está o formulario de id addmoredata - PRIMEIRO como eu fiz e depois o curso
                WebElement formulario = navegador.findElement(By.id("addmoredata"));
                WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        // Na combo de name "type" escolher a opção "Phone" - PRIMEIRO como eu fiz e depois o curso
        // Combo Box se trata sempre com a class Select, é tipo uma caso "especial"
                Select opcao = new Select(formulario.findElement(By.name("type")));
                opcao.selectByValue(tipo);
                //WebElement campoType = popupAddMoreData.findElement(By.name("type"));
               // new Select(campoType).selectByVisibleText("Phone");

        // No campo de name "contact" digitar "+5571999990930"
        formulario.findElement(By.name("contact")).sendKeys(contato);

        String nameScreenShot = "C:\\Users\\ifcig\\OneDrive\\Documentos\\CURSOS\\Selenium Julio de Lima\\test-report\\"+ Generator.dataHoraParaArquivo() + test.getMethodName() + ".png";
        Screenshot.tirar(navegador, nameScreenShot);

        // Clicar no link de text "Save" que está na pop up
        formulario.findElement(By.linkText("SAVE")).click();
            // formulario.findElement(By.xpath("//div[@id='addmoredata']/div[2]/a")).click();

        // Na mensagem de id"toast-container" validar que o texto é "Your contact has been added!"

        WebElement toastParaValidar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
                //navegador.findElement(By.id("toast-container"));
        //System.out.println("Foi encontrado esse texto no toast: " + (toastParaValidar.getText()));
        assertEquals(mensagemEsperada, toastParaValidar.getText());
    }

    @Test
    public void removerUmContatoDeUmUsuario(){

        // +5571999990930 Clicar no botão excluir pelo seu xpath que é //div[@class = 'container']//div[@id = 'moredata']//li[2]/a
        navegador.findElement(By.xpath("//span[text()='+5571999990930']/following-sibling::a")).click();
        // Confirmar na janela javascript
        navegador.switchTo().alert().accept();
        // Validar que a mensagem apresentada foi Rest in peace, dear phone!
        WebElement mensagemPop = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));       //navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!", mensagem);
        // Tirar ScreenShot
        String nameScreenShot = "C:\\Users\\ifcig\\OneDrive\\Documentos\\CURSOS\\Selenium Julio de Lima\\test-report\\"+ Generator.dataHoraParaArquivo() + test.getMethodName() + ".png";
        Screenshot.tirar(navegador, nameScreenShot);

        // Aguardar  até 10 segundos para que a janela desapareça

        wait.until(ExpectedConditions.stalenessOf(mensagemPop));
        // Clicar no link com texto Logout
        navegador.findElement(By.linkText("Logout")).click();
    }


    @After
    public void tearDown(){
        // Fechar o navegador
       navegador.quit();
    }
}
