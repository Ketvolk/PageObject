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
    private  SelenideElement valueError = $("[data-test-id=error-notification]");

    public TransferPage transfer(DataHelper.AddInfo addInfo, int amount) {
        DashboardPage dashboardPage = new DashboardPage();
        amountAdd.setValue(String.valueOf(amount));
        fromAdd.setValue(addInfo.getFrom());
        addButton.click();
        if (amount > dashboardPage.getCardBalance(DataHelper.getFirstCard())) {
            valueError.shouldHave(text("Сумма пополнения не должна превышать баланс карты для списания"));
        }
        return new TransferPage();
    }
}
