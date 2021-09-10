import dao.UserDao;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        System.out.println(userDao.getBalance(1L));
    }
}
