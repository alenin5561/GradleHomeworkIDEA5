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
    void testForm() {
        $("[placeholder='Город']").setValue(generateCity("ru"));
        String planningDate = generateDate(4);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue(generateName("ru"));
        $("[name='phone']").setValue(generatePhone("ru"));
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Успешно!')]").should(Condition.visible, Duration.ofSeconds(2));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate), Duration.ofSeconds(2))
                .shouldBe(Condition.visible);
        String planningDate2 = generateDate(8);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(planningDate2);
        $(".button__content").click();
        $x("//*[contains(text(),'Необходимо подтверждение')]").should(Condition.visible, Duration.ofSeconds(2));
        $(".button__content").click();
        $x("//*[contains(text(),'Успешно!')]").should(Condition.visible, Duration.ofSeconds(2));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate2), Duration.ofSeconds(2))
                .shouldBe(Condition.visible);

    }
}

