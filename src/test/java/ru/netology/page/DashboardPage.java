//package ru.netology.page;
//
//import com.codeborne.selenide.Condition;
//import com.codeborne.selenide.ElementsCollection;
//import com.codeborne.selenide.Selenide;
//import com.codeborne.selenide.SelenideElement;
//import lombok.val;
//
//import static com.codeborne.selenide.Selenide.$;
//import static com.codeborne.selenide.Selenide.$$;
//
//public class DashboardPage {
//    private final SelenideElement heading = $("[data-test-id=dashboard]");
//
//    public void verifyIsDashboardPage() {
//        heading.shouldBe(Condition.visible);
//    }
//}

package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");

    public void verifyIsDashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public int getCardBalance(String cardId) {
        SelenideElement cardElement = findCardById(cardId);
        return extractBalance(cardElement.text());
    }

    private SelenideElement findCardById(String cardId) {
        return cards.findBy(Condition.attribute("data-test-id", cardId));
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish).trim();
        return Integer.parseInt(value);
    }

    public TransferPage clickDepositCard(String cardId) {
        findCardById(cardId).$("[data-test-id='action-deposit']").click();
        return new TransferPage();
    }
}