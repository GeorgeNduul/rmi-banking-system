/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author HP
 */
public class InsuffecientFundsTest {
    @Test
void testInsufficientFunds() throws Exception {

    AccountImpl account = new AccountImpl("Alice");

    account.deposit(100);

    assertThrows(
        InsufficientFundsException.class,
        () -> account.withdraw(500)
    );
}
}
