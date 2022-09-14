package tests;

import org.junit.Test;
import org.openqa.selenium.By;
import utils.DataUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DownloadTest {

    @Test
    public void itShouldDownloadBussinesRules() throws IOException {
        open("https...");

        File file = $(byText("element text")).download();
        DataUtils.printPDF(file);
    }
}
