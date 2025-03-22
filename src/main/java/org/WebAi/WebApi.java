package org.WebAi;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.Game.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebApi {
    ConfigLoader config = new ConfigLoader();

    private final Playwright playwright;
    public final Page page;

    public final String username = config.getUsername();


    public WebApi() { //РАБОТАЕТ
        playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)//True чтоб в скрытом режиме был
                //.setArgs(Arrays.asList("--window-position=-10000,-10000")) // Окно за пределами экрана
        );



        page = browser.newPage();
        initializeGame();
    }


    public void newGame() {
        page.waitForSelector("button:has-text('Покинуть комнату')", new Page.WaitForSelectorOptions().setTimeout(50000));
        page.click("button:has-text('Покинуть комнату')");
        page.waitForSelector("button:has-text('Играть онлайн')", new Page.WaitForSelectorOptions().setTimeout(50000));
        page.waitForTimeout(500);
        page.click("button:has-text('Играть онлайн')");
        page.waitForTimeout(500);

    }


    private void initializeGame() { // РАБОТАЕТ
        page.navigate("https://papergames.io/ru/четыре-в-ряд");

        page.click("button:has-text('Войти')");

        page.fill("input#mat-input-0", config.getEmail()); // здесь можно указать нужный логин
        page.fill("input#mat-input-1", config.getPassword());    // здесь можно указать нужный пароль

        // Ожидание, пока кнопка "Войти" станет активной (без атрибута disabled)
        page.waitForSelector("button.btn.btn-secondary.w-100:not([disabled])");
        // Нажимаем на кнопку "Войти"
        page.click("button.btn.btn-secondary.w-100");
        page.waitForTimeout(500);

        page.click("button:has-text('Играть онлайн')");
//        page.fill("input[formcontrolname='username']", username);
//        page.click("button:has-text('Продолжить')");

    }

    public Player[] getPlayersInfo() {
        page.waitForSelector(".col-6:nth-child(1) .chronometer", new Page.WaitForSelectorOptions().setTimeout(50000000));
        page.waitForTimeout(700);

        return new Player[]{getPlayer1Info(), getPlayer2Info()};
    }


    public Player getPlayer1Info() {
        String p1Nickname = page.locator(".col-6:nth-child(1) .text-truncate.cursor-pointer").textContent();
        String p1Color = page.locator(".col-6:nth-child(1) .shape").getAttribute("class");
        if (p1Color.equals("shape circle-light")) {
            p1Color = "circle-light";
        } else {
            p1Color = "circle-dark";
        }


        String p1Timer = page.locator(".col-6:nth-child(1) .chronometer").textContent();

        String p1TimerFill = (String) page.evalOnSelector(".col-6:nth-child(1) .chronometer", "el => getComputedStyle(el).backgroundColor");
        int p1Turn = 2;
        if (Objects.equals(p1TimerFill, "rgb(24, 188, 156)")) {
            p1Turn = 1;
        }
        System.out.println("Игрок 1: Ник: " + p1Nickname + ", Цвет: " + p1Color + ", Таймер: " + p1Timer + ", Ходит: " + p1Turn + " isMinimax: " + username.equals(p1Nickname));

        return new Player(p1Nickname, p1Color, p1Timer, p1Turn, username.equals(p1Nickname));
    }

    public Player getPlayer2Info() {
        String p2Nickname = page.locator(".col-6:nth-child(2) .text-truncate.cursor-pointer").textContent();
        String p2Color = page.locator(".col-6:nth-child(2) .shape").getAttribute("class");
        if (p2Color.equals("shape circle-light")) {
            p2Color = "circle-light";
        } else {
            p2Color = "circle-dark";
        }


        String p2Timer = page.locator(".col-6:nth-child(2) .chronometer").textContent();
        String p2TimerFill = (String) page.evalOnSelector(".col-6:nth-child(2) .chronometer", "el => getComputedStyle(el).backgroundColor");
        int p2Turn = 2;
        if (Objects.equals(p2TimerFill, "rgb(24, 188, 156)")) {
            p2Turn = 1;
        }
        System.out.println("Игрок 2: Ник: " + p2Nickname + ", Цвет: " + p2Color + ", Таймер: " + p2Timer + ", Ходит: " + p2Turn + " isMinimax: " + username.equals(p2Nickname));
        return new Player(p2Nickname, p2Color, p2Timer, p2Turn, username.equals(p2Nickname));
    }


    public void placeMove(int column) { // РАБОТАЕТ.
        page.waitForTimeout(1000);
        if (page.locator("button:has-text('Играть онлайн')").isVisible()){
            return;
        }
        String selector = ".grid-item.cell-1-" + (column + 1);
        Locator selectableCells = page.locator(selector);
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        selectableCells.first().click();

    }


    public int getLastMoveCircle(String OpponentColor) {
        page.waitForTimeout(1000);

        page.waitForSelector("circle.last-move.ng-star-inserted", new Page.WaitForSelectorOptions().setTimeout(50000000));
        Locator lastMoveCircle = page.locator("circle.last-move.ng-star-inserted");
        Locator svg = lastMoveCircle.locator("..");
        Locator firstCircle = svg.locator("circle").first();
        String lastMoveCircleColor = firstCircle.getAttribute("class");

        // Ожидание, пока цвет не совпадет с нужным
        while (!OpponentColor.equals(lastMoveCircleColor)) {
            page.waitForTimeout(500);
            lastMoveCircleColor = firstCircle.getAttribute("class"); // обновление цвета в цикле
        }
        //System.out.println("Color matched: " + OpponentColor.equals(lastMoveCircleColor));

        // Поиск родительского элемента, содержащего класс вида "cell-X-Y"
        Locator gridItem = lastMoveCircle.locator("xpath=ancestor::div[contains(@class, 'grid-item') and contains(@class, 'cell-')]");
        String cellClass = gridItem.getAttribute("class");

        // Извлечение номера столбца (вторая цифра в шаблоне cell-X-Y)
        Pattern pattern = Pattern.compile("cell-\\d+-(\\d+)");
        Matcher matcher = pattern.matcher(cellClass);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1)) - 1;
        } else {
            throw new IllegalStateException("Не найден класс с шаблоном 'cell-X-Y' в: " + cellClass);
        }
    }
}
