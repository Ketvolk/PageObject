package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountAdd = $("[data-test-id=amount] input");
    private SelenideElement fromAdd = $("[data-test-id=from] input");
    private SelenideElement toAdd = $("[data-test-id=to] input");
    private SelenideElement addButton = $("[data-test-id=action-transfer]");
    private  SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public void transfer(DataHelper.TransferInfo transferInfo, int amount) {
        amountAdd.setValue(String.valueOf(amount));
        fromAdd.setValue(transferInfo.getFrom());
        addButton.click();
    }

    public void  amountError(String notificationText){
        errorNotification.shouldBe(visible).shouldHave(exactText(notificationText));
    }
}
