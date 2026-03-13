package tests;

import base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExcelUtils;

public class LoginTest2 extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {

        String path = "src/test/resources/testData/LoginData.xlsx";
        String sheetName = "Sheet2";

        ExcelUtils.setExcelFile(path, sheetName);

        int rows = ExcelUtils.getRowCount();
        int cols = ExcelUtils.getColCount();

        Object[][] data = new Object[rows-1][cols]; // exclude header

        for(int i=1; i<rows; i++){
            for(int j=0; j<cols; j++){
                data[i-1][j] = ExcelUtils.getCellData(i,j);
            }
        }

        return data;
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password){

        LoginPage loginPage = new LoginPage();

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

}