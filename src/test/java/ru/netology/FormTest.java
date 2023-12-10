package ru.netology;

import com.codeborne.selenide.ElementsCollection;
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
        ElementsCollection inputs = form.$$(".input__control");
        inputs.findBy(attribute("placeholder", "Город")).setValue("Казань");

        LocalDateTime date = LocalDateTime.now().plusDays(4);
        SelenideElement dateInput = inputs.findBy(attribute("placeholder", "Дата встречи"));
        dateInput.sendKeys(Keys.CONTROL + "A");
        dateInput.sendKeys(Keys.BACK_SPACE);
        dateInput.setValue(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        inputs.findBy(attribute("name", "name")).setValue("Иванов Иван");
        inputs.findBy(attribute("name", "phone")).setValue("+71234567890");
        form.$(".checkbox__box").click();
        form.$(".button").click();

        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
    }

    @Test
    public void shouldSubmitTheFormWithAdditionalElements() throws InterruptedException {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        ElementsCollection inputs = form.$$(".input__control");

        inputs.findBy(attribute("placeholder", "Город")).setValue("Ка");
        $$(".menu-item__control").findBy(exactText("Казань")).click();

        inputs.findBy(attribute("placeholder", "Дата встречи")).click();
        $$(".calendar__arrow_direction_right").last().click();
        $$(".calendar__day").findBy(exactText("5")).click();

        inputs.findBy(attribute("name", "name")).setValue("Иванов Иван");
        inputs.findBy(attribute("name", "phone")).setValue("+71234567890");
        form.$(".checkbox__box").click();
        form.$(".button").click();

        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
    }

}
