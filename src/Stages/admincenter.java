package Stages;

import JDBCUtils.JDBCutils;
import JDBCUtils.allentity;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author hongxiaobin
 * @create 2021/12/21-11:05
 * @description
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
public class admincenter {
    private static final TextField bookssearchTF = new TextField();
    private static final TextField readerssearchTF = new TextField();
    private static final TextField borrowsearchTF = new TextField();
    private static final TextField readersnumTF = new TextField();
    private static final ArrayList<String> arrayListselect = new ArrayList<>();
    private static Label labelled = new Label();
    private static Label label = new Label();
    private static String sql = null;
    private final TableView<allentity> bookstableView = new TableView<>();
    private final TableView<allentity> readerstableView = new TableView<>();
    private final TableView<allentity> borrowingtableView = new TableView<>();
    private final java.util.Date nowadate = new java.util.Date();
    private String snowadate;
    private String booksnumSQL;
    private String bcountnumSQL;
    private String usernumSQL;
    private File file;

    /*图书管理界面*/
    public Stage adminbooksstage() {
        MainStage mainStage = new MainStage();
        Stage stage = new Stage();
        MenuBar menuBar = new MenuBar();
        Menu homepageMenu = new Menu();
        Menu idMenu = new Menu();
        Label homepagelabel = new Label("主页");
        Label idlabel = new Label("您好，" + MainStage.ID);
        homepagelabel.setFont(new Font("黑体", 18));
        idlabel.setFont(new Font("黑体", 16));
        homepageMenu.setGraphic(homepagelabel);
        idMenu.setGraphic(idlabel);
        menuBar.getMenus().addAll(homepageMenu, idMenu);
        BorderPane menubarGP = new BorderPane();
        menubarGP.setTop(menuBar);
        homepagelabel.setOnMouseClicked(event -> {
            try {
                mainStage.adminmainstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });

        sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books";
        bookstableView.setItems(mainStage.gettablevalue(sql));
        bookstableView.setPrefSize(1475, 900);
        bookstableView.setMaxWidth(1475);
        TableColumn<allentity, String> bisbncolumn = new TableColumn<>("ISBN");
        TableColumn<allentity, String> bnamecolumn = new TableColumn<>("书名");
        TableColumn<allentity, String> bauthorcolumn = new TableColumn<>("作者");
        TableColumn<allentity, String> bcategorycolumn = new TableColumn<>("类别");
        TableColumn<allentity, String> bpricecolumn = new TableColumn<>("价格");
        TableColumn<allentity, String> bcountcolumn = new TableColumn<>("库存量");
        bisbncolumn.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
        bisbncolumn.setPrefWidth(140);
        bnamecolumn.setCellValueFactory(new PropertyValueFactory<>("bname"));
        bnamecolumn.setPrefWidth(500);
        bauthorcolumn.setCellValueFactory(new PropertyValueFactory<>("bauthor"));
        bauthorcolumn.setPrefWidth(350);
        bcategorycolumn.setCellValueFactory(new PropertyValueFactory<>("bcategory"));
        bcategorycolumn.setPrefWidth(150);
        bpricecolumn.setCellValueFactory(new PropertyValueFactory<>("bprice"));
        bpricecolumn.setPrefWidth(100);
        bcountcolumn.setCellValueFactory(new PropertyValueFactory<>("bcount"));
        bcountcolumn.setPrefWidth(100);
        bookstableView.getColumns().addAll(bisbncolumn, bnamecolumn, bauthorcolumn, bcategorycolumn, bpricecolumn, bcountcolumn);

        Label isbnlabel = new Label("ISBN");
        Label bnamelebel = new Label("书名");
        Label bauthorlabel = new Label("作者");
        Label bcategorylabel = new Label("类别");
        Label bpricelabel = new Label("价格");
        Label bcountlabel = new Label("库存量");
        Label searchtypeslabel = new Label("搜索类型");
        Label booksnumlabel = new Label("总图书种类数目");
        Label bcountnumlabel = new Label("总图书库存量数目");
        isbnlabel.setFont(new Font("黑体", 18));
        bnamelebel.setFont(new Font("黑体", 18));
        bauthorlabel.setFont(new Font("黑体", 18));
        bcategorylabel.setFont(new Font("黑体", 18));
        bpricelabel.setFont(new Font("黑体", 18));
        bcountlabel.setFont(new Font("黑体", 18));
        searchtypeslabel.setFont(new Font("黑体", 18));
        bcountnumlabel.setFont(new Font("黑体", 18));
        booksnumlabel.setFont(new Font("黑体", 18));

        TextField isbnTF = new TextField();
        TextField bnameTF = new TextField();
        TextField bauthorTF = new TextField();
        TextField bcategoryTF = new TextField();
        TextField bpriceTF = new TextField();
        TextField bcountTF = new TextField();
        TextField booksnumTF = new TextField();
        TextField bcountnumTF = new TextField();
        bookssearchTF.setPromptText("搜索内容");
        booksnumTF.setEditable(false);
        bcountnumTF.setEditable(false);
        booksnumTF.setMinWidth(100);

        Button addButton = new Button("增加");
        Button updateButton = new Button("修改");
        Button deleteButton = new Button("删除");
        Button okButton = new Button("搜索");
        Button refreshtableButton = new Button("刷新");
        Button choosebookButton = new Button("批量插入");
        Button deleteselectButton = new Button("批量删除");
        addButton.setFont(new Font("黑体", 18));
        updateButton.setFont(new Font("黑体", 18));
        deleteButton.setFont(new Font("黑体", 18));
        okButton.setFont(new Font("黑体", 18));
        refreshtableButton.setFont(new Font("黑体", 18));
        choosebookButton.setFont(new Font("黑体", 18));
        deleteselectButton.setFont(new Font("黑体", 18));
        Tooltip tooltip = new Tooltip();
        tooltip.setText("选择批量插入的文件");
        choosebookButton.setTooltip(tooltip);

        ComboBox<String> searchCB = new ComboBox<>();
        searchCB.setPromptText("请选择");
        searchCB.getItems().addAll("全部", "ISBN", "书名", "作者", "类别");
        GridPane labelledGP = new GridPane();

//        信息初始化
        booksnumSQL = "select count(bisbn) from books ";
        bcountnumSQL = "select sum(bcount) from books";
        booksnumTF.setText(getcount(booksnumSQL) + "");
        bcountnumTF.setText(getcount(bcountnumSQL) + "");

//        事件处理
        bookstableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        bookstableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ObservableList<allentity> a = bookstableView.getSelectionModel().getSelectedItems();
                for (int i = 0; i < a.size(); i++) {
                    arrayListselect.add(a.get(i).getBisbn());
                }
            } catch (Exception e) {
                System.out.print("");
            }
        });
        bookstableView.setRowFactory(tv -> {
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
        addButton.setOnAction(event -> {
            String insertSQL = "insert into books(bisbn,bname,bauthor,bcategory,bprice,bcount) values(?,?,?,?,?,?)";
            String isSQL = "select bisbn from books where bisbn = ?";
            if (!isbnTF.getText().isEmpty()) {
                if (!isbnTF.getText().isEmpty() && !bnameTF.getText().isEmpty() && !bauthorTF.getText().isEmpty() && !bcategoryTF.getText().isEmpty() && !bcountTF.getText().isEmpty() && !bpriceTF.getText().isEmpty()) {
                    try {
                        Long.parseLong(bcountTF.getText());
                        if (!JDBCutils.isexistence(isSQL, isbnTF.getText())) {
                            JDBCutils.setADM(insertSQL, isbnTF.getText().split("\t")[0], bnameTF.getText(), bauthorTF.getText(), bcategoryTF.getText(), bpriceTF.getText(), bcountTF.getText());
                            labelled = new Label("添加成功");
                            sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books";
                            bookstableView.setItems(mainStage.gettablevalue(sql));
                            booksnumSQL = "select count(bisbn) from books ";
                            bcountnumSQL = "select sum(bcount) from books";
                            booksnumTF.setText(getcount(booksnumSQL) + "");
                            bcountnumTF.setText(getcount(bcountnumSQL) + "");
                            isbnTF.clear();
                            bnameTF.clear();
                            bcategoryTF.clear();
                            bauthorTF.clear();
                            bpriceTF.clear();
                            bcountTF.clear();
                        } else {
                            labelled = new Label("课本已经存在");
                        }
                    } catch (Exception e) {
                        labelled = new Label("库存量输入整数");
                    }
                } else {
                    labelled = new Label("  请填写完整图书信息");
                }
            } else {
                labelled = new Label("ISBN为空");
            }
            labelled.setFont(new Font("黑体", 20));
            labelled.setTextFill(Color.RED);
            labelledGP.add(labelled, 1, 4);
            labelledGP.setAlignment(Pos.CENTER);
        });
        updateButton.setOnAction(event -> {
            String insertSQL = "update books set bisbn = ? , bname = ? , bauthor = ? , bcategory = ?,bprice = ?, bcount = ?  where bisbn = ?";
            String isSQL = "select bisbn from books where bisbn = ?";
            if (!isbnTF.getText().isEmpty()) {
                if (JDBCutils.isexistence(isSQL, isbnTF.getText())) {
                    JDBCutils.setADM(insertSQL, isbnTF.getText(), bnameTF.getText(), bauthorTF.getText(), bcategoryTF.getText(), bpriceTF.getText(), bcountTF.getText(), isbnTF.getText());
                    labelled = new Label("修改成功");
                    sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books";
                    bookstableView.setItems(mainStage.gettablevalue(sql));
                    booksnumSQL = "select count(bisbn) from books ";
                    bcountnumSQL = "select sum(bcount) from books";
                    booksnumTF.setText(getcount(booksnumSQL) + "");
                    bcountnumTF.setText(getcount(bcountnumSQL) + "");
                    isbnTF.clear();
                    bnameTF.clear();
                    bcategoryTF.clear();
                    bauthorTF.clear();
                    bpriceTF.clear();
                    bcountTF.clear();
                } else {
                    labelled = new Label("课本不存在");
                }
            } else {
                labelled = new Label("ISBN为空");
            }
            labelled.setFont(new Font("黑体", 20));
            labelled.setTextFill(Color.RED);
            labelledGP.add(labelled, 0, 4);
            labelledGP.setAlignment(Pos.CENTER);
        });
        deleteButton.setOnAction(event -> {
            String insertSQL = "delete from books where bisbn = ?";
            String isSQL = "select bisbn from books where bisbn =  ? ";
            String isnotreturn = "select bisbn from borrowing where bisbn = ? and remarks = '未归还' ";
            if (!isbnTF.getText().isEmpty()) {
                if (JDBCutils.isexistence(isSQL, isbnTF.getText())) {
                    if (!JDBCutils.isexistence(isnotreturn, isbnTF.getText())) {
                        JDBCutils.setADM(insertSQL, isbnTF.getText());
                        sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books";
                        bookstableView.setItems(mainStage.gettablevalue(sql));
                        labelled = new Label("删除成功");
                        booksnumSQL = "select count(bisbn) from books ";
                        bcountnumSQL = "select sum(bcount) from books";
                        booksnumTF.setText(getcount(booksnumSQL) + "");
                        bcountnumTF.setText(getcount(bcountnumSQL) + "");
                        isbnTF.clear();
                        bnameTF.clear();
                        bcategoryTF.clear();
                        bauthorTF.clear();
                        bpriceTF.clear();
                        bcountTF.clear();
                    } else {
                        labelled = new Label("该书籍已借出，不能删除");
                    }
                } else {
                    labelled = new Label("课本不存在");
                }
            } else {
                labelled = new Label("ISBN为空");
            }
            labelled.setFont(new Font("黑体", 20));
            labelled.setTextFill(Color.RED);
            labelledGP.add(labelled, 0, 4);
            labelledGP.setAlignment(Pos.CENTER);
        });
        okButton.setOnAction(event -> {
            if ("全部".equals(searchCB.getValue())) {
                sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books";
            } else if ("ISBN".equals(searchCB.getValue())) {
                sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books where bisbn like '%" + bookssearchTF.getText() + "%'";
            } else if ("书名".equals(searchCB.getValue())) {
                sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books where bname like '%" + bookssearchTF.getText() + "%'";
            } else if ("作者".equals(searchCB.getValue())) {
                sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books where bauthor like '%" + bookssearchTF.getText() + "%'";
            } else if ("类别".equals(searchCB.getValue())) {
                sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books where bcategory like '%" + bookssearchTF.getText() + "%'";
            } else {
                labelled = new Label("出错");
                labelled.setFont(new Font("黑体", 20));
                labelled.setTextFill(Color.RED);
                labelledGP.add(labelled, 0, 4);
                labelledGP.setAlignment(Pos.CENTER);
            }
            bookstableView.setItems(mainStage.gettablevalue(sql));
        });
        refreshtableButton.setOnAction(event -> {
            isbnTF.clear();
            bnameTF.clear();
            bauthorTF.clear();
            bcategoryTF.clear();
            bpriceTF.clear();
            bcountTF.clear();
            bookssearchTF.clear();
            sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books";
            bookstableView.setItems(mainStage.gettablevalue(sql));
            booksnumSQL = "select count(bisbn) from books ";
            bcountnumSQL = "select sum(bcount) from books";
            booksnumTF.setText(getcount(booksnumSQL) + "");
            bcountnumTF.setText(getcount(bcountnumSQL) + "");
        });
        searchCB.setOnAction(event -> {
            bookssearchTF.clear();
            if ("全部".equals(searchCB.getValue())) {
                sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books ";
                bookstableView.setItems(mainStage.gettablevalue(sql));
            }
        });
        choosebookButton.setOnAction(event -> {
            try {
                choosestage(1, labelledGP).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sql = "SELECT bisbn ,bname ,bauthor ,bcategory,bprice,bcount FROM books";
            bookstableView.setItems(mainStage.gettablevalue(sql));
            booksnumSQL = "select count(bisbn) from books ";
            bcountnumSQL = "select sum(bcount) from books";
            booksnumTF.setText(getcount(booksnumSQL) + "");
            bcountnumTF.setText(getcount(bcountnumSQL) + "");
        });
        deleteselectButton.setOnAction(event -> {
            String SQL = "delete from books where bisbn = ?";
            String isSQL = "select bisbn from books where bisbn = ?";
            String isnotreturn = "select bisbn from borrowing where bisbn = ? and remarks = '未归还' ";
            deletestage(SQL, isSQL, isnotreturn, labelledGP, 1).show();
        });
        if (labelled != null) {
            isbnTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bnameTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bauthorTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bcategoryTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bpriceTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bcountTF.setOnMouseClicked(event -> labelled.setVisible(false));
            bookssearchTF.setOnMouseClicked(event -> labelled.setVisible(false));
            refreshtableButton.setOnMouseClicked(event -> labelled.setVisible(false));
            bookstableView.setOnMouseClicked(event -> labelled.setVisible(false));
            searchCB.setOnMouseClicked(event -> labelled.setVisible(false));
        }

        HBox firstHB = new HBox();
        HBox secondHB = new HBox();
        HBox thirdHB = new HBox();
        HBox fourthHB = new HBox();
        HBox fifthHB = new HBox();
        firstHB.setAlignment(Pos.CENTER);
        secondHB.setAlignment(Pos.CENTER);
        thirdHB.setAlignment(Pos.CENTER);
        fourthHB.setAlignment(Pos.CENTER);
        fifthHB.setAlignment(Pos.CENTER);
        firstHB.setSpacing(20);
        secondHB.setSpacing(20);
        thirdHB.setSpacing(20);
        fourthHB.setSpacing(20);
        firstHB.getChildren().addAll(isbnlabel, isbnTF, bnamelebel, bnameTF, bauthorlabel, bauthorTF, addButton, updateButton);
        secondHB.getChildren().addAll(bcategorylabel, bcategoryTF, bpricelabel, bpriceTF, bcountlabel, bcountTF, deleteButton, refreshtableButton);
        thirdHB.getChildren().addAll(searchtypeslabel, searchCB, bookssearchTF, okButton);
        fourthHB.getChildren().addAll(booksnumlabel, booksnumTF, bcountnumlabel, bcountnumTF, choosebookButton, deleteselectButton);
        fifthHB.getChildren().addAll(bookstableView);

        VBox rootnodeVB = new VBox();
        rootnodeVB.setPadding(new Insets(0, 0, 0, 0));
        rootnodeVB.setSpacing(15);
        rootnodeVB.getChildren().addAll(menubarGP, firstHB, secondHB, fourthHB, thirdHB, labelledGP, fifthHB);

        Scene scene = new Scene(rootnodeVB, 1500, 900);
        scene.setOnMouseClicked(event -> {
            if (labelled != null) {
                labelled.setVisible(false);
            }
        });
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("管理员 图书管理");
        return stage;
    }

    /*读者管理界面*/
    public Stage adminreadersstage() {
        MainStage mainStage = new MainStage();
        Stage stage = new Stage();
        MenuBar menuBar = new MenuBar();
        Menu homepageMenu = new Menu();
        Menu idMenu = new Menu();
        Label homepagelabel = new Label("主页");
        Label idlabel = new Label("您好，" + MainStage.ID);
        homepagelabel.setFont(new Font("黑体", 18));
        idlabel.setFont(new Font("黑体", 16));
        homepageMenu.setGraphic(homepagelabel);
        idMenu.setGraphic(idlabel);
        menuBar.getMenus().addAll(homepageMenu, idMenu);
        BorderPane menubarGP = new BorderPane();
        menubarGP.setTop(menuBar);
        homepagelabel.setOnMouseClicked(event -> {
            try {
                mainStage.adminmainstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });

        sql = "SELECT rid ,rpassword,rname ,rage ,rsex,rphone ,rcredit FROM readers";
        readerstableView.setItems(mainStage.gettablevalue(sql));
        readerstableView.setPrefSize(1475, 900);
        readerstableView.setMaxWidth(1475);
        TableColumn<allentity, String> ridcolumn = new TableColumn<>("借阅证编号");
        TableColumn<allentity, String> rpasswordcolumn = new TableColumn<>("密码");
        TableColumn<allentity, String> rnamecolumn = new TableColumn<>("姓名");
        TableColumn<allentity, String> ragecolumn = new TableColumn<>("性别");
        TableColumn<allentity, String> rsexcolumn = new TableColumn<>("年龄");
        TableColumn<allentity, String> rphonecolumn = new TableColumn<>("联系方式");
        TableColumn<allentity, String> rcredircolumn = new TableColumn<>("信用分");
        ridcolumn.setCellValueFactory(new PropertyValueFactory<>("rid"));
        ridcolumn.setPrefWidth(150);
        rpasswordcolumn.setCellValueFactory(new PropertyValueFactory<>("rpassword"));
        rpasswordcolumn.setPrefWidth(200);
        rnamecolumn.setCellValueFactory(new PropertyValueFactory<>("rname"));
        rnamecolumn.setPrefWidth(120);
        ragecolumn.setCellValueFactory(new PropertyValueFactory<>("rsex"));
        ragecolumn.setPrefWidth(120);
        rsexcolumn.setCellValueFactory(new PropertyValueFactory<>("rage"));
        rsexcolumn.setPrefWidth(100);
        rphonecolumn.setCellValueFactory(new PropertyValueFactory<>("rphone"));
        rphonecolumn.setPrefWidth(200);
        rcredircolumn.setCellValueFactory(new PropertyValueFactory<>("rcredit"));
        rcredircolumn.setPrefWidth(100);
        readerstableView.getColumns().addAll(ridcolumn, rpasswordcolumn, rnamecolumn, ragecolumn, rsexcolumn, rphonecolumn, rcredircolumn);

//       添加各种控件
        Label ridlabel = new Label("借阅证编号");
        Label rnamelabel = new Label("姓     名");
        Label readersnumlabel = new Label("总读者数目");
        ridlabel.setFont(new Font("黑体", 18));
        rnamelabel.setFont(new Font("黑体", 18));
        readersnumlabel.setFont(new Font("黑体", 18));

        TextField rdiTF = new TextField();
        TextField rnameTF = new TextField();
        rdiTF.setPromptText("借阅证编号长度为10位");
        rnameTF.setPromptText("输入正确的姓名");
        readerssearchTF.setPromptText("搜索内容");
        readersnumTF.setEditable(false);
        readersnumTF.setPrefWidth(100);

        Button addButton = new Button("增加");
        Button deleteButton = new Button("删除");
        Button okButton = new Button("搜索");
        Button refreshtableButton = new Button("刷新");
        Button credieupdateButton = new Button("更改信用分");
        Button chooseButton = new Button("批量增加");
        Button deleteallButton = new Button("批量删除");
        addButton.setFont(new Font("黑体", 18));
        deleteButton.setFont(new Font("黑体", 18));
        okButton.setFont(new Font("黑体", 18));
        refreshtableButton.setFont(new Font("黑体", 18));
        credieupdateButton.setFont(new Font("黑体", 18));
        chooseButton.setFont(new Font("黑体", 18));
        deleteallButton.setFont(new Font("黑体", 18));

        ComboBox<String> searchCB = new ComboBox<>();
        searchCB.setPromptText("请选择");
        searchCB.getItems().addAll("全部", "借阅证编号", "姓名");
        GridPane labelledGP = new GridPane();

//        信息初始化
        usernumSQL = "select count(rid) from readers";
        readersnumTF.setText(getcount(usernumSQL) + "");

//        事件处理
        readerstableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        readerstableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ObservableList<allentity> a = readerstableView.getSelectionModel().getSelectedItems();
                for (int i = 0; i < a.size(); i++) {
                    arrayListselect.add(a.get(i).getRid());
                }
            } catch (Exception e) {
                System.out.print("");
            }
        });
        readerstableView.setRowFactory(tv -> {
            TableRow<allentity> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    rdiTF.setText(row.getItem().getRid());
                    rnameTF.setText(row.getItem().getRname());
                }
            });
            return row;
        });
        addButton.setOnAction(event -> {
            try {
                String insertSQL = "insert into readers(rid,rpassword,rname,rsex,rphone,rcredit) values(?,?,?,?,?,?)";
                String isSQL = "select rid from readers where rid = ?";
                String sex = (Integer.parseInt(rdiTF.getText().substring(1, 2)) % 2 == 0) ? "女" : "男";
                if (!rdiTF.getText().isEmpty()) {
                    try {
                        Long.parseLong(rdiTF.getText());
                        if (rdiTF.getText().length() == 10) {
                            if (!rnameTF.getText().isEmpty()) {
                                if (!JDBCutils.isexistence(isSQL, rdiTF.getText())) {
                                    JDBCutils.setADM(insertSQL, rdiTF.getText().split("\t")[0], rdiTF.getText(), rnameTF.getText(), sex, "---", 100);
                                    labelled = new Label("添加成功");
                                    sql = "SELECT rid ,rpassword,rname ,rage ,rsex,rphone ,rcredit FROM readers";
                                    readerstableView.setItems(mainStage.gettablevalue(sql));
                                    usernumSQL = "select count(rid) from readers";
                                    readersnumTF.setText(getcount(usernumSQL) + "");
                                    rdiTF.clear();
                                    rnameTF.clear();
                                } else {
                                    labelled = new Label("读者已经存在");
                                }
                            } else {
                                labelled = new Label("姓名为空");
                            }
                        } else {
                            labelled = new Label("借阅证编号不是10位");
                        }
                    } catch (Exception e) {
                        labelled = new Label("借阅证编号输入不是数字");
                    }
                } else {
                    labelled = new Label("借阅证编号为空");
                }
            } catch (Exception e) {
                labelled = new Label("借阅证编号输入不是数字");
            }
            labelled.setFont(new Font("黑体", 20));
            labelled.setTextFill(Color.RED);
            labelledGP.add(labelled, 1, 5);
            labelledGP.setAlignment(Pos.CENTER);
        });
        deleteButton.setOnAction(event -> {
            String deleteSQL = "delete from readers where rid = ?";
            String isSQL = "select rid from readers where rid = ?";
            String isnotreturn = "select rid from borrowing where rid = ? and remarks = '未归还' ";
            if (!rdiTF.getText().isEmpty()) {
                if (JDBCutils.isexistence(isSQL, rdiTF.getText())) {
                    if (!JDBCutils.isexistence(isnotreturn, rdiTF.getText())) {
                        JDBCutils.setADM(deleteSQL, rdiTF.getText());
                        labelled = new Label("删除成功");
                        sql = "SELECT rid ,rpassword,rname ,rage ,rsex,rphone ,rcredit FROM readers";
                        readerstableView.setItems(mainStage.gettablevalue(sql));
                        String delde = "delete from borrowing where rid = ?";
                        JDBCutils.setADM(delde, rdiTF.getText());
                        usernumSQL = "select count(rid) from readers";
                        readersnumTF.setText(getcount(usernumSQL) + "");
                        rdiTF.clear();
                        rnameTF.clear();
                    } else {
                        labelled = new Label("读者" + rdiTF.getText() + "有未归还的书籍，不可删除");
                    }
                } else {
                    labelled = new Label("读者不存在");
                }
            } else {
                labelled = new Label("借阅证编号为空");
            }
            labelled.setFont(new Font("黑体", 20));
            labelled.setTextFill(Color.RED);
            labelledGP.add(labelled, 0, 5);
            labelledGP.setAlignment(Pos.CENTER);
        });
        credieupdateButton.setOnAction(event -> updatecreditstage(rdiTF.getText()).show());
        okButton.setOnAction(event -> {
            if ("全部".equals(searchCB.getValue())) {
                sql = "SELECT rid ,rpassword,rname ,rage ,rsex,rphone ,rcredit FROM readers";
            } else if ("借阅证编号".equals(searchCB.getValue())) {
                sql = "SELECT rid ,rpassword,rname ,rage ,rsex,rphone ,rcredit FROM readers where rid like '%" + readerssearchTF.getText() + "%'";
            } else if ("姓名".equals(searchCB.getValue())) {
                sql = "SELECT rid ,rpassword,rname ,rage ,rsex,rphone ,rcredit FROM readers where rname like '%" + readerssearchTF.getText() + "%'";
            } else {
                labelled = new Label("出错");
                labelled.setFont(new Font("黑体", 20));
                labelled.setTextFill(Color.RED);
                labelledGP.add(labelled, 0, 5);
                labelledGP.setAlignment(Pos.CENTER);
            }
            readerstableView.setItems(mainStage.gettablevalue(sql));
        });
        refreshtableButton.setOnAction(event -> {
            rdiTF.clear();
            rnameTF.clear();
            readerssearchTF.clear();
            sql = "SELECT rid ,rpassword,rname ,rage ,rsex,rphone ,rcredit FROM readers";
            readerstableView.setItems(mainStage.gettablevalue(sql));
            usernumSQL = "select count(rid) from readers";
            readersnumTF.setText(getcount(usernumSQL) + "");
        });
        searchCB.setOnAction(event -> {
            readerssearchTF.clear();
            if ("全部".equals(searchCB.getValue())) {
                sql = "SELECT rid ,rpassword,rname ,rage ,rsex,rphone ,rcredit FROM readers ";
                readerstableView.setItems(mainStage.gettablevalue(sql));
            }
        });
        chooseButton.setOnAction(event -> {
            try {
                choosestage(2, labelledGP).show();
            } catch (Exception e) {
                labelled = new Label("出错");
            }
        });
        deleteallButton.setOnAction(event -> {
            String deleteSQL = "delete from readers where rid = ?";
            String isSQL = "select rid from readers where rid = ?";
            String isnotreturn = "select rid from borrowing where rid = ? and remarks = '未归还' ";
            deletestage(deleteSQL, isSQL, isnotreturn, labelledGP, 2).show();
        });
        if (labelled != null) {
            rdiTF.setOnMouseClicked(event -> labelled.setVisible(false));
            rnameTF.setOnMouseClicked(event -> labelled.setVisible(false));
            readerssearchTF.setOnMouseClicked(event -> labelled.setVisible(false));
            searchCB.setOnMouseClicked(event -> labelled.setVisible(false));
            refreshtableButton.setOnMouseClicked(event -> labelled.setVisible(false));
            readerstableView.setOnMouseClicked(event -> labelled.setVisible(false));
        }

        HBox firstHB = new HBox();
        HBox secondHB = new HBox();
        HBox thirdHB = new HBox();
        firstHB.setSpacing(20);
        secondHB.setSpacing(20);
        firstHB.setAlignment(Pos.CENTER);
        secondHB.setAlignment(Pos.CENTER);
        thirdHB.setAlignment(Pos.CENTER);
        firstHB.getChildren().addAll(ridlabel, rdiTF, rnamelabel, rnameTF, addButton, deleteButton, credieupdateButton);
        secondHB.getChildren().addAll(readersnumlabel, readersnumTF, searchCB, readerssearchTF, okButton, refreshtableButton, chooseButton, deleteallButton);
        thirdHB.getChildren().addAll(readerstableView);

        VBox rootnodeVB = new VBox();
        rootnodeVB.setPadding(new Insets(0, 0, 0, 0));
        rootnodeVB.setSpacing(15);
        rootnodeVB.getChildren().addAll(menubarGP, firstHB, secondHB, labelledGP, thirdHB);

        Scene scene = new Scene(rootnodeVB, 1500, 900);
        scene.setOnMouseClicked(event -> {
            if (labelled != null) {
                labelled.setVisible(false);
            }
        });
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("管理员 读者管理");
        return stage;
    }

    /*借阅管理界面*/
    public Stage adminborrowstage() {
        MainStage mainStage = new MainStage();
        Stage stage = new Stage();
        MenuBar menuBar = new MenuBar();
        Menu homepageMenu = new Menu();
        Menu idMenu = new Menu();
        Label homepagelabel = new Label("主页");
        Label idlabel = new Label("您好，" + MainStage.ID);
        homepagelabel.setFont(new Font("黑体", 18));
        idlabel.setFont(new Font("黑体", 16));
        homepageMenu.setGraphic(homepagelabel);
        idMenu.setGraphic(idlabel);
        menuBar.getMenus().addAll(homepageMenu, idMenu);
        BorderPane menubarGP = new BorderPane();
        menubarGP.setTop(menuBar);
        homepagelabel.setOnMouseClicked(event -> {
            try {
                mainStage.adminmainstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });

        sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid";
        borrowingtableView.setItems(mainStage.gettablevalue(sql));
        borrowingtableView.setPrefSize(1475, 900);
        borrowingtableView.setMaxWidth(1474);
        TableColumn<allentity, String> bisbncolumn1 = new TableColumn<>("ISBN");
        TableColumn<allentity, String> ridcolumn1 = new TableColumn<>("借阅证编号");
        TableColumn<allentity, String> borrowingdatecolumn = new TableColumn<>("借书日期");
        TableColumn<allentity, String> duedatecolumn = new TableColumn<>("截止还书日期");
        TableColumn<allentity, String> returndatecolumn = new TableColumn<>("还书日期");
        TableColumn<allentity, String> remarkscolumn = new TableColumn<>("状态");
        TableColumn<allentity, String> rphonescolumn = new TableColumn<>("联系方式");
        TableColumn<allentity, String> rnamecolumn = new TableColumn<>("姓名");
        TableColumn<allentity, String> bnamescolumn = new TableColumn<>("书名");
        TableColumn<allentity, String> bnumcolumn = new TableColumn<>("数量");
        bisbncolumn1.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
        bisbncolumn1.setPrefWidth(150);
        ridcolumn1.setCellValueFactory(new PropertyValueFactory<>("rid"));
        ridcolumn1.setPrefWidth(120);
        borrowingdatecolumn.setCellValueFactory(new PropertyValueFactory<>("borrowingdate"));
        borrowingdatecolumn.setPrefWidth(120);
        duedatecolumn.setCellValueFactory(new PropertyValueFactory<>("duedate"));
        duedatecolumn.setPrefWidth(120);
        returndatecolumn.setCellValueFactory(new PropertyValueFactory<>("returndate"));
        returndatecolumn.setPrefWidth(120);
        remarkscolumn.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        remarkscolumn.setPrefWidth(100);
        rphonescolumn.setCellValueFactory(new PropertyValueFactory<>("rphone"));
        rphonescolumn.setPrefWidth(150);
        rnamecolumn.setCellValueFactory(new PropertyValueFactory<>("rname"));
        rnamecolumn.setPrefWidth(120);
        bnamescolumn.setCellValueFactory(new PropertyValueFactory<>("bname"));
        bnamescolumn.setPrefWidth(300);
        bnumcolumn.setCellValueFactory(new PropertyValueFactory<>("borrownum"));
        bnumcolumn.setPrefWidth(120);
        borrowingtableView.getColumns().addAll(bisbncolumn1, bnamescolumn, ridcolumn1, rnamecolumn, rphonescolumn, borrowingdatecolumn, duedatecolumn, returndatecolumn, bnumcolumn, remarkscolumn);

//       添加各种控件
        Label searchtypelabel = new Label("搜索类型");
        Label searchrangelebel = new Label("搜索范围");
        Label counttypelabel = new Label("统计类型");
        searchrangelebel.setFont(new Font("黑体", 20));
        searchtypelabel.setFont(new Font("黑体", 20));
        counttypelabel.setFont(new Font("黑体", 20));

        TextField counttypetf = new TextField();
        counttypetf.setEditable(false);
        borrowsearchTF.setPromptText("搜索内容");

        Button okButton = new Button("搜索");
        Button refreshtableButton = new Button("刷新");
        refreshtableButton.setFont(new Font("黑体", 18));
        okButton.setFont(new Font("黑体", 18));

        ComboBox<String> searchtypeCB = new ComboBox<>();
        ComboBox<String> searchrangeCB = new ComboBox<>();
        ComboBox<String> counttypeCB = new ComboBox<>();
        searchtypeCB.setPromptText("请选择");
        searchrangeCB.setPromptText("请选择");
        counttypeCB.setPromptText("请选择");
        searchtypeCB.getItems().addAll("借阅证编号", "ISBN", "姓名", "书名");
        searchrangeCB.getItems().addAll("全部", "已归还", "未归还", "逾期未归还");
        counttypeCB.getItems().addAll("已归还图书总数目", "未归还图书总数目", "逾期未归还图书总数目", "已归还图书种类数目", "未归还图书种类数目", "逾期未归还图书种类数目");

//        事件处理
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        snowadate = simpleDateFormat.format(nowadate);
        counttypeCB.setOnAction(event -> {
            String countSQL = null;
            sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid";
            borrowingtableView.setItems(mainStage.gettablevalue(sql));
            if ("已归还图书总数目".equals(counttypeCB.getValue())) {
                countSQL = "select sum(borrownum) from borrowing where remarks = '已归还' ";
            } else if ("未归还图书总数目".equals(counttypeCB.getValue())) {
                countSQL = "select sum(borrownum) from borrowing where remarks = '未归还'";
            } else if ("逾期未归还图书总数目".equals(counttypeCB.getValue())) {
                countSQL = "select sum(borrownum) from borrowing where remarks = '未归还' and duedate < ' " + snowadate + " ' ";
            } else if ("已归还图书种类数目".equals(counttypeCB.getValue())) {
                countSQL = "select count(bisbn) from borrowing where remarks = '已归还'";
            } else if ("未归还图书种类数目".equals(counttypeCB.getValue())) {
                countSQL = "select count(bisbn) from borrowing where remarks = '未归还'";
            } else if ("逾期未归还图书种类数目".equals(counttypeCB.getValue())) {
                countSQL = "select count(bisbn) from borrowing where remarks = '未归还' and duedate < ' " + snowadate + " ' ";
            }
            try {
                counttypetf.setText(getcount(countSQL) + "");
            } catch (Exception e) {
                labelled = new Label("请选择统计类型");
            }
        });

        refreshtableButton.setOnAction(event -> {
            borrowsearchTF.clear();
            searchrangeCB.setValue("全部");
            sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid";
            borrowingtableView.setItems(mainStage.gettablevalue(sql));
        });
        okButton.setOnAction(event -> {
            if ("全部".equals(searchrangeCB.getValue())) {
                if ("ISBN".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and borrowing.bisbn like '%" + borrowsearchTF.getText() + "%'";
                } else if ("借阅证编号".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and borrowing.rid like '%" + borrowsearchTF.getText() + "%'";
                } else if ("书名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and bname like '%" + borrowsearchTF.getText() + "%'";
                } else if ("姓名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and rname like '%" + borrowsearchTF.getText() + "%'";
                }
            } else if ("已归还".equals(searchrangeCB.getValue())) {
                if ("ISBN".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and remarks = '已归还' and borrowing.bisbn like '%" + borrowsearchTF.getText() + "%'";
                } else if ("借阅证编号".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and remarks = '已归还' and borrowing.rid like '%" + borrowsearchTF.getText() + "%'";
                } else if ("书名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and remarks = '已归还' and bname like '%" + borrowsearchTF.getText() + "%'";
                } else if ("姓名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and remarks = '已归还' and rname like '%" + borrowsearchTF.getText() + "%'";
                }
            } else if ("未归还".equals(searchrangeCB.getValue())) {
                if ("ISBN".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and remarks = '未归还' and borrowing.bisbn like '%" + borrowsearchTF.getText() + "%'";
                } else if ("借阅证编号".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and remarks = '未归还' and borrowing.rid like '%" + borrowsearchTF.getText() + "%'";
                } else if ("书名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and remarks = '未归还' and bname like '%" + borrowsearchTF.getText() + "%'";
                } else if ("姓名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and remarks = '未归还' and rname like '%" + borrowsearchTF.getText() + "%'";
                }
            } else if ("逾期未归还".equals(searchrangeCB.getValue())) {
                if ("ISBN".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and duedate < '" + snowadate + "' and borrowing.bisbn like '%" + borrowsearchTF.getText() + "%' and remarks = '未归还'";
                } else if ("借阅证编号".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and duedate < '" + snowadate + "' and borrowing.rid like '%" + borrowsearchTF.getText() + "%' and remarks = '未归还'";
                } else if ("书名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and duedate < '" + snowadate + "' and bname like '%" + borrowsearchTF.getText() + "%' and remarks = '未归还'";
                } else if ("姓名".equals(searchtypeCB.getValue())) {
                    sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and duedate < '" + snowadate + "' and rname like '%" + borrowsearchTF.getText() + "%' and remarks = '未归还'";
                }
            }
            borrowingtableView.setItems(mainStage.gettablevalue(sql));
        });
        searchrangeCB.setOnAction(event -> {
            borrowsearchTF.clear();
            if ("全部".equals(searchrangeCB.getValue())) {
                sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid";
            } else if ("已归还".equals(searchrangeCB.getValue())) {
                sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and remarks = '已归还'";
            } else if ("未归还".equals(searchrangeCB.getValue())) {
                sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid and remarks = '未归还'";
            } else if ("逾期未归还".equals(searchrangeCB.getValue())) {
                sql = "select borrowing.bisbn,bname,borrowing.rid ,rname,rphone,borrowingdate ,duedate ,returndate,borrownum,remarks from borrowing,books,readers where borrowing.bisbn = books.bisbn and borrowing.rid = readers.rid  and duedate < '" + snowadate + "' and remarks = '未归还' ";
            }
            borrowingtableView.setItems(mainStage.gettablevalue(sql));
        });
        searchtypeCB.setOnAction(event -> borrowsearchTF.clear());
        if (labelled != null) {
            borrowsearchTF.setOnMouseClicked(event -> labelled.setVisible(false));
            borrowingtableView.setOnMouseClicked(event -> labelled.setVisible(false));
            searchrangeCB.setOnMouseClicked(event -> labelled.setVisible(false));
            counttypeCB.setOnMouseClicked(event -> labelled.setVisible(false));
        }

        HBox firstHB = new HBox();
        HBox secondHB = new HBox();
        HBox thirdHB = new HBox();
        firstHB.setSpacing(20);
        secondHB.setSpacing(20);
        firstHB.setAlignment(Pos.CENTER);
        secondHB.setAlignment(Pos.CENTER);
        thirdHB.setAlignment(Pos.CENTER);
        firstHB.getChildren().addAll(counttypelabel, counttypeCB, counttypetf);
        secondHB.getChildren().addAll(searchrangelebel, searchrangeCB, searchtypelabel, searchtypeCB, borrowsearchTF, okButton, refreshtableButton);
        thirdHB.getChildren().addAll(borrowingtableView);

        VBox rootnodeVB = new VBox();
        rootnodeVB.setPadding(new Insets(0, 0, 0, 0));
        rootnodeVB.setSpacing(15);
        rootnodeVB.getChildren().addAll(menubarGP, firstHB, secondHB, thirdHB);

        Scene scene = new Scene(rootnodeVB, 1500, 900);
        scene.setOnMouseClicked(event -> {
            if (labelled != null) {
                labelled.setVisible(false);
            }
        });
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("管理员 借阅管理");
        return stage;
    }

    /*查询聚集函数*/
    private long getcount(String sql) {
        return JDBCutils.getcount(sql);
    }

    /*更改信用分界面*/
    private Stage updatecreditstage(String rid) {

        Stage stage = new Stage();
        Label ridlabel = new Label("借阅证编号");
        Label creditlabel = new Label("信 用 分");
        ridlabel.setFont(new Font("黑体", 20));
        creditlabel.setFont(new Font("黑体", 20));
        TextField ridTF = new TextField();
        TextField creditTF = new TextField();
        ridTF.setPromptText("借阅证编号");
        creditTF.setPromptText("更改在0-100之间的信用分");
        ridTF.setText(rid);

        Button okButton = new Button("确定");
        Button cancelButton = new Button("取消");
        okButton.setFont(new Font("黑体", 20));
        cancelButton.setFont(new Font("黑体", 20));

        HBox buttonHB = new HBox();
        buttonHB.setSpacing(60);
        buttonHB.setPadding(new Insets(0, 0, 0, 0));
        buttonHB.setAlignment(Pos.CENTER);
        buttonHB.getChildren().addAll(cancelButton, okButton);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20, 0, 0, 32));
        GridPane.setHalignment(ridlabel, HPos.RIGHT);
        GridPane.setHalignment(creditlabel, HPos.RIGHT);
        gridPane.add(ridlabel, 0, 1);
        gridPane.add(ridTF, 1, 1);
        gridPane.add(creditlabel, 0, 2);
        gridPane.add(creditTF, 1, 2);
        gridPane.add(buttonHB, 1, 3);

        cancelButton.setOnAction(event -> stage.close());
        okButton.setOnAction(event -> {

            if (!ridTF.getText().isEmpty()) {
                try {
                    Long.parseLong(ridTF.getText());
                    if (ridTF.getText().length() == 10) {
                        String isridSQL = "select rid from readers where rid = ?";
                        if (JDBCutils.isexistence(isridSQL, ridTF.getText())) {
                            if (!creditTF.getText().isEmpty()) {
                                try {
                                    Long.parseLong(creditTF.getText());
                                    if (Long.parseLong(creditTF.getText()) >= 0 && Long.parseLong(creditTF.getText()) <= 100) {
                                        String updatecreditSQL = "update readers set rcredit = ? where rid = ?";
                                        JDBCutils.setADM(updatecreditSQL, creditTF.getText(), ridTF.getText());
                                        labelled = new Label("修改成功");
                                        sql = "SELECT rid ,rpassword,rname ,rage ,rsex,rphone ,rcredit FROM readers";
                                        MainStage mainStage = new MainStage();
                                        readerstableView.setItems(mainStage.gettablevalue(sql));
                                        stage.close();
                                    } else {
                                        labelled = new Label("请输入0-100之间的信用分");
                                    }
                                } catch (NumberFormatException e) {
                                    labelled = new Label("信用分请输入数字");
                                }
                            } else {
                                labelled = new Label("信用分为空");
                            }
                        } else {
                            labelled = new Label("借阅证编号不存在");
                        }
                    } else {
                        labelled = new Label("借阅证编号不是10位");
                    }
                } catch (Exception e) {
                    labelled = new Label("借阅证编号不是数字");
                }
            } else {
                labelled = new Label("借阅证编号为空");
            }
            labelled.setFont(new Font("黑体", 18));
            labelled.setTextFill(Color.RED);
            labelled.setAlignment(Pos.CENTER);
            gridPane.add(labelled, 1, 4);
        });
        Scene scene = new Scene(gridPane, 400, 260);
        if (labelled != null) {
            ridTF.setOnMouseClicked(event -> labelled.setVisible(false));
            creditTF.setOnMouseClicked(event -> labelled.setVisible(false));
            scene.setOnMouseClicked(event -> labelled.setVisible(false));
        }
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        return stage;
    }

    /*批量上传图书界面*/
    private Stage choosestage(int a, GridPane gridPanelabel) {
        Stage stage = new Stage();
        Label fielnamelabel = new Label("文 件 名");
        Label tits = new Label("注意从左到右每列值与文本框对应");
        fielnamelabel.setFont(new Font("黑体", 20));
        tits.setFont(new Font("黑体", 15));
        tits.setTextFill(Color.RED);
        TextField fielnameTF = new TextField();
        fielnameTF.setPromptText("检查文件名");
        fielnameTF.setEditable(false);
        fielnameTF.setPrefWidth(200);

        Button okButton = new Button("确定");
        Button cancelButton = new Button("取消");
        Button choose = new Button("选择文件");
        okButton.setFont(new Font("黑体", 20));
        cancelButton.setFont(new Font("黑体", 20));
        choose.setFont(new Font("黑体", 20));

        HBox buttonHB = new HBox();
        buttonHB.setSpacing(60);
        buttonHB.setPadding(new Insets(0, 0, 0, 0));
        buttonHB.setAlignment(Pos.CENTER);
        buttonHB.getChildren().addAll(cancelButton, okButton);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20, 0, 0, 32));
        GridPane.setHalignment(fielnamelabel, HPos.CENTER);
        gridPane.add(fielnamelabel, 0, 2);
        gridPane.add(fielnameTF, 1, 2);
        gridPane.add(choose, 0, 1);
        gridPane.add(tits, 1, 1);
        gridPane.add(buttonHB, 1, 3);
        cancelButton.setOnAction(event -> stage.close());
        okButton.setOnAction(event -> {
            if (file != null) {
                try {
                    InputStream inputStream = new FileInputStream(file);
                    try {
                        Workbook workbook = Workbook.getWorkbook(inputStream);
                        int sheetnum = workbook.getNumberOfSheets();
                        if (a == 1) {
                            for (int i = 0; i < sheetnum; i++) {
                                Sheet sheet = workbook.getSheet(i);
                                if (sheet.getColumns() >= 6) {
                                    int row = sheet.getRows();
                                    for (int j = 0; j < row; j++) {
                                        if (!sheet.getCell(0, j).getContents().isEmpty()) {
                                            try {
                                                Long.parseLong(sheet.getCell(5, j).getContents());
                                                String isSQL = "select bisbn from books where bisbn = ?";
                                                if (!JDBCutils.isexistence(isSQL, sheet.getCell(0, j).getContents())) {
                                                    String SQL = "insert into books(bisbn,bname,bauthor,bcategory,bprice,bcount) values(?,?,?,?,?,?)";
                                                    JDBCutils.setADM(SQL, sheet.getCell(0, j).getContents().split("\t")[0], sheet.getCell(1, j).getContents(), sheet.getCell(2, j).getContents(), sheet.getCell(3, j).getContents(), sheet.getCell(4, j).getContents(), sheet.getCell(5, j).getContents());
                                                }
                                            } catch (Exception e) {
                                                continue;
                                            }
                                        }
                                    }
                                    labelled = new Label("批量添加成功");
                                    labelled.setFont(new Font("黑体", 20));
                                    labelled.setTextFill(Color.RED);
                                    gridPanelabel.add(labelled, 0, 4);
                                    gridPanelabel.setAlignment(Pos.CENTER);
                                    stage.close();
                                } else {
                                    label = new Label("表格缺少列，请检查");
                                }
                            }
                        } else if (a == 2) {
                            for (int i = 0; i < sheetnum; i++) {
                                Sheet sheet = workbook.getSheet(i);
                                if (sheet.getColumns() >= 2) {
                                    int row = sheet.getRows();
                                    for (int j = 0; j < row; j++) {
                                        if (!sheet.getCell(0, j).getContents().isEmpty()) {
                                            try {
                                                Long.parseLong(sheet.getCell(0, j).getContents());
                                                String isSQL = "select rid from readers where rid = ?";
                                                if (sheet.getCell(0, j).getContents().length() == 10 && !JDBCutils.isexistence(isSQL, sheet.getCell(0, j).getContents())) {
                                                    String insertSQL = "insert into readers(rid,rpassword,rname,rsex,rphone,rcredit) values(?,?,?,?,?,?)";
                                                    String sex = (Long.parseLong(sheet.getCell(0, j).getContents().substring(1, 2)) % 2 == 0) ? "女" : "男";
                                                    JDBCutils.setADM(insertSQL, sheet.getCell(0, j).getContents().split("\t")[0], sheet.getCell(0, j).getContents(), sheet.getCell(1, j).getContents(), sex, "---", 100);
                                                }
                                            } catch (NumberFormatException e) {
                                                continue;
                                            }
                                        }
                                    }
                                    labelled = new Label("批量添加成功,请刷新数据");
                                    labelled.setFont(new Font("黑体", 20));
                                    labelled.setTextFill(Color.RED);
                                    gridPanelabel.add(labelled, 0, 4);
                                    gridPanelabel.setAlignment(Pos.CENTER);
                                    stage.close();
                                } else {
                                    label = new Label("表格缺少列，请检查");
                                }
                            }
                        } else {
                            label = new Label("出错");
                        }
                    } catch (IOException | BiffException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                label = new Label("请先选择xls文件");
            }
            label.setFont(new Font("黑体", 18));
            label.setTextFill(Color.RED);
            label.setAlignment(Pos.CENTER);
            gridPane.add(label, 1, 4);

        });
        choose.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择xls文件");
            file = fileChooser.showOpenDialog(stage);
            try {
                if ("xls".equals(file.getName().substring(file.getName().length() - 3))) {
                    try {
                        fielnameTF.setText(file.getName());
                    } catch (Exception e) {
                        labelled = new Label("文件解析出错，请另保存后上传");
                    }
                } else {
                    labelled = new Label("请上传.xls文件");
                }
            } catch (Exception e) {
                labelled = new Label();
            }
            labelled.setFont(new Font("黑体", 18));
            labelled.setTextFill(Color.RED);
            labelled.setAlignment(Pos.CENTER);
            try {
                gridPane.add(labelled, 1, 4);
            } catch (Exception e) {
                labelled = new Label();
            }
        });

        Scene scene = new Scene(gridPane, 400, 270);
        if (labelled != null || label != null) {
            fielnameTF.setOnMouseClicked(event -> labelled.setVisible(false));
            fielnameTF.setOnMouseClicked(event -> label.setVisible(false));
            scene.setOnMouseClicked(event -> labelled.setVisible(false));
            scene.setOnMouseClicked(event -> label.setVisible(false));
        }
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        return stage;
    }

    /*批量删除界面*/
    private Stage deletestage(String SQL, String isSQL, String isduedateSQL, GridPane gridPane, int num) {
        Stage stage = new Stage();
        Button cancel = new Button("重新选择");
        Button ok = new Button("确定选择");
        Button delete = new Button("批量删除");
        cancel.setFont(new Font("黑体", 20));
        ok.setFont(new Font("黑体", 20));
        delete.setFont(new Font("黑体", 20));
        TextArea textArea = new TextArea();
        textArea.setPrefWidth(630);
        textArea.setPrefHeight(300);
        textArea.setWrapText(true);
        Label labeltip = new Label("！！！注意：用Tab键间隔每一个编号！！！");
        labeltip.setFont(new Font("黑体", 18));
        labeltip.setTextFill(Color.RED);
        GridPane rootgridPane = new GridPane();

        ok.setOnAction(event -> {
            for (int i = 0; i < arrayListselect.size(); i++) {
                for (int j = i + 1; j < arrayListselect.size(); j++) {
                    if (arrayListselect.get(i).equals(arrayListselect.get(j))) {
                        arrayListselect.set(i, "0");
                        break;
                    }
                }
            }
            for (String s : arrayListselect) {
                if (!"0".equals(s)) {
                    textArea.appendText(s + "\t");
                }
            }
            arrayListselect.clear();
            label = new Label("添加成功，请检查无误后点击删除键删除");
            label.setTextFill(Color.RED);
            label.setFont(new Font("黑体", 20));
            GridPane.setHalignment(label, HPos.CENTER);
            rootgridPane.add(label, 0, 3);
        });

        delete.setOnAction(event -> {
            String[] strings = textArea.getText().split("\t");
            for (String a : strings) {
                if (!a.isEmpty() && JDBCutils.isexistence(isSQL, a) && !JDBCutils.isexistence(isduedateSQL, a)) {
                    if (num == 1) {
                        JDBCutils.setADM(SQL, a);
                    }
                    if (num == 2) {
                        String deleteSQL = "delete from borrowing where rid = ?";
                        JDBCutils.setADM(SQL, a);
                        JDBCutils.setADM(deleteSQL, a);
                    }
                }
            }
            labelled = new Label("删除成功，请刷新数据,存在未归还的数据尚未删除");
            labelled.setFont(new Font("黑体", 20));
            labelled.setTextFill(Color.RED);
            gridPane.add(labelled, 0, 4);
            gridPane.setAlignment(Pos.CENTER);
            stage.close();
        });

        cancel.setOnAction(event -> stage.close());
        HBox buttonHB = new HBox();
        buttonHB.setSpacing(65);
        buttonHB.setAlignment(Pos.CENTER);
        buttonHB.getChildren().addAll(cancel, delete, ok);

        GridPane.setHalignment(textArea, HPos.CENTER);
        GridPane.setHalignment(buttonHB, HPos.CENTER);
        GridPane.setHalignment(labeltip, HPos.CENTER);
        rootgridPane.setVgap(20);
        rootgridPane.setAlignment(Pos.TOP_CENTER);
        rootgridPane.setPadding(new Insets(15, 0, 0, 0));
        rootgridPane.add(labeltip, 0, 0);
        rootgridPane.add(textArea, 0, 1);
        rootgridPane.add(buttonHB, 0, 2);
        Scene scene = new Scene(rootgridPane, 655, 480);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        if (label != null || labelled != null) {
            scene.setOnMouseClicked(event -> labelled.setVisible(false));
            scene.setOnMouseClicked(event -> label.setVisible(false));
            textArea.setOnMouseClicked(event -> labelled.setVisible(false));
            textArea.setOnMouseClicked(event -> label.setVisible(false));
        }
        return stage;
    }
}