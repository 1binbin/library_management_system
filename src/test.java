import JDBCUtils.JDBCutils;
import JDBCUtils.allentity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongxiaobin
 * @create 2022/3/4-8:42
 * @description
 */
public class test {
    public static void main(String[] args) {
        String sql = "select * from readers";
        List<allentity> list = JDBCutils.getselectlist(allentity.class, sql);
        System.out.println(list);
        String a = "3120003369";
        assert list != null;
        List<allentity> list1 = new ArrayList<>();
        for (JDBCUtils.allentity allentity : list) {
            if (allentity.getRid().equals(a)) {
                list1.add(allentity);
                break;
            }
        }
        System.out.println(list1);
    }
}
