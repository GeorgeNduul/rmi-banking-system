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


public class AccountImplTest {

    @Test
    void testAccountCreation() throws Exception {

        AccountImpl account = new AccountImpl("Alice");

        assertEquals("Alice", account.getName());
        assertEquals(0.0f, account.getBalance());
    }
}