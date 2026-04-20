package steps.payments;

import base.KafkaTestClient;
import base.PaymentsDbClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentsCucumberSteps {

    private final KafkaTestClient kafkaClient = new KafkaTestClient("localhost:9092");
    private final String paymentsTopic = "payments";

    private final PaymentsDbClient paymentsDbClient = new PaymentsDbClient();

    @Given("в системе есть клиент с активным счётом и достаточным остатком")
    public void existingCustomerWithActiveAccount() {
        // учебный каркас
    }

    @Given("у клиента на счёте достаточно средств для платежа на сумму {int} рублей")
    public void clientHasEnoughBalance(int amount) {
        // учебный каркас
    }

    // Сценарий SUCCESS
    @When("клиент в ДБО отправляет платёж по реквизитам на сумму {int} рублей")
    public void sendPaymentByDetails(int amount) throws Exception {
        String body = """
                {
                 "clientId": "test-client",
                 "amount": %d
                }
                """.formatted(amount);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/payments"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode()!=200){
            throw new IllegalStateException("Payment service returned status " + response.statusCode()
            + "body: " + response.body());
        }
//        // имитация успешного платежа: сразу пишем запись в БД
//        paymentsDbClient.insertTestPayment(amount, "SUCCESS");
    }

    // Сценарий DECLINED_LIMIT
    @When("клиент в ДБО пытается отправить платёж по реквизитам на сумму {int} рублей")
    public void tryToSendPaymentByDetailsWithLimit(int amount) throws Exception {
        // имитация отклонённого платежа
        paymentsDbClient.insertTestPayment(amount, "DECLINED_LIMIT");
    }

    @Then("сервис платежей по REST вызывает сервис счетов для списания средств")
    public void paymentServiceCallsAccountsServiceForDebit() {
        // учебный каркас
    }

    @And("сервис платежей публикует событие о платеже в Kafka")
    public void paymentServicePublishesKafkaEvent() throws Exception {
        kafkaClient.sendMessage(paymentsTopic, "test-key", "test-payment-event");
        var records = kafkaClient.pollMessages(paymentsTopic, Duration.ofSeconds(5));
        assertFalse(records.isEmpty(), "Ожидали хотя бы одно сообщение в Kafka топике 'payments'");
    }

    @And("сервис уведомлений получает событие из Kafka и формирует уведомление")
    public void notificationServiceProcessesKafkaEvent() {
        // учебный каркас
    }

    @And("в PostgreSQL создаётся запись о проведённом платеже со статуса {string}")
    public void verifySuccessfulPaymentStatusInDb(String status) throws Exception {
        boolean exists = paymentsDbClient.existsPayment(1000, status);
        assertTrue(exists, "Ожидали запись о платеже на сумму 1000 со статусом " + status);
    }

    @And("в PostgreSQL остаток по счёту плательщика уменьшается на {int} рублей")
    public void verifyPayerBalanceDecreased(int amount) {
        // учебный каркас
    }

    @Given("у клиента установлен дневной лимит на платежи в размере {int} рублей")
    public void setDailyLimit(int limit) {
        // учебный каркас
    }

    @And("по текущему дню клиент уже выполнил платежи на сумму {int} рублей")
    public void setAlreadySpentAmount(int amount) {
        // учебный каркас
    }

    @Then("сервис платежей по REST получает от сервиса лимитов отказ с причиной {string}")
    public void verifyLimitServiceReason(String reason) {
        // учебный каркас
    }

    @And("сервис платежей возвращает в ДБО ошибку {string}")
    public void verifyErrorReturnedToDbo(String message) {
        // учебный каркас
    }

    @And("в PostgreSQL создаётся запись о платеже со статусом {string}")
    public void verifyDeclinedPaymentStatusInDb(String status) throws Exception {
        boolean exists = paymentsDbClient.existsPayment(1000, status);
        assertTrue(exists, "Ожидали запись о платеже на сумму 1000 со статусом " + status);
    }

    @And("в PostgreSQL остаток по счёту клиента не изменяется")
    public void verifyCustomerBalanceNotChanged() {
        // учебный каркас
    }
}