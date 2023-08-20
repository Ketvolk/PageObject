package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.TransferPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    @BeforeEach
    void prepare() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(1);
        var secondCardBalance = dashboardPage.getCardBalance(2);
        dashboardPage.cardToTransferClick(2);

        var transferPage = new TransferPage();
        var transferInfo = DataHelper.getTransferInfo();
        int amount = 200;
        transferPage.transfer(transferInfo, amount);

        Assertions.assertEquals(firstCardBalance - amount, dashboardPage.getCardBalance(1));
        Assertions.assertEquals(secondCardBalance + amount, dashboardPage.getCardBalance(2));
    }

    @Test
    void shouldNotTransfersIfBalanceIsNotEnough() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(1);
        var secondCardBalance = dashboardPage.getCardBalance(2);
        dashboardPage.cardToTransferClick(2);

        var transferPage = new TransferPage();
        var transferInfo = DataHelper.getTransferInfo();
        int amount = 15_000;
        transferPage.transfer(transferInfo, amount);

        Assertions.assertEquals(firstCardBalance, dashboardPage.getCardBalance(1));
        Assertions.assertEquals(secondCardBalance, dashboardPage.getCardBalance(2));
    }
}
