package Stages;
/**
 * @author hongxiaobin
 * @create 2021/12/1-10:50
 * @function 设置登录主界面
 */

import JDBCUtils.JDBCutils;
import JDBCUtils.allentity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class MainStage {
    public static String ID;
    private final java.util.Date nowadate = new java.util.Date();
    private Label labelled = new Label();
    private String snowadate;

    public Stage mainstage() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        snowadate = simpleDateFormat.format(nowadate);
        Stage stage = new Stage();
        Label mainlabel = new Label("图 书 管 理 系 统");
        Label accountlabel = new Label("账    号");
        Label passwordlabel = new Label("密    码");
        Label rolelabel = new Label("角    色");
        Label verificationcodelabel = new Label("验证码");
        Label retrievepasswordlabel = new Label("找回密码");
        mainlabel.setFont(new Font("黑体", 30));
        accountlabel.setFont(new Font("黑体", 20));
        passwordlabel.setFont(new Font("黑体", 20));
        rolelabel.setFont(new Font("黑体", 20));
        verificationcodelabel.setFont(new Font("黑体", 20));
        retrievepasswordlabel.setFont(new Font("黑体", 15));
        retrievepasswordlabel.setTextFill(Color.RED);

        TextField accounttf = new TextField();
        PasswordField passwordpf = new PasswordField();
        TextField verificationcodetf = new TextField();
        accounttf.setPromptText("借阅证编号或管理员ID");
        passwordpf.setPromptText("请输入正确的密码");
        verificationcodetf.setPromptText("请输入验证码");
        Button loginbutton = new Button("登   录");
        Button registeredbutton = new Button("用户注册");
        loginbutton.setFont(new Font("黑体", 20));
        registeredbutton.setFont(new Font("黑体", 20));
//        设置验证码(只能大写)
        char a = (char) (Math.random() * 25 + 65);
        char b = (char) (Math.random() * 25 + 65);
        char c = (char) (Math.random() * 25 + 65);
        char d = (char) (Math.random() * 25 + 65);
        String code = a + "" + b + "" + c + "" + d;
        Label codelabel = new Label(code);
        codelabel.setVisible(false);
        codelabel.setFont(new Font("黑体", 20));
        codelabel.setTextFill(Color.RED);
//        设置选择框
        final ComboBox<String> rolecb = new ComboBox<>();
        rolecb.getItems().addAll("用户", "管理员");
        rolecb.setPromptText("请选择···");
//        创建一个水平空间框，放置两个按钮
        HBox buttonhb = new HBox();
        buttonhb.setSpacing(45);
        buttonhb.setAlignment(Pos.CENTER);
        buttonhb.setPadding(new Insets(15, 0, 10, 0));
        buttonhb.getChildren().addAll(registeredbutton, loginbutton);

        HBox mainlabelHB = new HBox();
        mainlabelHB.setAlignment(Pos.CENTER);
        mainlabelHB.setPadding(new Insets(20, 0, 0, 0));
        mainlabelHB.getChildren().add(mainlabel);

        GridPane rootnode = new GridPane();
        GridPane.setHalignment(accountlabel, HPos.RIGHT);
        GridPane.setHalignment(passwordlabel, HPos.RIGHT);
        GridPane.setHalignment(rolelabel, HPos.RIGHT);
        GridPane.setHalignment(verificationcodelabel, HPos.RIGHT);
        rootnode.setHgap(10);
        rootnode.setVgap(15);
        rootnode.setPadding(new Insets(20, 0, 0, 42));
        rootnode.add(accountlabel, 1, 0);
        rootnode.add(passwordlabel, 1, 1);
        rootnode.add(accounttf, 2, 0);
        rootnode.add(passwordpf, 2, 1);
        rootnode.add(retrievepasswordlabel, 3, 1);
        rootnode.add(verificationcodelabel, 1, 2);
        rootnode.add(verificationcodetf, 2, 2);
        rootnode.add(codelabel, 3, 2);
        rootnode.add(rolelabel, 1, 3);
        rootnode.add(rolecb, 2, 3);
        rootnode.add(buttonhb, 2, 4);

//        登录按钮事件
        verificationcodetf.setOnMouseClicked(event -> codelabel.setVisible(true));
        loginbutton.setOnAction(event -> {
            String getpassword;
            String readersselectSQL = "select rid,rpassword from readers where rid = ?";
            String adminselectSQL = "select aid,apassword from administrators where aid = ?";
            if (!accounttf.getText().isEmpty()) {
                if (accounttf.getText().length() == 10) {
                    if (rolecb.getValue() != null) {
                        if (verificationcodetf.getText().equalsIgnoreCase(code)) {
                            if ("用户".equals(rolecb.getValue())) {
                                if (JDBCutils.isexistence(readersselectSQL, accounttf.getText())) {
                                    getpassword = Objects.requireNonNull(JDBCutils.Login(readersselectSQL, accounttf.getText()))[1];
                                    if (passwordpf.getText().equals(getpassword)) {
                                        ID = accounttf.getText();
                                        String rcredirSQL = "select rcredit from readers where rid = '"+ID+"'";
                                        int credit = Objects.requireNonNull(JDBCutils.getselectlist(allentity.class, rcredirSQL)).get(0).getRcredit();
                                        if (credit >=70) {
                                            String selectSQL = "select rid from borrowing where rid = ? and duedate < ' " + snowadate + " ' and remarks = '未归还' " ;
                                            if (JDBCutils.isexistence(selectSQL, accounttf.getText())) {
                                                isoutdatestage().show();
                                            } else {
                                                usermainstage().show();
                                            }
                                            stage.close();
                                        } else {
                                            labelled = new Label("您的信用分过低，请联系管理员");
                                        }
                                    } else if (passwordpf.getText().isEmpty()) {
                                        labelled = new Label("密码为空");
                                    } else {
                                        labelled = new Label("密码错误");
                                    }
                                } else {
                                    labelled = new Label("账号不存在，请先注册");
                                }
                            } else if ("管理员".equals(rolecb.getValue())) {
                                if (JDBCutils.isexistence(adminselectSQL, accounttf.getText())) {
                                    getpassword = Objects.requireNonNull(JDBCutils.Login(adminselectSQL, accounttf.getText()))[1];
                                    if (passwordpf.getText().equals(getpassword)) {
                                        ID = accounttf.getText();
                                        try {
                                            adminmainstage().show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        stage.close();
                                    } else if (passwordpf.getText().isEmpty()) {
                                        labelled = new Label("密码为空");
                                    } else {
                                        labelled = new Label("密码错误");
                                    }
                                } else {
                                    labelled = new Label("账号不存在，请先注册");
                                }
                            } else {
                                labelled = new Label("系统错误！！！");
                            }
                        } else {
                            labelled = new Label("验证码错误请重新输入");
                        }
                    } else {
                        labelled = new Label("请选择角色");
                    }
                } else {
                    labelled = new Label("账号格式不正确");
                }
            } else {
                labelled = new Label("账号输入为空");
            }
            labelled.setFont(new Font("黑体", 18));
            labelled.setTextFill(Color.RED);
            try {
                rootnode.add(labelled, 2, 5);
            } catch (Exception e) {
                labelled.setVisible(false);
            }
            Label finalLabelled = labelled;
            accounttf.setOnMouseClicked(event1 -> finalLabelled.setVisible(false));
            passwordpf.setOnMouseClicked(event1 -> finalLabelled.setVisible(false));
            rolecb.setOnMouseClicked(event1 -> finalLabelled.setVisible(false));
            verificationcodetf.setOnMouseClicked(event1 -> finalLabelled.setVisible(false));
        });
//        注册按钮的事件
        registeredbutton.setOnAction(event -> {
            try {
                enrollstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
//        设置找回密码事件
        retrievepasswordlabel.setOnMouseClicked(event -> {
            try {
                retrievepasswordstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });

        VBox rootnodeVB = new VBox();
        rootnodeVB.getChildren().addAll(mainlabelHB, rootnode);
//        设置主场景
        Scene scene = new Scene(rootnodeVB, 480, 420);
        scene.setOnMouseClicked(event -> {
            if (labelled != null) {
                labelled.setVisible(false);
            }
        });
        stage.setTitle("图书管理系统");
        stage.setScene(scene);
        stage.setResizable(false);
        return stage;
    }

    /*管理员主界面*/
    public Stage adminmainstage() {
        Stage stage = new Stage();
        MenuBar menuBar = new MenuBar();
        Menu logoutMenu = new Menu();
        Menu idMenu = new Menu();
        Label logoutlabel = new Label("退出登录");
        Label idlabel = new Label("您好，" + ID);
        logoutlabel.setFont(new Font("黑体", 18));
        idlabel.setFont(new Font("黑体", 16));
        logoutMenu.setGraphic(logoutlabel);
        idMenu.setGraphic(idlabel);
        menuBar.getMenus().addAll(logoutMenu, idMenu);
        BorderPane menubarBP = new BorderPane();
        menubarBP.setTop(menuBar);

        Button booksButton = new Button("图书管理");
        Button readersButton = new Button("读者管理");
        Button borrowButton = new Button("借阅管理");
        booksButton.setFont(new Font("黑体", 30));
        readersButton.setFont(new Font("黑体", 30));
        borrowButton.setFont(new Font("黑体", 30));
//        按钮事件
        admincenter admincenter = new admincenter();
        booksButton.setOnMouseClicked(event -> {
            try {
                admincenter.adminbooksstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
        readersButton.setOnMouseClicked(event -> {
            try {
                admincenter.adminreadersstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
        borrowButton.setOnMouseClicked(event -> {
            try {
                admincenter.adminborrowstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
//        退出登录事件
        logoutlabel.setOnMouseClicked(event -> {
            isadminoutloginstage().show();
            stage.close();
        });

        VBox buttonVB = new VBox();
        buttonVB.setAlignment(Pos.CENTER);
        buttonVB.setSpacing(50);
        buttonVB.setPadding(new Insets(40, 0, 0, 0));
        buttonVB.getChildren().addAll(booksButton, readersButton, borrowButton);

        VBox rootnodeVB = new VBox();
        rootnodeVB.getChildren().addAll(menubarBP);
        rootnodeVB.getChildren().addAll(buttonVB);

        Scene scene = new Scene(rootnodeVB, 300, 420);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("管理员");
        return stage;
    }

    /*用户主界面*/
    public Stage usermainstage() {
        Stage stage = new Stage();
        String selectrnameSQL = "select rname from readers where rid = " + ID;
        List<allentity> rnameList = JDBCutils.getselectlist(allentity.class, selectrnameSQL);
        assert rnameList != null;
        String rname = rnameList.get(0).getRname();
        MenuBar menuBar = new MenuBar();
        Menu logoutMenu = new Menu();
        Menu idMenu = new Menu();
        Label logoutlabel = new Label("退出登录");
        Label idlabel = new Label("您好，" + rname + "(" + ID + ")");
        logoutlabel.setFont(new Font("黑体", 18));
        idlabel.setFont(new Font("黑体", 16));
        logoutMenu.setGraphic(logoutlabel);
        idMenu.setGraphic(idlabel);
        menuBar.getMenus().addAll(logoutMenu, idMenu);
        BorderPane menubarBP = new BorderPane();
        menubarBP.setTop(menuBar);

        Button borrowbookButton = new Button("借书中心");
        borrowbookButton.setFont(new Font("黑体", 30));
        Button returnbookButton = new Button("还书中心");
        returnbookButton.setFont(new Font("黑体", 30));
        Button personalButton = new Button("个人中心");
        personalButton.setFont(new Font("黑体", 30));
//        按钮事件
        usercenter usercenter = new usercenter();
        borrowbookButton.setOnMouseClicked(event -> {
            try {
                usercenter.userborrowstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
        returnbookButton.setOnMouseClicked(event -> {
            try {
                usercenter.userreturnstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
        personalButton.setOnMouseClicked(event -> {
            try {
                usercenter.userpersonalstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
//        退出登录事件
        logoutlabel.setOnMouseClicked(event -> {
            isuseroutloginstage().show();
            stage.close();
        });

        VBox buttonVB = new VBox();
        buttonVB.setAlignment(Pos.CENTER);
        buttonVB.setSpacing(50);
        buttonVB.setPadding(new Insets(40, 0, 0, 0));
        buttonVB.getChildren().addAll(borrowbookButton, returnbookButton, personalButton);

        VBox rootnodeVB = new VBox();
        rootnodeVB.getChildren().addAll(menubarBP);
        rootnodeVB.getChildren().addAll(buttonVB);
        Scene scene = new Scene(rootnodeVB, 360, 440);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("图书管理系统");
        return stage;
    }

    /*注册界面*/
    private Stage enrollstage() {
        Stage stage = new Stage();
        Label accountlabel = new Label("账    号");
        Label namelabel = new Label("姓    名");
        Label passwordlabel = new Label("密    码");
        Label passwordchecklabel = new Label("确认密码");
        Label verificationodelabel = new Label("验证码");
        accountlabel.setFont(new Font("黑体", 20));
        namelabel.setFont(new Font("黑体", 20));
        passwordlabel.setFont(new Font("黑体", 20));
        passwordchecklabel.setFont(new Font("黑体", 20));
        verificationodelabel.setFont(new Font("黑体", 20));

        TextField accountTF = new TextField();
        TextField nameTF = new TextField();
        PasswordField passwordPF = new PasswordField();
        PasswordField passwordcheckPF = new PasswordField();
        TextField verificationcodeTF = new TextField();
        accountTF.setPromptText("借阅证编号");
        nameTF.setPromptText("输入真实姓名");
        passwordPF.setPromptText("密码长度应至少6位");
        passwordcheckPF.setPromptText("再次输入密码");
        verificationcodeTF.setPromptText("请输入验证码");

        Button returnButton = new Button("返   回");
        Button registeredButton = new Button("注   册");
        returnButton.setFont(new Font("黑体", 20));
        registeredButton.setFont(new Font("黑体", 20));
//        设置验证码(只能大写)
        char a = (char) (Math.random() * 25 + 65);
        char b = (char) (Math.random() * 25 + 65);
        char c = (char) (Math.random() * 25 + 65);
        char d = (char) (Math.random() * 25 + 65);
        String code = a + "" + b + "" + c + "" + d;
        Label codelabel = new Label(code);
        codelabel.setFont(new Font("黑体", 20));
        codelabel.setTextFill(Color.RED);

//        创建一个水平空间框，放置两个按钮
        HBox buttonHB = new HBox();
        buttonHB.setSpacing(80);
        buttonHB.setPadding(new Insets(15, 0, 15, 0));
        buttonHB.getChildren().addAll(returnButton, registeredButton);
//        创建根面板
        GridPane rootnodeGP = new GridPane();
        rootnodeGP.setHgap(10);
        rootnodeGP.setVgap(10);
        rootnodeGP.setPadding(new Insets(40, 0, 0, 45));
//        向网格面板中添加控件
        rootnodeGP.add(accountlabel, 1, 0);
        rootnodeGP.add(namelabel, 1, 1);
        rootnodeGP.add(passwordlabel, 1, 2);
        rootnodeGP.add(passwordchecklabel, 1, 3);
        rootnodeGP.add(accountTF, 2, 0);
        rootnodeGP.add(nameTF, 2, 1);
        rootnodeGP.add(passwordPF, 2, 2);
        rootnodeGP.add(passwordcheckPF, 2, 3);
        rootnodeGP.add(verificationodelabel, 1, 4);
        rootnodeGP.add(verificationcodeTF, 2, 4);
        rootnodeGP.add(codelabel, 3, 4);
        rootnodeGP.add(buttonHB, 2, 5);

//        按钮事件
        registeredButton.setOnAction(event -> {
            String sqlInsertinto = "insert into readers(rid,rpassword,rname,rsex,rphone) values(?,?,?,?,?)";
            String sql = "select rid,rpassword from readers where rid = ?";
            long acc;
            if (!accountTF.getText().isEmpty()) {
                try {
                    acc = Long.parseLong(accountTF.getText());
                    int sexnum = Integer.parseInt(accountTF.getText().substring(1, 2));
                    if (accountTF.getText().length() == 10) {
                        if (!JDBCutils.isexistence(sql, acc)) {
                            if (passwordPF.getText().equals(passwordcheckPF.getText())) {
                                if (verificationcodeTF.getText().equalsIgnoreCase(code)) {
                                    if (passwordPF.getText().length() >= 6) {
                                        if (sexnum % 2 == 0) {
                                            JDBCutils.setADM(sqlInsertinto, acc, passwordcheckPF.getText(), nameTF.getText(), "女", "---");
                                        } else if (sexnum % 2 == 1) {
                                            JDBCutils.setADM(sqlInsertinto, acc, passwordcheckPF.getText(), nameTF.getText(), "男", "---");
                                        } else {
                                            labelled = new Label("出错");
                                        }
                                        stage.close();
                                        mainstage().show();
                                    } else {
                                        labelled = new Label("密码长度小于6位");
                                    }
                                } else {
                                    labelled = new Label("验证码错误");
                                }
                            } else {
                                labelled = new Label("密码设置不相同！");
                            }
                        } else {
                            labelled = new Label("账号存在请直接登录");
                        }
                    } else {
                        labelled = new Label("账号格式不正确");
                    }
                } catch (Exception e) {
                    labelled = new Label("账号输入不是数字");
                }
            } else {
                labelled = new Label("账号为空");
            }
            labelled.setFont(new Font("黑体", 18));
            labelled.setTextFill(Color.RED);
            labelled.setAlignment(Pos.CENTER);
            rootnodeGP.add(labelled, 2, 6);
            Label finalLabelled = labelled;
            accountTF.setOnMouseClicked(event1 -> finalLabelled.setVisible(false));
            nameTF.setOnMouseClicked(event1 -> finalLabelled.setVisible(false));
            passwordPF.setOnMouseClicked(event1 -> finalLabelled.setVisible(false));
            passwordcheckPF.setOnMouseClicked(event1 -> finalLabelled.setVisible(false));
            verificationcodeTF.setOnMouseClicked(event1 -> finalLabelled.setVisible(false));
        });
        returnButton.setOnAction(event -> {
            try {
                mainstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });

//        设置主场景
        Scene scene = new Scene(rootnodeGP, 480, 400);
        scene.setOnMouseClicked(event -> {
            if (labelled != null) {
                labelled.setVisible(false);
            }
        });
        stage.setTitle("注册");
        stage.setScene(scene);
        stage.setResizable(false);
        return stage;
    }

    /*找回密码界面*/
    public Stage retrievepasswordstage() {
        Stage stage = new Stage();
        Label accountlabel = new Label("账    号");
        Label namelabel = new Label("姓    名");
        Label passwordlabel = new Label("密    码");
        Label passwordchecklabel = new Label("确认密码");
        Label verificationcodelabel = new Label("验证码");
        accountlabel.setFont(new Font("黑体", 20));
        namelabel.setFont(new Font("黑体", 20));
        passwordlabel.setFont(new Font("黑体", 20));
        passwordchecklabel.setFont(new Font("黑体", 20));
        verificationcodelabel.setFont(new Font("黑体", 20));

        TextField accountTF = new TextField();
        TextField nameTF = new TextField();
        PasswordField passwordPF = new PasswordField();
        PasswordField passwordcheckPF = new PasswordField();
        TextField verificationcodeTF = new TextField();
        accountTF.setPromptText("输入正确的借阅证编号");
        nameTF.setPromptText("正确的姓名");
        passwordPF.setPromptText("密码长度应大于6位");
        passwordcheckPF.setPromptText("再次输入密码");
        verificationcodeTF.setPromptText("请输入验证码");

        Button returnButton = new Button("返   回");
        Button okButton = new Button("确   定");
        returnButton.setFont(new Font("黑体", 20));
        okButton.setFont(new Font("黑体", 20));
//        设置验证码(只能大写)
        char a = (char) (Math.random() * 25 + 65);
        char b = (char) (Math.random() * 25 + 65);
        char c = (char) (Math.random() * 25 + 65);
        char d = (char) (Math.random() * 25 + 65);
        String code = a + "" + b + "" + c + "" + d;
        Label codelabel = new Label(code);
        codelabel.setFont(new Font("黑体", 20));
        codelabel.setTextFill(Color.RED);

//        创建一个水平空间框，放置两个按钮
        HBox buttonHB = new HBox();
        buttonHB.setSpacing(70);
        buttonHB.setPadding(new Insets(15, 0, 15, 0));
        buttonHB.getChildren().addAll(returnButton, okButton);

        GridPane rootnodeGP = new GridPane();
        rootnodeGP.setHgap(10);
        rootnodeGP.setVgap(10);
        GridPane.setHalignment(accountlabel, HPos.RIGHT);
        GridPane.setHalignment(namelabel, HPos.RIGHT);
        GridPane.setHalignment(passwordchecklabel, HPos.RIGHT);
        GridPane.setHalignment(passwordlabel, HPos.RIGHT);
        GridPane.setHalignment(verificationcodelabel, HPos.RIGHT);
        rootnodeGP.setPadding(new Insets(40, 20, 0, 40));
        rootnodeGP.add(accountlabel, 1, 0);
        rootnodeGP.add(namelabel, 1, 1);
        rootnodeGP.add(passwordlabel, 1, 2);
        rootnodeGP.add(passwordchecklabel, 1, 3);
        rootnodeGP.add(verificationcodelabel, 1, 4);
        rootnodeGP.add(accountTF, 2, 0);
        rootnodeGP.add(nameTF, 2, 1);
        rootnodeGP.add(passwordPF, 2, 2);
        rootnodeGP.add(passwordcheckPF, 2, 3);
        rootnodeGP.add(verificationcodeTF, 2, 4);
        rootnodeGP.add(codelabel, 3, 4);
        rootnodeGP.add(buttonHB, 2, 5);

//        按钮事件
        okButton.setOnAction(event -> {
            String updateSQL = "update readers set rpassword = ? where rid = ?";
            String selectrnameSQL = "select rname from readers where rid = ? and rname = ?";
            String selectridSQL = "select rid from readers where rid = ?";
            if (!accountTF.getText().isEmpty()) {
                if (accountTF.getText().length() == 10) {
                    if (JDBCutils.isexistence(selectridSQL, accountTF.getText())) {
                        if (JDBCutils.isexistence(selectrnameSQL, accountTF.getText(), nameTF.getText())) {
                            if (passwordPF.getText().equals(passwordcheckPF.getText())) {
                                if (verificationcodeTF.getText().equalsIgnoreCase(code)) {
                                    if (passwordPF.getText().length() > 3) {
                                        JDBCutils.setADM(updateSQL, passwordcheckPF.getText(), accountTF.getText());
                                        labelled = new Label("修改密码成功！登录中····");
                                        System.out.println("修改密码成功！登录中····");
                                        try {
                                            mainstage().show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        stage.close();
                                    } else {
                                        labelled = new Label("密码长度小于6位");
                                    }
                                } else {
                                    labelled = new Label("验证码错误");
                                }
                            } else {
                                labelled = new Label("密码设置不相同！");
                            }
                        } else {
                            labelled = new Label("姓名出错");
                        }
                    } else {
                        labelled = new Label("账号不存在请返回注册");
                    }
                } else {
                    labelled = new Label("借阅证编号格式不正确");
                }
            } else {
                labelled = new Label("借阅证编号为空");
            }
            labelled.setFont(new Font("黑体", 18));
            labelled.setTextFill(Color.RED);
            rootnodeGP.add(labelled, 2, 6);
            accountTF.setOnMouseClicked(event1 -> labelled.setVisible(false));
            nameTF.setOnMouseClicked(event1 -> labelled.setVisible(false));
            passwordPF.setOnMouseClicked(event1 -> labelled.setVisible(false));
            passwordcheckPF.setOnMouseClicked(event1 -> labelled.setVisible(false));
            verificationcodeTF.setOnMouseClicked(event1 -> labelled.setVisible(false));
        });
        returnButton.setOnAction(event -> {
            try {
                mainstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });

        Scene scene = new Scene(rootnodeGP, 480, 400);
        scene.setOnMouseClicked(event -> {
            if (labelled != null) {
                labelled.setVisible(false);
            }
        });
        stage.setScene(scene);
        stage.setTitle("找回密码");
        stage.setResizable(false);
        return stage;
    }

    /*管理者询问退出登录*/
    private Stage isadminoutloginstage() {
        Stage stage = new Stage();
        Label label = new Label("是 否 退 出 登 录");
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

        ok.setOnAction(event -> {
            try {
                mainstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
        cancel.setOnAction(event -> {
            adminmainstage().show();
            stage.close();
        });
        Scene scene = new Scene(vBox, 300, 120);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("退出登录");
        return stage;
    }

    /*用户询问退出登录*/
    private Stage isuseroutloginstage() {
        Stage stage = new Stage();
        Label label = new Label("是 否 退 出 登 录");
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

        ok.setOnAction(event -> {
            try {
                mainstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
        cancel.setOnAction(event -> {
            usermainstage().show();
            stage.close();
        });
        Scene scene = new Scene(vBox, 300, 120);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("退出登录");
        return stage;
    }

    /*提示逾期未归还*/
    private Stage isoutdatestage() {
        Stage stage = new Stage();
        Label label = new Label("你有逾期未归还的书籍，请尽快归还");
        label.setFont(new Font("黑体", 20));
        Button ok = new Button("我已了解");
        ok.setFont(new Font("黑体", 20));

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(ok);

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(22, 0, 0, 0));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(label, hBox);

        ok.setOnAction(event -> {
            try {
                usermainstage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
        Scene scene = new Scene(vBox, 400, 150);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("提示");
        return stage;
    }

    /* 得到表格数据 */
    public ObservableList<allentity> gettablevalue(String sql) {
        List<allentity> list = JDBCutils.getselectlist(allentity.class, sql);
        assert list != null;
        return FXCollections.observableList(list);
    }
}