import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.menu.exception.BankException;
import org.menu.model.User;
import org.menu.service.BankAppSearch;
import org.menu.service.impl.BankAppSearchImpl;

public class MainTest {
    private static BankAppSearch validation;

    @BeforeAll
    public static void setupValidation() { validation=new BankAppSearchImpl();}

    @Test
    public void testCreateUser() throws BankException {
        String username="user";
        String password="pass";
        String type = "customer";
        String nullvar="";
        User user = validation.getUser(username,password);
        Assertions.assertNotNull(user);
        user=validation.getUser(username,nullvar);
        Assertions.assertNull(user);
        user=validation.getUser(nullvar,password);
        Assertions.assertNull(user);
        user=validation.getUser(nullvar,nullvar);
        Assertions.assertNull(user);
    }
}
