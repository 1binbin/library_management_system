package JDBCUtils;

import java.util.Date;

/**
 * @author hongxiaobin
 * @create 2021/12/20-19:40
 * @description
 */
@SuppressWarnings("unused")
public class allentity {
    private String rid;
    private String rpassword;
    private String rname;
    private String rsex;
    private int rage;
    private String rphone;
    private int rcredit;
    private String bisbn;
    private String bname;
    private String bauthor;
    private String bcategory;
    private String bprice;
    private String bcount;
    private Date borrowingdate;
    private Date duedate;
    private Date returndate;
    private Date rbirthday;
    private String remarks;
    private int borrownum;

    public allentity() {
    }

    public allentity(String rid, String rpassword, String rname, String rsex, int rage, String rphone, int rcredit, String bisbn, String bname, String bauthor, String bcategory, String bprice, String bcount, Date borrowingdate, Date duedate, Date returndate, Date rbirthday, String remarks, int borrownum) {
        this.rid = rid;
        this.rpassword = rpassword;
        this.rname = rname;
        this.rsex = rsex;
        this.rage = rage;
        this.rphone = rphone;
        this.rcredit = rcredit;
        this.bisbn = bisbn;
        this.bname = bname;
        this.bauthor = bauthor;
        this.bcategory = bcategory;
        this.bprice = bprice;
        this.bcount = bcount;
        this.borrowingdate = borrowingdate;
        this.duedate = duedate;
        this.returndate = returndate;
        this.rbirthday = rbirthday;
        this.remarks = remarks;
        this.borrownum = borrownum;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRpassword() {
        return rpassword;
    }

    public void setRpassword(String rpassword) {
        this.rpassword = rpassword;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRsex() {
        return rsex;
    }

    public void setRsex(String rsex) {
        this.rsex = rsex;
    }

    public int getRage() {
        return rage;
    }

    public void setRage(int rage) {
        this.rage = rage;
    }

    public String getRphone() {
        return rphone;
    }

    public void setRphone(String rphone) {
        this.rphone = rphone;
    }

    public int getRcredit() {
        return rcredit;
    }

    public void setRcredit(int rcredit) {
        this.rcredit = rcredit;
    }

    public String getBisbn() {
        return bisbn;
    }

    public void setBisbn(String bisbn) {
        this.bisbn = bisbn;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public String getBcategory() {
        return bcategory;
    }

    public void setBcategory(String bcategory) {
        this.bcategory = bcategory;
    }

    public String getBprice() {
        return bprice;
    }

    public void setBprice(String bprice) {
        this.bprice = bprice;
    }

    public String getBcount() {
        return bcount;
    }

    public void setBcount(String bcount) {
        this.bcount = bcount;
    }

    public Date getBorrowingdate() {
        return borrowingdate;
    }

    public void setBorrowingdate(Date borrowingdate) {
        this.borrowingdate = borrowingdate;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public Date getReturndate() {
        return returndate;
    }

    public void setReturndate(Date returndate) {
        this.returndate = returndate;
    }

    public Date getRbirthday() {
        return rbirthday;
    }

    public void setRbirthday(Date rbirthday) {
        this.rbirthday = rbirthday;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getBorrownum() {
        return borrownum;
    }

    public void setBorrownum(int borrownum) {
        this.borrownum = borrownum;
    }

    @Override
    public String toString() {
        return "allentity{" +
                "rid='" + rid + '\'' +
                ", rpassword='" + rpassword + '\'' +
                ", rname='" + rname + '\'' +
                ", rsex='" + rsex + '\'' +
                ", rage=" + rage +
                ", rphone='" + rphone + '\'' +
                ", rcredit=" + rcredit +
                ", bisbn='" + bisbn + '\'' +
                ", bname='" + bname + '\'' +
                ", bauthor='" + bauthor + '\'' +
                ", bcategory='" + bcategory + '\'' +
                ", bprice='" + bprice + '\'' +
                ", bcount='" + bcount + '\'' +
                ", borrowingdate=" + borrowingdate +
                ", duedate=" + duedate +
                ", returndate=" + returndate +
                ", rbirthday=" + rbirthday +
                ", remarks='" + remarks + '\'' +
                ", borrownum=" + borrownum +
                '}';
    }
}