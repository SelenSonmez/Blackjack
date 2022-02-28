import java.lang.reflect.Array;
import java.util.ArrayList;

public class User extends Account{
    private String userName;
    private String password;

    public User(){
        ArrayList<User> users = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String toString(){
        return this.getUserName();
    }
}
