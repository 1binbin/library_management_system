import JDBCUtils.JDBCutils;

import java.util.Scanner;

/**
 * @author hongxiaobin
 * @create 2021/12/15-11:24
 * @function 设置管理员账号和密码
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
public class administratorsEnroll {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("账号");
        String phone = scanner.next();
        if (phone.length() == 10) {
            System.out.println("请输入密码");
            String password1 = scanner.next();
            System.out.println("请再次确认密码");
            String password2 = scanner.next();
            String password = null;
            if (password1.equals(password2)) {
                password = password1;
            }
            administratorsEnroll administratorsEnroll = new administratorsEnroll();
            administratorsEnroll.setEnroll(phone, password);
        } else {
            System.out.println("账号格式不正确！");
        }
    }

    private void setEnroll(String phone, String password) {
        String sql = "insert into administrators(aid,apassword) values(?,?)";
        JDBCutils.setADM(sql, phone, password);
        System.out.println("管理员信息注册成功！");
    }
}