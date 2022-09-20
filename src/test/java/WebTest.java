import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static data.DataGenerator.*;

public class WebTest {
    WebDriver driver;

    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(4);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(7);
    }

    @Test
    void testForm() {
        $("[placeholder='Город']").setValue(generateCity("ru"));
        String planningDate = generateDate(4);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue(generateName("ru"));
        $("[name='phone']").setValue(generatePhone("ru"));
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Необходимо подтверждение')]").should(Condition.visible, Duration.ofSeconds(2));
        $(".notification__content")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(2))
                .shouldBe(Condition.visible);
        $("//span[text()='Перепланировать']").click();
        $x("//*[contains(text(),'Успешно!')]").should(Condition.visible, Duration.ofSeconds(2));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate), Duration.ofSeconds(2))
                .shouldBe(Condition.visible);
    }
}

