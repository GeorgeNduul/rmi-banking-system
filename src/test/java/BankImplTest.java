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

public class BankImplTest {

    @Test
    void testOpenAccount() throws Exception {

        BankImpl bank = new BankImpl();

Account account = bank.openAccount("George", 100);
        assertNotNull(account);
        assertEquals("George", account.getName());
    }
}