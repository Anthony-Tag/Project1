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
        String nullvar=null;
        User user = validation.createUser(username,password,type);
        Assertions.assertNotNull(user);
        user=validation.createUser(username,nullvar, type);
        Assertions.assertNull(user);
        user=validation.createUser(nullvar,password, type);
        Assertions.assertNull(user);
        user=validation.createUser(nullvar,nullvar, type);
        Assertions.assertNull(user);
    }
}
