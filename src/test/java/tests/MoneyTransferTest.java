package tests;

import org.junit.jupiter.api.*;
import pages.DashboardPage;
import pages.LoginPage;
import pages.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoneyTransferTest {

    @BeforeAll
    static void setUp() {
        open("http://localhost:9999"); // Запуск приложения
    }

    @Test
    @Order(1)
    void shouldTransferMoneyFromFirstToSecondCard() {
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin("vasya", "qwerty123");
        DashboardPage dashboardPage = verificationPage.validVerify("12345");

        int initialBalanceFirst = dashboardPage.getCardBalance(0);
        int initialBalanceSecond = dashboardPage.getCardBalance(1);

        int transferAmount = 5000;

        dashboardPage.transferMoney(0, 1, transferAmount);

        int finalBalanceFirst = dashboardPage.getCardBalance(0);
        int finalBalanceSecond = dashboardPage.getCardBalance(1);

        assertEquals(initialBalanceFirst - transferAmount, finalBalanceFirst);
        assertEquals(initialBalanceSecond + transferAmount, finalBalanceSecond);
    }

    @Test
    @Order(2)
    void shouldNotTransferMoreThanBalance() {
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin("vasya", "qwerty123");
        DashboardPage dashboardPage = verificationPage.validVerify("12345");

        int initialBalanceFirst = dashboardPage.getCardBalance(0);
        int initialBalanceSecond = dashboardPage.getCardBalance(1);

        int transferAmount = initialBalanceFirst + 1; // Больше, чем баланс

        dashboardPage.transferMoney(0, 1, transferAmount);

        int finalBalanceFirst = dashboardPage.getCardBalance(0);
        int finalBalanceSecond = dashboardPage.getCardBalance(1);

        // Балансы не должны измениться
        assertEquals(initialBalanceFirst, finalBalanceFirst);
        assertEquals(initialBalanceSecond, finalBalanceSecond);
    }
}