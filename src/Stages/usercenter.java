package Stages;

import JDBCUtils.JDBCutils;
import JDBCUtils.allentity;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author hongxiaobin
 * @create 2021/12/21-11:56
 * @description 用户中心界面
 */
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "AlibabaUndefineMagicConstant"})
public class usercenter {
    private static Label labelledpi = new Label();
    private static Label labelledpass = new Label();
    private static String sqlselect;
    private static String sqlsumcount;
    private static String sqlnotreturncount;
    private static String sqloutcount;
    private final TextField searchreturnTF = new TextField();
    private final TextField borrownumTF = new TextField();
    private final TextField searchborrowTF = new TextField();
    private final TableView<allentity> borrowtableView = new TableView<>();
    private final TableView<allentity> returntableView = new TableView<>();
    private final ComboBox<String> daysCB = new ComboBox<>();
    private final java.util.Date nowadate = new java.util.Date();
    private Label labelled = new Label();
    private String rname;
    private int rcredit;
    private String sql = null;
    private String snowadate;
    private String rpassword;
    private String oldremarks;

    /*借书中心*/
    public Stage userborrowstage() {
        Stage stage = new Stage();
        MainStage mainStage = new MainStage();
        sql = "select rname ,rcredit from readers where rid = " + MainStage.ID;
        List<allentity> list = JDBCutils.getselectlist(allentity.class, sql);
        assert list != null;
        rname = list.get(0).getRname();
        rcredit = list.get(0).getRcredit();

        MenuBar menuBar = new MenuBar();
        Menu homepageMenu = new Menu();
        Menu idMenu = new Menu();
        Label homepagelabel = new Label("主页");
        Label idlabel = new Label("您好，" + rname + "(" + MainStage.ID + ")   信用分：" + rcredit);
        homepagelabel.setFont(new Font("黑体", 18));
        idlabel.setFont(new Font("黑体", 16));
        homepageMenu.setGraphic(homepagelabel);
        idMenu.setGraphic(idlabel);
        menuBar.getMenus().addAll(homepageMenu, idMenu);
        BorderPane menubarGP = new BorderPane();
        menubarGP.setTop(menuBar);
        homepagelabel.setOnMouseClicked(event -> {
            try {
                mainStage.usermainstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });

        sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books";
        borrowtableView.setItems(mainStage.gettablevalue(sql));
        borrowtableView.setPrefSize(1175, 900);
        borrowtableView.setMaxWidth(1175);
        TableColumn<allentity, String> firstColumn1 = new TableColumn<>("ISBN");
        firstColumn1.setPrefWidth(140);
        firstColumn1.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
        TableColumn<allentity, String> firstColumn2 = new TableColumn<>("书名");
        firstColumn2.setPrefWidth(300);
        firstColumn2.setCellValueFactory(new PropertyValueFactory<>("bname"));
        TableColumn<allentity, String> firstColumn3 = new TableColumn<>("作者");
        firstColumn3.setPrefWidth(250);
        firstColumn3.setCellValueFactory(new PropertyValueFactory<>("bauthor"));
        TableColumn<allentity, String> firstColumn4 = new TableColumn<>("类别");
        firstColumn4.setPrefWidth(160);
        firstColumn4.setCellValueFactory(new PropertyValueFactory<>("bcategory"));
        TableColumn<allentity, String> firstColumn5 = new TableColumn<>("价格");
        firstColumn5.setPrefWidth(100);
        firstColumn5.setCellValueFactory(new PropertyValueFactory<>("bprice"));
        TableColumn<allentity, String> firstColumn6 = new TableColumn<>("库存量");
        firstColumn6.setPrefWidth(100);
        firstColumn6.setCellValueFactory(new PropertyValueFactory<>("bcount"));
        borrowtableView.getColumns().addAll(firstColumn1, firstColumn2, firstColumn3, firstColumn4, firstColumn5, firstColumn6);

//       添加各种控件
        Label isbnlabel = new Label("ISBN");
        Label bnamelabel = new Label("书名");
        Label bauthorlabel = new Label("作者");
        Label bcategorylabel = new Label("类别");
        Label bpricelabel = new Label("价格");
        Label bcountlabel = new Label("库存量");
        Label searchtypelabel = new Label("搜索类型");
        Label borrowdatelabel = new Label("借书时长");
        Label monthlabel = new Label("天");
        Label borrownumlabel = new Label("数量");
        isbnlabel.setFont(new Font("黑体", 18));
        bnamelabel.setFont(new Font("黑体", 18));
        bauthorlabel.setFont(new Font("黑体", 18));
        bcategorylabel.setFont(new Font("黑体", 18));
        bpricelabel.setFont(new Font("黑体", 18));
        bcountlabel.setFont(new Font("黑体", 18));
        searchtypelabel.setFont(new Font("黑体", 18));
        borrowdatelabel.setFont(new Font("黑体", 18));
        monthlabel.setFont(new Font("黑体", 18));
        borrownumlabel.setFont(new Font("黑体", 18));

        TextField isbnTF = new TextField();
        TextField bnameTF = new TextField();
        TextField bauthorTF = new TextField();
        TextField bcategoryTF = new TextField();
        TextField bpriceTF = new TextField();
        TextField bcountTF = new TextField();
        isbnTF.setEditable(false);
        bnameTF.setEditable(false);
        bauthorTF.setEditable(false);
        bcategoryTF.setEditable(false);
        bpriceTF.setEditable(false);
        bcountTF.setEditable(false);
        borrownumTF.setPromptText("借书数量");
        searchborrowTF.setPromptText("搜索内容");

        Button borrowButton = new Button("借书");
        Button okButton = new Button("搜索");
        Button refreshButton = new Button("刷新");
        borrowButton.setFont(new Font("黑体", 18));
        okButton.setFont(new Font("黑体", 18));
        refreshButton.setFont(new Font("黑体", 18));

        ComboBox<String> searchtypeCB = new ComboBox<>();
        searchtypeCB.setPromptText("请选择");
        searchtypeCB.getItems().addAll("全部", "ISBN", "书名", "作者", "类别");
        daysCB.setPromptText("请选择");
        for (int i = 1; i <= 120; i++) {
            daysCB.getItems().add(String.valueOf(i));
        }
        GridPane labelledGP = new GridPane();

//        事件
        borrowtableView.setRowFactory(tv -> {
            TableRow<allentity> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    isbnTF.setText(row.getItem().getBisbn());
                    bnameTF.setText(row.getItem().getBname());
                    bauthorTF.setText(row.getItem().getBauthor());
                    bcategoryTF.setText(row.getItem().getBcategory());
                    bpriceTF.setText(row.getItem().getBprice());
                    bcountTF.setText(row.getItem().getBcount());
                }
            });
            return row;
        });
