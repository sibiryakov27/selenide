package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class FormTest {

    @Test
    public void shouldSubmitTheForm() throws InterruptedException {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Казань");

        LocalDateTime date = LocalDateTime.now().plusDays(4);
        SelenideElement dateInput =  form.$("[data-test-id=date] input");
        dateInput.sendKeys(Keys.CONTROL + "A");
        dateInput.sendKeys(Keys.BACK_SPACE);
        dateInput.setValue(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    }

    @Test
    public void shouldSubmitTheFormWithAdditionalElements() throws InterruptedException {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id=city] input").setValue("Ка");
        $$(".menu-item__control").findBy(exactText("Казань")).click();

        LocalDateTime date = LocalDateTime.now().plusDays(7);
        form.$("[data-test-id=date] input").click();
        if (!date.getMonth().equals(LocalDateTime.now().getMonth())) {
            $(".calendar__arrow_direction_right[data-step=\"1\"]").click();
        }
        $$(".calendar__day").findBy(exactText(String.valueOf(date.getDayOfMonth()))).click();

        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    }

}
