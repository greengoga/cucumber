package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;


public class VerificationPage {
    private final SelenideElement codeInput = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorPopup = $(".notification__content");

    public VerificationPage() {
        codeInput.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        codeInput.setValue(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }

    public void verifyCodeIsInvalid() {
        errorPopup.shouldHave(text("Ошибка!"));
    }
}