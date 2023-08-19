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
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        dashboardPage.fromFirstToSecondTransfer(DataHelper.getSecondCard());

        var transferPage = new TransferPage();
        var addInfo = DataHelper.getAddInfo();
        int amount = 200;
        transferPage.transfer(addInfo, amount);

        Assertions.assertEquals(firstCardBalance - amount, dashboardPage.getCardBalance(firstCard));
        Assertions.assertEquals(secondCardBalance + amount, dashboardPage.getCardBalance(secondCard));
    }

    @Test
    void shouldNotTransfersIfBalanceIsNotEnough() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        dashboardPage.fromFirstToSecondTransfer(DataHelper.getSecondCard());

        var transferPage = new TransferPage();
        var addInfo = DataHelper.getAddInfo();
        int amount = 15_000;
        transferPage.transfer(addInfo, amount);

        Assertions.assertEquals(dashboardPage.getCardBalance(firstCard), firstCardBalance);
        Assertions.assertEquals(dashboardPage.getCardBalance(secondCard), secondCardBalance);
    }
}
