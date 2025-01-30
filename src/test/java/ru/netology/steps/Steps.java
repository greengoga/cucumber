package ru.netology.steps;

import com.codeborne.selenide.Selenide;

import io.cucumber.java.ru.*;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import ru.netology.page.VerificationPage;
import ru.netology.data.DataHelper;

import static org.junit.Assert.assertEquals;

public class Steps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static TransferPage transferPage;

    @Пусть("открыта страница с формой авторизации {}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPage.class);
    }

    @Когда("пользователь пытается авторизоваться с именем {} и паролем {}")
    public void loginWithNameAndPassword(String login, String password) {
        verificationPage = loginPage.validLogin(login, password);
    }

    @И("пользователь вводит проверочный код {int}")
    public void setValidCode(int verificationCode) {
        dashboardPage = verificationPage.validVerify(String.valueOf(verificationCode));
    }

    @Тогда("происходит успешная авторизация и пользователь попадает на страницу 'Личный кабинет'")
    public void verifyDashboardPage() {
        dashboardPage.verifyIsDashboardPage();
    }

    @Тогда("появляется ошибка о неверном коде проверки")
    public void verifyCodeIsInvalid() {
        verificationPage.verifyCodeIsInvalid();
    }

    @Тогда("пользователь переводит {int} рублей с карты {string} на свою 1-ю карту")
    public void transferCreditsFromSecondToFirst(int amount, String fromCardNumber) {
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();

        int balanceBefore = dashboardPage.getCardBalance(firstCard.getId());

        transferPage = dashboardPage.clickDepositCard(firstCard.getId());
        transferPage.makeTransfer(String.valueOf(amount), fromCardNumber);

        int balanceAfter = dashboardPage.getCardBalance(firstCard.getId());

        assertEquals(balanceBefore + amount, balanceAfter);
    }

    @Тогда("баланс его 1-й карты из списка на главной странице должен стать {int} рублей")
    public void firstCardBalanceShouldBecome(int expectedBalance) {
        var firstCard = DataHelper.getFirstCardInfo();
        int actualBalance = dashboardPage.getCardBalance(firstCard.getId());
        assertEquals(expectedBalance, actualBalance);
    }
}