//        各种事件处理
        borrowButton.setOnAction(event -> {
            long intonum;
            long oldnum;
            if (daysCB.getValue() != null) {
                try {
                    intonum = Long.parseLong(borrownumTF.getText());
                    oldnum = Long.parseLong(bcountTF.getText());
                    Date nowadate = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    //           现在日期
                    String snowadate = simpleDateFormat.format(nowadate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(nowadate);
                    calendar.add(Calendar.DATE, Integer.parseInt(daysCB.getValue()));
                    //           应还书日期
                    String sduedate = simpleDateFormat.format(calendar.getTime());
                    if (intonum <= oldnum) {
                        String insertsql = "insert into borrowing(rid,bisbn,borrowingdate,duedate,borrownum,remarks) values(?,?,?,?,?,?)";
                        JDBCutils.setADM(insertsql, MainStage.ID, isbnTF.getText(), snowadate, sduedate, borrownumTF.getText(), "未归还");
                        String updatasql = "update books set bcount = bcount - ? where bisbn = ?";
                        JDBCutils.setADM(updatasql, borrownumTF.getText(), isbnTF.getText());
                        labelled = new Label("借书成功，请尽快归还，超过到期日期三天将扣除信用分");
                        sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books";
                        borrowtableView.setItems(mainStage.gettablevalue(sql));
                    } else {
                        labelled = new Label("借书数量超出库存量");
                    }
                } catch (Exception e) {
                    labelled = new Label("请输入正确的借书数量");
                }
            } else {
                labelled = new Label("请选择借书时长");
            }
            labelled.setFont(new Font("黑体", 20));
            labelled.setTextFill(Color.RED);
            labelledGP.add(labelled, 0, 5);
            labelledGP.setAlignment(Pos.CENTER);
        });
        okButton.setOnAction(event -> {
            if ("全部".equals(searchtypeCB.getValue())) {
                sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books";
            } else if ("ISBN".equals(searchtypeCB.getValue())) {
                sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books where bisbn like '%" + searchborrowTF.getText() + "%'";
            } else if ("书名".equals(searchtypeCB.getValue())) {
                sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books where bname like '%" + searchborrowTF.getText() + "%'";
            } else if ("作者".equals(searchtypeCB.getValue())) {
                sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books where bauthor like '%" + searchborrowTF.getText() + "%'";
            } else if ("类别".equals(searchtypeCB.getValue())) {
                sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books where bcategory like '%" + searchborrowTF.getText() + "%'";
            } else {
                labelled = new Label("出错");
                labelled.setFont(new Font("黑体", 20));
                labelled.setTextFill(Color.RED);
                labelledGP.add(labelled, 0, 5);
                labelledGP.setAlignment(Pos.CENTER);
            }
            borrowtableView.setItems(mainStage.gettablevalue(sql));
        });
        refreshButton.setOnAction(event -> {
            isbnTF.clear();
            bnameTF.clear();
            bauthorTF.clear();
            bcategoryTF.clear();
            bpriceTF.clear();
            bcountTF.clear();
            borrownumTF.clear();
            searchborrowTF.clear();
            sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books";
            borrowtableView.setItems(mainStage.gettablevalue(sql));
        });
        searchtypeCB.setOnAction(event -> {
            searchborrowTF.clear();
            sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books ";
            borrowtableView.setItems(mainStage.gettablevalue(sql));
        });
        daysCB.setOnAction(event -> borrownumTF.clear());
        if (labelled != null) {
            isbnTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bnameTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bauthorTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bcategoryTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bpriceTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bcountTF.setOnMouseClicked(event -> labelled.setVisible(false));
            searchborrowTF.setOnMouseClicked(event -> labelled.setVisible(false));
            borrownumTF.setOnMouseClicked(event -> labelled.setVisible(false));
            borrowtableView.setOnMouseClicked(event -> labelled.setVisible(false));
            refreshButton.setOnMouseClicked(event -> labelled.setVisible(false));
            daysCB.setOnMouseClicked(event -> labelled.setVisible(false));
            searchtypeCB.setOnMouseClicked(event -> labelled.setVisible(false));
        }

        HBox firstHB = new HBox();
        HBox secondHB = new HBox();
        HBox thirdHB = new HBox();
        HBox forthHB = new HBox();
        HBox fifthHB = new HBox();
        firstHB.setAlignment(Pos.CENTER);
        secondHB.setAlignment(Pos.CENTER);
        thirdHB.setAlignment(Pos.CENTER);
        forthHB.setAlignment(Pos.CENTER);
        fifthHB.setAlignment(Pos.CENTER);
        firstHB.setSpacing(20);
        secondHB.setSpacing(20);
        thirdHB.setSpacing(20);
        forthHB.setSpacing(20);
        firstHB.getChildren().addAll(isbnlabel, isbnTF, bnamelabel, bnameTF, bauthorlabel, bauthorTF);
        secondHB.getChildren().addAll(bcategorylabel, bcategoryTF, bpricelabel, bpriceTF, bcountlabel, bcountTF);
        thirdHB.getChildren().addAll(borrowdatelabel, daysCB, monthlabel, borrownumlabel, borrownumTF, borrowButton, refreshButton);
        forthHB.getChildren().addAll(searchtypelabel, searchtypeCB, searchborrowTF, okButton);
        fifthHB.getChildren().addAll(borrowtableView);

        VBox rootnodeVB = new VBox();
        rootnodeVB.setPadding(new Insets(0, 0, 0, 0));
        rootnodeVB.setSpacing(15);
        rootnodeVB.getChildren().addAll(menubarGP, firstHB, secondHB, thirdHB, forthHB, labelledGP, fifthHB);

        Scene scene = new Scene(rootnodeVB, 1200, 800);
        scene.setOnMouseClicked(event -> {
            if (labelled != null) {
                labelled.setVisible(false);
            }
        });
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("读者服务 借书中心");
        return stage;
    }

    /*还书中心*/
    public Stage userreturnstage() {
        Stage stage = new Stage();
        MainStage mainStage = new MainStage();
        sql = "select rname,rcredit from readers where rid = " + MainStage.ID;
        List<allentity> list = JDBCutils.getselectlist(allentity.class, sql);
        assert list != null;
        rname = list.get(0).getRname();
        rcredit = list.get(0).getRcredit();

        MenuBar menuBar = new MenuBar();
        Menu homepageMenu = new Menu();
        Menu idMenu = new Menu();
        Label homepagelabel = new Label("主页");
        Label idlabel = new Label("您好，" + rname + "(" + MainStage.ID + ")   信用分：" + rcredit);
        homepagelabel.setFont(new Font("黑体", 18));
        homepageMenu.setGraphic(homepagelabel);
        idlabel.setFont(new Font("黑体", 16));
        idMenu.setGraphic(idlabel);
        menuBar.getMenus().addAll(homepageMenu, idMenu);
        BorderPane menubarBP = new BorderPane();
        menubarBP.setTop(menuBar);
        homepagelabel.setOnMouseClicked(event -> {
            try {
                mainStage.usermainstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });

        sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and borrowing.rid = " + MainStage.ID;
        returntableView.setItems(mainStage.gettablevalue(sql));
        returntableView.setPrefSize(1175, 900);
        returntableView.setMaxWidth(1175);
        TableColumn<allentity, String> firstColumn1 = new TableColumn<>("ISBN");
        firstColumn1.setPrefWidth(140);
        firstColumn1.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
        TableColumn<allentity, String> firstColumn2 = new TableColumn<>("书名");
        firstColumn2.setPrefWidth(200);
        firstColumn2.setCellValueFactory(new PropertyValueFactory<>("bname"));
        TableColumn<allentity, String> firstColumn3 = new TableColumn<>("作者");
        firstColumn3.setPrefWidth(120);
        firstColumn3.setCellValueFactory(new PropertyValueFactory<>("bauthor"));
        TableColumn<allentity, String> firstColumn4 = new TableColumn<>("类别");
        firstColumn4.setPrefWidth(120);
        firstColumn4.setCellValueFactory(new PropertyValueFactory<>("bcategory"));
        TableColumn<allentity, String> firstColumn5 = new TableColumn<>("借书日期");
        firstColumn5.setPrefWidth(120);
        firstColumn5.setCellValueFactory(new PropertyValueFactory<>("borrowingdate"));
        TableColumn<allentity, String> firstColumn6 = new TableColumn<>("截止还书日期");
        firstColumn6.setPrefWidth(120);
        firstColumn6.setCellValueFactory(new PropertyValueFactory<>("duedate"));
        TableColumn<allentity, String> firstColumn7 = new TableColumn<>("还书日期");
        firstColumn7.setPrefWidth(120);
        firstColumn7.setCellValueFactory(new PropertyValueFactory<>("returndate"));
        TableColumn<allentity, String> firstColumn8 = new TableColumn<>("状态");
        firstColumn8.setPrefWidth(80);
        firstColumn8.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        TableColumn<allentity, String> firstColumn9 = new TableColumn<>("借书数量");
        firstColumn9.setPrefWidth(80);
        firstColumn9.setCellValueFactory(new PropertyValueFactory<>("borrownum"));
        returntableView.getColumns().addAll(firstColumn1, firstColumn2, firstColumn3, firstColumn4, firstColumn5, firstColumn6, firstColumn7, firstColumn9, firstColumn8);

//       添加各种控件
        Label isbnlabel = new Label("ISBN");
        Label bnamelabel = new Label("书名");
        Label bauthorlabel = new Label("作者");
        Label bcategorylabel = new Label("类别");
        Label borrowdatelabel = new Label("借书日期");
        Label duedatelabel = new Label("截止还书日期");
        Label searchtypelabel = new Label("搜索类型");
        Label borrowmonthlabel = new Label("借书时长");
        Label monthlabel = new Label("月");
        Label borrownumlabel = new Label("数量");
        Label searchrangelabel = new Label("搜索范围");
        isbnlabel.setFont(new Font("黑体", 18));
        bnamelabel.setFont(new Font("黑体", 18));
        bauthorlabel.setFont(new Font("黑体", 18));
        bcategorylabel.setFont(new Font("黑体", 18));
        borrowdatelabel.setFont(new Font("黑体", 18));
        duedatelabel.setFont(new Font("黑体", 18));
        searchtypelabel.setFont(new Font("黑体", 18));
        borrowmonthlabel.setFont(new Font("黑体", 18));
        monthlabel.setFont(new Font("黑体", 18));
        borrownumlabel.setFont(new Font("黑体", 18));
        searchrangelabel.setFont(new Font("黑体", 18));

        TextField isbnTF = new TextField();
        TextField bnameTF = new TextField();
        TextField bauthorTF = new TextField();
        TextField bcategoryTF = new TextField();
        TextField borrowdateTF = new TextField();
        TextField duedateTF = new TextField();
        isbnTF.setEditable(false);
        bnameTF.setEditable(false);
        bauthorTF.setEditable(false);
        bcategoryTF.setEditable(false);
        borrowdateTF.setEditable(false);
        duedateTF.setEditable(false);
        searchreturnTF.setPromptText("搜索内容");

        Button returnbookButton = new Button("还书");
        Button okButton = new Button("搜索");
        Button refreshButton = new Button("刷新");
        returnbookButton.setFont(new Font("黑体", 18));
        okButton.setFont(new Font("黑体", 18));
        refreshButton.setFont(new Font("黑体", 18));

        ComboBox<String> searchtypeCB = new ComboBox<>();
        ComboBox<String> searchrangeCB = new ComboBox<>();
        searchtypeCB.setPromptText("请选择");
        searchrangeCB.setPromptText("请选择");
        searchtypeCB.getItems().addAll("ISBN", "书名", "作者", "类别");
        searchrangeCB.getItems().addAll("全部", "已归还", "未归还", "逾期未归还");
        GridPane labelledGP = new GridPane();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        snowadate = simpleDateFormat.format(nowadate);

//        事件
        returntableView.setRowFactory(tv -> {
            TableRow<allentity> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    isbnTF.setText(row.getItem().getBisbn());
                    bnameTF.setText(row.getItem().getBname());
                    bauthorTF.setText(row.getItem().getBauthor());
                    bcategoryTF.setText(row.getItem().getBcategory());
                    borrowdateTF.setText(row.getItem().getBorrowingdate() + "");
                    duedateTF.setText(row.getItem().getDuedate() + "");
                    oldremarks = row.getItem().getRemarks();
                }
            });
            return row;
        });
        returnbookButton.setOnAction(event -> {
            try {
                if ("未归还".equals(oldremarks)) {
//                    借书数量
                    String selectcountsql = "select borrownum from borrowing where bisbn = " + isbnTF.getText();
                    List<allentity> list1 = JDBCutils.getselectlist(allentity.class, selectcountsql);
                    assert list1 != null;
                    int count = list1.get(0).getBorrownum();
//                    更新借阅表
                    String updatesqlborrowing = "update borrowing set returndate = ? ,remarks = '已归还' where bisbn = ?";
                    JDBCutils.setADM(updatesqlborrowing, snowadate, isbnTF.getText());
//                    更新书库表
                    String updatesqlbooks = "update books set bcount = bcount + ? where bisbn = ?";
                    JDBCutils.setADM(updatesqlbooks, count, isbnTF.getText());
                    labelled = new Label("还书成功");
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and borrowing.rid = " + MainStage.ID;
                    returntableView.setItems(mainStage.gettablevalue(sql));
                    java.util.Date duedate = Objects.requireNonNull(JDBCutils.getselectlist(allentity.class, sql)).get(0).getDuedate();
                    long now = nowadate.getTime();
                    long due = duedate.getTime();
                    if (now - due >= 3) {
                        String updateSQL = "update readers set rcredit = rcredit - ? where rid = " + MainStage.ID;
                        JDBCutils.setADM(updateSQL, 10);
                        labelled = new Label("您已超过到期日期三天还书，已扣除信用分10分");
                    }
                } else if ("已归还".equals(oldremarks)) {
                    labelled = new Label("图书已归还");
                } else {
                    labelled = new Label("请先选择要还的书籍");
                }
            } catch (Exception e) {
                labelled = new Label("出错请重新还书");
            }
            labelled.setFont(new Font("黑体", 20));
            labelled.setTextFill(Color.RED);
            labelledGP.add(labelled, 0, 5);
            labelledGP.setAlignment(Pos.CENTER);
        });
        okButton.setOnAction(event -> {
            if ("全部".equals(searchrangeCB.getValue())) {
                if ("ISBN".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and borrowing.bisbn like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("书名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and bname like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("作者".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and bauthor like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("类别".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and bcategory like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                }
            } else if ("已归还".equals(searchrangeCB.getValue())) {
                if ("ISBN".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and remarks = '已归还' and borrowing.bisbn like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("书名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and remarks = '已归还' and bname like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("作者".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and remarks = '已归还' and bauthor like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("类别".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and remarks = '已归还' and bcategory like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                }
            } else if ("未归还".equals(searchrangeCB.getValue())) {
                if ("ISBN".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and remarks = '未归还' and borrowing.bisbn like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("书名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and remarks = '未归还' and bname like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("作者".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and remarks = '未归还' and bauthor like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("类别".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and remarks = '未归还' and bcategory like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                }
            } else if ("逾期未归还".equals(searchrangeCB.getValue())) {
                if ("ISBN".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and duedate < '" + snowadate + "' and borrowing.bisbn like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("书名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and duedate < '" + snowadate + "' and bname like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("作者".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and duedate < '" + snowadate + "' and bauthor like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                } else if ("类别".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and duedate < '" + snowadate + "' and bcategory like '%" + searchreturnTF.getText() + "%' and borrowing.rid = " + MainStage.ID;
                }
            }
            returntableView.setItems(mainStage.gettablevalue(sql));
        });
        refreshButton.setOnAction(event -> {
            isbnTF.clear();
            bnameTF.clear();
            bauthorTF.clear();
            bcategoryTF.clear();
            borrowdateTF.clear();
            duedateTF.clear();
            borrownumTF.clear();
            searchreturnTF.clear();
            searchrangeCB.setValue("全部");
            sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and borrowing.rid = " + MainStage.ID;
            returntableView.setItems(mainStage.gettablevalue(sql));
        });
        searchrangeCB.setOnAction(event -> {
            searchreturnTF.clear();
            if ("全部".equals(searchrangeCB.getValue())) {
                sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and borrowing.rid = " + MainStage.ID;
            } else if ("已归还".equals(searchrangeCB.getValue())) {
                sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and remarks = '已归还' and borrowing.rid = " + MainStage.ID;
            } else if ("未归还".equals(searchrangeCB.getValue())) {
                sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and remarks = '未归还' and borrowing.rid = " + MainStage.ID;
            } else if ("逾期未归还".equals(searchrangeCB.getValue())) {
                sql = "select borrowing.bisbn,bname,bauthor,bcategory,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books where borrowing.bisbn = books.bisbn and duedate < '" + snowadate + "' and borrowing.rid = " + MainStage.ID;
            }
            returntableView.setItems(mainStage.gettablevalue(sql));
        });
        searchtypeCB.setOnAction(event -> searchreturnTF.clear());
        if (labelled != null) {
            isbnTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bnameTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bauthorTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bcategoryTF.setOnMouseClicked(event -> labelled.setVisible(false));
            borrowdateTF.setOnMouseClicked(event -> labelled.setVisible(false));
            duedateTF.setOnMouseClicked(event -> labelled.setVisible(false));
            searchreturnTF.setOnMouseClicked(event -> labelled.setVisible(false));
            borrownumTF.setOnMouseClicked(event -> labelled.setVisible(false));
            returntableView.setOnMouseClicked(event -> labelled.setVisible(false));
            searchtypeCB.setOnMouseClicked(event -> labelled.setVisible(false));
            searchrangeCB.setOnMouseClicked(event -> labelled.setVisible(false));
            refreshButton.setOnMouseClicked(event -> labelled.setVisible(false));
        }

        HBox firstHB = new HBox();
        HBox secondHB = new HBox();
        HBox thirdHB = new HBox();
        HBox fourthHB = new HBox();
        firstHB.setAlignment(Pos.CENTER);
        secondHB.setAlignment(Pos.CENTER);
        thirdHB.setAlignment(Pos.CENTER);
        fourthHB.setAlignment(Pos.CENTER);
        firstHB.setSpacing(20);
        secondHB.setSpacing(20);
        thirdHB.setSpacing(20);
        firstHB.getChildren().addAll(isbnlabel, isbnTF, bnamelabel, bnameTF, borrowdatelabel, borrowdateTF);
        secondHB.getChildren().addAll(bcategorylabel, bcategoryTF, bauthorlabel, bauthorTF, duedatelabel, duedateTF);
        thirdHB.getChildren().addAll(searchrangelabel, searchrangeCB, searchtypelabel, searchtypeCB, searchreturnTF, okButton, refreshButton, returnbookButton);
        fourthHB.getChildren().addAll(returntableView);

        VBox rootnodeVB = new VBox();
        rootnodeVB.setPadding(new Insets(0, 0, 0, 0));
        rootnodeVB.setSpacing(15);
        rootnodeVB.getChildren().addAll(menubarBP, firstHB, secondHB, thirdHB, labelledGP, fourthHB);

        Scene scene = new Scene(rootnodeVB, 1200, 800);
        scene.setOnMouseClicked(event -> {
            if (labelled != null) {
                labelled.setVisible(false);
            }
        });
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("读者服务 还书中心");
        return stage;
    }

    /*个人中心*/
    public Stage userpersonalstage() {
        Stage stage = new Stage();
        MainStage mainStage = new MainStage();
        sql = "select rname ,rcredit from readers where rid = " + MainStage.ID;
        List<allentity> list = JDBCutils.getselectlist(allentity.class, sql);
        assert list != null;
        rname = list.get(0).getRname();
        rcredit = list.get(0).getRcredit();

        MenuBar menuBar = new MenuBar();
        Menu homepageMenu = new Menu();
        Menu idMenu = new Menu();
        Label homepagelabel = new Label("主页");
        Label idlabel = new Label("您好，" + rname + "(" + MainStage.ID + ")   信用分：" + rcredit);
        homepagelabel.setFont(new Font("黑体", 18));
        idlabel.setFont(new Font("黑体", 16));
        homepageMenu.setGraphic(homepagelabel);
        idMenu.setGraphic(idlabel);
        menuBar.getMenus().addAll(homepageMenu, idMenu);
        BorderPane menubarBP = new BorderPane();
        menubarBP.setTop(menuBar);
        homepagelabel.setOnMouseClicked(event -> {
            try {
                mainStage.usermainstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });

        Label ridlabel = new Label("借阅证编号");
        Label rnamelabel = new Label("姓 名");
        Label ragelabel = new Label("年  龄");
        Label rsexlabel = new Label("性  别");
        Label rphonelabel = new Label("联系方式");
        Label borrownum = new Label("借书总数");
        Label notreturnnum = new Label("未归还数目");
        Label rbirthdaylabel = new Label("出生日期");
        Label outdatenum = new Label("逾期未归还数目");
        Label resetpassword = new Label("重置密码");
        Label updatapilabel = new Label("修改个人信息");
        Label personalinfornation = new Label("修改项");
        Label oldpassword = new Label("原 密 码");
        Label newpassword = new Label("新 密 码");
        Label newcheckpassword = new Label("确定密码");
        Label updatepassword = new Label("修改密码");
        Label codelabel = new Label("验 证 码");
        ridlabel.setFont(new Font("黑体", 20));
        rnamelabel.setFont(new Font("黑体", 20));
        ragelabel.setFont(new Font("黑体", 20));
        rsexlabel.setFont(new Font("黑体", 20));
        rphonelabel.setFont(new Font("黑体", 20));
        borrownum.setFont(new Font("黑体", 20));
        notreturnnum.setFont(new Font("黑体", 20));
        outdatenum.setFont(new Font("黑体", 20));
        resetpassword.setFont(new Font("黑体", 16));
        resetpassword.setTextFill(Color.RED);
        rbirthdaylabel.setFont(new Font("黑体", 20));
        oldpassword.setFont(new Font("黑体", 22));
        newpassword.setFont(new Font("黑体", 22));
        newcheckpassword.setFont(new Font("黑体", 22));
        personalinfornation.setFont(new Font("黑体", 22));
        codelabel.setFont(new Font("黑体", 22));
        updatapilabel.setFont(new Font("黑体", 24));
        updatepassword.setFont(new Font("黑体", 24));

        TextField ridtextfield = new TextField();
        TextField rnametextfield = new TextField();
        TextField ragetextfield = new TextField();
        TextField rsextextfield = new TextField();
        TextField rphonetextfield = new TextField();
        TextField rbithdaytextfield = new TextField();
        TextField borrownumtextfield = new TextField();
        TextField notreturntextfield = new TextField();
        TextField outdatenumtextfield = new TextField();
        TextField updatepitextfield = new TextField();
        TextField codetextfield = new TextField();
        PasswordField oldpasswordtextfield = new PasswordField();
        PasswordField newpasswordtextfield = new PasswordField();
        PasswordField newpasswordchecktextfield = new PasswordField();
        ridtextfield.setEditable(false);
        rnametextfield.setEditable(false);
        ragetextfield.setEditable(false);
        rsextextfield.setEditable(false);
        rphonetextfield.setEditable(false);
        rbithdaytextfield.setEditable(false);
        borrownumtextfield.setEditable(false);
        notreturntextfield.setEditable(false);
        outdatenumtextfield.setEditable(false);
        oldpasswordtextfield.setPromptText("原密码");
        newpasswordtextfield.setPromptText("密码长度至少6位");
        newpasswordchecktextfield.setPromptText("请再次确认密码");
        codetextfield.setPromptText("请输入验证码");

        Button ok1 = new Button("确   定");
        Button ok2 = new Button("确   定");
        Button refresh = new Button("刷  新");
        Button reset1 = new Button("重    置");
        Button reset2 = new Button("重    置");
        ok1.setFont(new Font("黑体", 20));
        ok2.setFont(new Font("黑体", 20));
        refresh.setFont(new Font("黑体", 20));
        reset1.setFont(new Font("黑体", 20));
        reset2.setFont(new Font("黑体", 20));

        DatePicker datePicker = new DatePicker();
        ComboBox<String> picombox = new ComboBox<>();
        picombox.setPromptText("请选择");
        picombox.getItems().addAll("姓名", "性别", "联系方式", "出生日期");

//        设置验证码
        char a = (char) (Math.random() * 25 + 65);
        char b = (char) (Math.random() * 25 + 65);
        char c = (char) (Math.random() * 25 + 65);
        char d = (char) (Math.random() * 25 + 65);
        String s = a + "" + b + "" + c + "" + d;
        Label labelledcode = new Label(s);
        labelledcode.setFont(new Font("黑体", 20));
        labelledcode.setTextFill(Color.RED);
        GridPane labelledpiGP = new GridPane();
        GridPane labelledpassGP = new GridPane();

//        信息初始化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        snowadate = simpleDateFormat.format(nowadate);
        sqlselect = "select rid,rpassword,rname,rbirthday,rage,rsex,rphone from readers where rid = " + MainStage.ID;
        sqlsumcount = "select count(rid) from borrowing where rid = " + MainStage.ID;
        sqlnotreturncount = "select count(rid) from borrowing where remarks = '未归还' and rid = " + MainStage.ID;
        sqloutcount = "select count(rid) from borrowing where duedate < '" + snowadate + "' and rid = " + MainStage.ID + " and remarks = '未归还'";
        getlist(sqlselect, ridtextfield, rnametextfield, ragetextfield, rsextextfield, rphonetextfield, rbithdaytextfield);
        borrownumtextfield.setText(getcount(sqlsumcount) + "");
        notreturntextfield.setText(getcount(sqlnotreturncount) + "");
        outdatenumtextfield.setText(getcount(sqloutcount) + "");

//        事件处理
        refresh.setOnAction(event -> {
            sqlselect = "select rid,rpassword,rname,rbirthday,rage,rsex,rphone from readers where rid = " + MainStage.ID;
            sqlsumcount = "select count(rid) from borrowing where rid = " + MainStage.ID;
            sqlnotreturncount = "select count(rid) from borrowing where remarks = '未归还' and rid = " + MainStage.ID;
            sqloutcount = "select count(rid) from borrowing where duedate < '" + snowadate + "' and rid = " + MainStage.ID + " and remarks = '未归还'";
            getlist(sqlselect, ridtextfield, rnametextfield, ragetextfield, rsextextfield, rphonetextfield, rbithdaytextfield);
            borrownumtextfield.setText(getcount(sqlsumcount) + "");
            notreturntextfield.setText(getcount(sqlnotreturncount) + "");
            outdatenumtextfield.setText(getcount(sqloutcount) + "");
            updatepitextfield.clear();
        });
        datePicker.setVisible(false);
        picombox.setOnAction(event -> {
            if ("姓名".equals(picombox.getValue())) {
                updatepitextfield.setPromptText("输入正确的姓名");
            } else if ("性别".equals(picombox.getValue())) {
                updatepitextfield.setPromptText("输入 男 或 女");
            } else if ("联系方式".equals(picombox.getValue())) {
                updatepitextfield.setPromptText("手机号");
            } else if ("出生日期".equals(picombox.getValue())) {
                updatepitextfield.setPromptText("");
                datePicker.setPromptText("选择正确的出生日期");
            }
            updatepitextfield.clear();
            datePicker.setVisible("出生日期".equals(picombox.getValue()));
        });
        reset1.setOnAction(event -> {
            updatepitextfield.clear();
            datePicker.setValue(LocalDate.parse(snowadate));
        });
        ok1.setOnAction(event -> {
            String sqlname;
            if ("姓名".equals(picombox.getValue())) {
                if (!updatepitextfield.getText().isEmpty()) {
                    sqlname = "update readers set rname = ? where rid = ?";
                    JDBCutils.setADM(sqlname, updatepitextfield.getText(), MainStage.ID);
                    labelledpi = new Label("修改成功");
                } else {
                    labelledpi = new Label("输入为空");
                }
            } else if ("联系方式".equals(picombox.getValue())) {
                if (!updatepitextfield.getText().isEmpty()) {
                    sqlname = "update readers set rphone = ? where rid = ?";
                    JDBCutils.setADM(sqlname, updatepitextfield.getText(), MainStage.ID);
                    labelledpi = new Label("修改成功");
                } else {
                    labelledpi = new Label("输入为空");
                }
            } else if ("性别".equals(picombox.getValue())) {
                if (!updatepitextfield.getText().isEmpty()) {
                    if ("男".equals(updatepitextfield.getText()) || "女".equals(updatepitextfield.getText())) {
                        sqlname = "update readers set rsex = ? where rid = ?";
                        JDBCutils.setADM(sqlname, updatepitextfield.getText(), MainStage.ID);
                        labelledpi = new Label("修改成功");
                    } else {
                        labelledpi = new Label("请输入男或女");
                    }
                } else {
                    labelledpi = new Label("输入为空");
                }
            } else if ("出生日期".equals(picombox.getValue())) {
                try {
//                转换localdate和date
                    LocalDate localdatechoose = datePicker.getValue();
                    ZoneId zonedID = ZoneId.systemDefault();
                    ZonedDateTime zonedDateTime = localdatechoose.atStartOfDay(zonedID);
                    Date datechoose = Date.from(zonedDateTime.toInstant());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(datechoose);
                    int old = calendar.get(Calendar.YEAR);
                    calendar.setTime(nowadate);
                    int now = calendar.get(Calendar.YEAR);
                    int age = now - old;
                    sqlname = "update readers set rbirthday = ? ,rage = ? where rid = ?";
                    JDBCutils.setADM(sqlname, datePicker.getValue(), age, MainStage.ID);
                    labelledpi = new Label("修改成功");
                } catch (Exception e) {
                    labelledpi = new Label("日期选择为空");
                }

            } else if (picombox.getValue() == null) {
                labelledpi = new Label("修改项选择为空");
            } else {
                labelledpi = new Label("出错");
            }
            sqlselect = "select rid,rpassword,rname,rbirthday,rage,rsex,rphone from readers where rid = " + MainStage.ID;
            sqlsumcount = "select count(rid) from borrowing where rid = " + MainStage.ID;
            sqlnotreturncount = "select count(rid) from borrowing where remarks = '未归还' and rid = " + MainStage.ID;
            sqloutcount = "select count(rid) from borrowing where duedate < '" + snowadate + "' and rid = " + MainStage.ID + " and remarks = '未归还'";
            getlist(sqlselect, ridtextfield, rnametextfield, ragetextfield, rsextextfield, rphonetextfield, rbithdaytextfield);
            borrownumtextfield.setText(getcount(sqlsumcount) + "");
            notreturntextfield.setText(getcount(sqlnotreturncount) + "");
            outdatenumtextfield.setText(getcount(sqloutcount) + "");
            labelledpi.setFont(new Font("黑体", 20));
            labelledpi.setTextFill(Color.RED);
            labelledpiGP.add(labelledpi, 1, 1);
        });
        reset2.setOnAction(event -> {
            oldpasswordtextfield.clear();
            newpasswordtextfield.clear();
            newpasswordchecktextfield.clear();
            codetextfield.clear();
        });
        ok2.setOnAction(event -> {
            if (!oldpasswordtextfield.getText().isEmpty()) {
                if (oldpasswordtextfield.getText().equals(rpassword)) {
                    if (newpasswordtextfield.getText().length() >= 6) {
                        if (newpasswordtextfield.getText().equals(newpasswordchecktextfield.getText())) {
                            if (!newpasswordtextfield.getText().equals(oldpasswordtextfield.getText())) {
                                if (codetextfield.getText().equalsIgnoreCase(s)) {
                                    String updatepasssql = "update readers set rpassword = ? where rid = ?";
                                    JDBCutils.setADM(updatepasssql, newpasswordchecktextfield.getText(), MainStage.ID);
                                    labelledpass = new Label("修改密码成功");
                                    oldpasswordtextfield.clear();
                                    newpasswordtextfield.clear();
                                    newpasswordchecktextfield.clear();
                                    codetextfield.clear();
                                } else {
                                    labelledpass = new Label("验证码错误");
                                }
                            } else {
                                labelledpass = new Label("设置密码与原密码相同");
                            }
                        } else {
                            labelledpass = new Label("密码设置不同");
                        }
                    } else {
                        labelledpass = new Label("密码设置长度小于6位");
                    }
                } else {
                    labelledpass = new Label("原密码输入错误");
                }
            } else {
                labelledpass = new Label("原密码输入为空");
            }
            labelledpass.setFont(new Font("黑体", 20));
            labelledpass.setTextFill(Color.RED);
            labelledpassGP.add(labelledpass, 1, 1);
        });
        newpasswordtextfield.setOnMouseClicked(event -> {
            if (newpasswordtextfield.getText().isEmpty()) {
                newpasswordchecktextfield.setEditable(false);
            }
        });
        newpasswordchecktextfield.setOnMouseClicked(event -> {
            if (!newpasswordtextfield.getText().isEmpty()) {
                newpasswordchecktextfield.setEditable(true);
            }
        });
        resetpassword.setOnMouseClicked(event -> {
            try {
                isresetpassstage().show();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if (labelledpi != null) {
            picombox.setOnMouseClicked(event -> labelledpi.setVisible(false));
            updatepitextfield.setOnMouseClicked(event -> labelledpi.setVisible(false));
            refresh.setOnMouseClicked(event -> labelledpi.setVisible(false));
            reset1.setOnMouseClicked(event -> labelledpi.setVisible(false));
        }
        if (labelledpass != null) {
            oldpasswordtextfield.setOnMouseClicked(event -> labelledpass.setVisible(false));
            newpasswordtextfield.setOnMouseClicked(event -> labelledpass.setVisible(false));
            newpasswordchecktextfield.setOnMouseClicked(event -> labelledpass.setVisible(false));
            codetextfield.setOnMouseClicked(event -> labelledpass.setVisible(false));
            reset2.setOnMouseClicked(event -> labelledpass.setVisible(false));
        }

        GridPane infornationGP = new GridPane();
        infornationGP.setAlignment(Pos.CENTER);
        infornationGP.setHgap(20);
        infornationGP.setVgap(25);
        GridPane.setHalignment(ridlabel, HPos.RIGHT);
        GridPane.setHalignment(rnamelabel, HPos.RIGHT);
        GridPane.setHalignment(ragelabel, HPos.RIGHT);
        GridPane.setHalignment(rphonelabel, HPos.RIGHT);
        GridPane.setHalignment(rsexlabel, HPos.RIGHT);
        GridPane.setHalignment(borrownum, HPos.RIGHT);
        GridPane.setHalignment(notreturnnum, HPos.RIGHT);
        GridPane.setHalignment(outdatenum, HPos.RIGHT);
        GridPane.setHalignment(rbirthdaylabel, HPos.RIGHT);
        infornationGP.setPadding(new Insets(20, 0, 50, 0));
        infornationGP.add(ridlabel, 1, 1);
        infornationGP.add(ridtextfield, 2, 1);
        infornationGP.add(rnamelabel, 3, 1);
        infornationGP.add(rnametextfield, 4, 1);
        infornationGP.add(rsexlabel, 5, 1);
        infornationGP.add(rsextextfield, 6, 1);
        infornationGP.add(rphonelabel, 1, 2);
        infornationGP.add(rphonetextfield, 2, 2);
        infornationGP.add(ragelabel, 3, 2);
        infornationGP.add(ragetextfield, 4, 2);
        infornationGP.add(rbirthdaylabel, 5, 2);
        infornationGP.add(rbithdaytextfield, 6, 2);
        infornationGP.add(borrownum, 1, 3);
        infornationGP.add(borrownumtextfield, 2, 3);
        infornationGP.add(notreturnnum, 3, 3);
        infornationGP.add(notreturntextfield, 4, 3);
        infornationGP.add(outdatenum, 5, 3);
        infornationGP.add(outdatenumtextfield, 6, 3);
//        分割线1
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(1200);
        line.setStrokeWidth(0.3);
//        修改个人信息面板
        HBox personalinfornationhbox = new HBox();
        HBox updatepihbox = new HBox();
        HBox buttonhbox = new HBox();
        personalinfornationhbox.setSpacing(20);
        updatepihbox.setSpacing(100);
        buttonhbox.setSpacing(80);
        personalinfornationhbox.setAlignment(Pos.CENTER);
        updatepihbox.setAlignment(Pos.CENTER);
        updatepihbox.setPadding(new Insets(30, 0, 0, 0));
        buttonhbox.setPadding(new Insets(25, 0, 50, 455));
        personalinfornationhbox.getChildren().addAll(personalinfornation, picombox, updatepitextfield, datePicker);
        updatepihbox.getChildren().addAll(updatapilabel, personalinfornationhbox);
        buttonhbox.getChildren().addAll(refresh, reset1, ok1, labelledpiGP);
//        分割线2
        Line line2 = new Line();
        line2.setStartX(0);
        line2.setEndX(1200);
        line2.setStrokeWidth(0.3);
//        修改密码面板
        VBox passwordvBox = new VBox();
        HBox passwordhbox = new HBox();
        passwordhbox.setPadding(new Insets(30, 0, 0, 150));
        passwordhbox.setSpacing(100);
        HBox buttonhbox1 = new HBox();
        buttonhbox1.setPadding(new Insets(20, 0, 0, 475));
        buttonhbox1.setSpacing(38);
        GridPane passwordgridPane = new GridPane();
        GridPane.setHalignment(newpassword, HPos.RIGHT);
        GridPane.setHalignment(newcheckpassword, HPos.RIGHT);
        GridPane.setHalignment(oldpassword, HPos.RIGHT);
        GridPane.setHalignment(codelabel, HPos.RIGHT);
        passwordgridPane.setHgap(20);
        passwordgridPane.setVgap(15);
        passwordgridPane.add(oldpassword, 1, 1);
        passwordgridPane.add(oldpasswordtextfield, 2, 1);
        passwordgridPane.add(resetpassword, 3, 1);
        passwordgridPane.add(newpassword, 1, 2);
        passwordgridPane.add(newpasswordtextfield, 2, 2);
        passwordgridPane.add(newcheckpassword, 1, 3);
        passwordgridPane.add(newpasswordchecktextfield, 2, 3);
        passwordgridPane.add(codelabel, 1, 4);
        passwordgridPane.add(codetextfield, 2, 4);
        passwordgridPane.add(labelledcode, 3, 4);
        passwordhbox.getChildren().addAll(updatepassword, passwordgridPane);
        buttonhbox1.getChildren().addAll(reset2, ok2, labelledpassGP);
        passwordvBox.getChildren().addAll(passwordhbox, buttonhbox1);
//        主面板布局
        VBox rootnodeVB = new VBox();
        rootnodeVB.getChildren().addAll(menubarBP, infornationGP, line, updatepihbox, buttonhbox, line2, passwordvBox);

        Scene scene = new Scene(rootnodeVB, 1200, 800);
        scene.setOnMouseClicked(event -> {
            if (labelledpi != null) {
                labelledpi.setVisible(false);
            }
            if (labelledpass != null) {
                labelledpass.setVisible(false);
            }
        });
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("读者服务 个人中心");
        return stage;
    }

    /*询问重置密码*/
    private Stage isresetpassstage() {
        Stage stage = new Stage();
        Label label = new Label("是 否 重 置 密 码");
        label.setFont(new Font("黑体", 25));
        Button ok = new Button("确 定");
        Button cancel = new Button("取 消");
        ok.setFont(new Font("黑体", 19));
        cancel.setFont(new Font("黑体", 19));

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(40);
        hBox.getChildren().addAll(ok, cancel);

        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(10, 0, 0, 0));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(label, hBox);

        MainStage mainStage = new MainStage();
        ok.setOnAction(event -> {
            mainStage.retrievepasswordstage().show();
            stage.close();
        });
        cancel.setOnAction(event -> {
            userpersonalstage().show();
            stage.close();
        });
        Scene scene = new Scene(vBox, 300, 120);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("重置密码");
        return stage;
    }

    /*查询所有结果*/
    private void getlist(String sql, TextField ridtextfield, TextField rnametextfield, TextField ragetextfield, TextField rsextextfield, TextField rphonetextfield, TextField rbithdaytextfield) {
        List<allentity> list = JDBCutils.getselectlist(allentity.class, sql);
        assert list != null;
        String rid = list.get(0).getRid();
        rname = list.get(0).getRname();
        Date rbirthday = list.get(0).getRbirthday();
        int rage = list.get(0).getRage();
        String rsex = list.get(0).getRsex();
        String rphone = list.get(0).getRphone();
        rpassword = list.get(0).getRpassword();
        ridtextfield.setText(rid);
        rnametextfield.setText(rname);
        ragetextfield.setText(rage + "");
        rsextextfield.setText(rsex);
        rphonetextfield.setText(rphone);
        rbithdaytextfield.setText(rbirthday + "");
    }

    /*查询聚集函数*/
    private long getcount(String sql) {
        return JDBCutils.getcount(sql);
    }
}