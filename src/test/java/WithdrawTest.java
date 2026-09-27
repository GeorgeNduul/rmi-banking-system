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
public class WithdrawTest {
    @Test
void testWithdraw() throws Exception {

    AccountImpl account = new AccountImpl("Alice");

    account.deposit(1000);

    account.withdraw(250);

    assertEquals(750.0f, account.getBalance());
}
    
}
