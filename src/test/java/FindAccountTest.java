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
public class FindAccountTest {
 @Test
void testFindAccount() throws Exception {

    BankImpl bank = new BankImpl();

    bank.openAccount("George", 100);

    Account account =
            bank.findAccount("George");

    assertNotNull(account);
    assertEquals("George", account.getName());
}   
}
