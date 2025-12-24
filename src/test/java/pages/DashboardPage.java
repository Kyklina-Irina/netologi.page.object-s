package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$("[data-test-id='card-list'] [data-test-id='card']");

    public int getCardBalance(int index) {
        var text = cards.get(index).text();
        return extractBalance(text);
    }

    public void transferMoney(int fromIndex, int toIndex, int amount) {
        cards.get(fromIndex).$$("button").get(0).click(); // Кнопка "Пополнить"
        TransferPage transferPage = new TransferPage();
        transferPage.transfer(amount, cards.get(toIndex).getAttribute("data-test-id"));
    }

    private int extractBalance(String text) {
        var balanceStart = "баланс: ";
        var balanceFinish = " р.";
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}