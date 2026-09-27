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
public class TransferTest {
    @Test
void testTransfer() throws Exception {

    BankImpl bank = new BankImpl();

    bank.openAccount("George", 1000);
    bank.openAccount("Alice", 500);

    bank.transfer("George", "Alice", 200);

    assertEquals(
            800.0f,
            bank.findAccount("George").getBalance());

    assertEquals(
            700.0f,
            bank.findAccount("Alice").getBalance());
}
}
