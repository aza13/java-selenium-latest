package pageObjects;
import org.openqa.selenium.By;
public class InsuredPageObjects {

    private InsuredPageObjects(){

    }

    public static final By newInsuredButton = By.xpath("//button[text()=' New Insured']");
    public static final By modifySearchButton = By.xpath("//button[text()='Modify Search']");
    public static final By selectInsuredButton = By.xpath("//button[text()='Select']");
    public static final By cancelButton = By.xpath("//button[text()='Cancel']");
    public static final By continueButton = By.xpath("//button[text()='Continue']");
}
