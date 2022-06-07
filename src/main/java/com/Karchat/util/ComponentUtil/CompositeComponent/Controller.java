package com.Karchat.util.ComponentUtil.CompositeComponent;

import com.Karchat.entity.Friends;
import com.Karchat.entity.History;
import com.Karchat.entity.Post;
import com.Karchat.util.ComponentUtil.Label.InnerLabel;
import com.Karchat.util.PictureUtil.GetPicture;
import com.Karchat.util.SoundUtil.PlaySound;
import com.Karchat.view.Home;
import com.Karchat.view.LoginHome;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.*;

import static com.Karchat.util.ComponentUtil.CompositeComponent.Menu.*;
import static com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent.*;
import static com.Karchat.view.Home.*;
import static com.Karchat.view.LoginHome.*;


/**
 * 客户端程序,向服务器发送请求，并接收客户端的反馈，服务器在EchoThread类里处理信息
 */
@Slf4j
public class Controller {

    public static boolean login =false;  //判断是否登录
    public static boolean check =false;  //判断是否存在用户
    public static boolean getIcon =false;  //判断是否更换头像
    public static boolean get =false;  //获取所有请求
    public static boolean isGetFinished =false;  //获取所有请求
    public static boolean post =false;  //获取所有发送
    public static boolean isPostFinished =false;  //获取所有发送
    public static boolean getSbIcon =false;  //获取某人头像
    public static boolean getSbIconGet =false;  //获取某人头像
    public static boolean addFriend =false;  //加好友
    public static boolean remember =false;  //是否记住密码
    public static boolean addState =false;  //是否修改addFriend中的state
    public static String addStateName ;  //是否修改addFriend中的被邀请者
    public static String deleteAddFriendName ;  //删除好友邀请的好友姓名
    public static boolean deleteAddFriend=false ;  //是否删除好友邀请
    public static boolean checkFriends=false ;  //查看用户的所有好友
    public static boolean checkFriendsNameOnly=false ;  //查看用户的所有好友姓名
    public static boolean getFriendIcon=false ;  //获得好友的头像
    public static boolean getUserState=false ;  //获得好友在线状态
    public static boolean getChatHistory=false ;  //获得聊天历史记录
    private static Friends[] friends;
    private static Socket clien;
    public static String[] usernameAll=new String[1];
    public static String[] passwordAll=new String[1];
    static boolean historyIsFlash=true;  //是否开始刷新聊天记录
    public static HashMap<String, Boolean> isSomeBodyFinished=new HashMap<>();


    public static void start() throws InterruptedException {
        //指定连接主机及端口
        try {
            clien = new Socket("localhost", 8888);
        }catch (IOException e) {
            LoginHome.isAlive = false;
            new LoginHome();  //打开客户端登录窗口
            log.info("登录界面打开成功");
            log.info("服务器未连接");
            LoginHome.wrongMessage.setTextDynamic("服务器未连接");
            load.add(sign);
            sign.setBounds(-10,10,155,155);
            load.show();
            while (true) {
                Thread.sleep(100);
                if (login) {  //若登录则提示未连接服务器
                    LoginHome.wrongMessage.shake();
                    LoginHome.loginLabel.shake();
                    new Thread(){
                        @SneakyThrows
                        @Override
                        public void run() {
                            PlaySound.play("sound/error.mp3");
                        }
                    }.start();
                    login = false;
                }
                if (check) {  //若注册则显示未连接服务器
                    LoginHome.wrongMessage.shake();
                    LoginHome.registerLabel5.shake();
                    new Thread(){
                        @SneakyThrows
                        @Override
                        public void run() {
                            PlaySound.play("sound/error.mp3");
                        }
                    }.start();
                    check = false;
                }
            }
        }
        try (
                PrintStream out = new PrintStream(
                        clien.getOutputStream());  //向服务器端输出信息
                BufferedReader buf = new BufferedReader(new InputStreamReader(clien.getInputStream())); //接收服务器返回的信息
        ) {
            new LoginHome();  //打开客户端登录窗口
            {  //判断是否储存密码
                try (Scanner sc = new Scanner(new FileReader("userMessage"))) {
                    while (sc.hasNextLine()) {
                        String user = sc.nextLine();
                        String pass = sc.nextLine();
                        username.setText(user);
                        password.setText(pass);
                        passwordMessage.setText("");
                        log.info("读取了保存的账号密码");
                    }

                } catch (FileNotFoundException e) {
                    log.info("EchoClient:没有保存的账号密码文件");
                }

            }

            boolean ifStartFlash = true;  //是否是第一次开始刷新
            label:
            {
                usernameAll = new String[1];  //账号
                passwordAll = new String[1];  //密码
                while (true) {
                    Thread.sleep(100);  //加入多次点击延迟,防止卡服
                    if (login) {  //判断提交事件是否发生
                        log.info("正在登陆中.....");
                        loadIn.add(sign);  //加入加载条
                        sign.setBounds(-10, 10, 155, 155);
                        loadIn.setColor(new Color(115, 175, 197));
                        LoginHome.wrongMessage.setTextDynamic("登陆中");
                        wrongMessage.setForeground(new Color(115, 175, 197));
                        loadIn.show();
                        Thread.sleep(1000);

                        LoginHome.checkLogin(message -> {  //发送到服务器，并判断是否存在该用户
                            out.println("login");  //输出给服务器要进行的功能
                            out.println(message[0]);  //向服务器发送账号
                            out.println(message[1]);  //向服务器发送密码
                            usernameAll[0] = message[0];
                            passwordAll[0] = message[0];
                        });
                        String message = buf.readLine();
                        if ("true".equals(message)) {
                            log.info("登录成功!");
                            new Thread() {
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/loginsuccess.mp3");
                                }
                            }.start();
                            {  //判断是否保存密码，保存了则储存密码
                                if (remember) {  //保存密码
                                    log.info("保存了账号: "+usernameAll[0]);
                                    log.info("保存了密码: "+passwordAll[0]);
                                    PrintStream userMessage = new PrintStream(new FileOutputStream("userMessage"));
                                    userMessage.println(usernameAll[0]);
                                    userMessage.println(passwordAll[0]);
                                }
                            }
                            LoginHome.background.dispose();  //登录成功关闭页面
                            //进入新的界面
                            break label; //退出登录功能
                        } else if ("already".equals(message)) {
                            loadIn.setColor(new Color(0, 0, 0, 0));
                            wrongMessage.setForeground(new Color(215, 27, 71, 205));
                            LoginHome.loginLabel.shake();  //错误后让按钮抖动
                            LoginHome.wrongMessage.setTextDynamic("账号已经登录");
                            LoginHome.wrongMessage.shake();
                            log.info("账号已经登录,登录失败");
                            new Thread() {
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/error.mp3");
                                }
                            }.start();
                            login = false;  //登录失败标志位重置为false
                        } else {
                            loadIn.setColor(new Color(0, 0, 0, 0));
                            wrongMessage.setForeground(new Color(215, 27, 71, 205));
                            LoginHome.loginLabel.shake();  //错误后让按钮抖动
                            LoginHome.wrongMessage.setTextDynamic("账号不存在或密码错误");
                            LoginHome.wrongMessage.shake();
                            log.info("账号不存在或密码错误");
                            new Thread() {
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/error.mp3");
                                }
                            }.start();
                            login = false;  //登录失败标志位重置为false
                        }
                    }
                    if (check) {  //判断提交事件是否发生
                        log.info("注册中");
                        loadIn.add(sign);  //加入加载条
                        sign.setBounds(-10, 10, 155, 155);
                        loadIn.setColor(new Color(115, 175, 197));
                        LoginHome.wrongMessage.setTextDynamic("注册中");
                        wrongMessage.setForeground(new Color(115, 175, 197));
                        loadIn.show();

                        LoginHome.checkLogin(message -> {  //发送到服务器，并判断是否存在该用户
                            out.println("login");  //输出给服务器要进行的功能
                            out.println(message[0]);  //向服务器发送账号
                            out.println(message[1]);  //向服务器发送密码
                            usernameAll[0] = message[0];
                            passwordAll[0] = message[1];
                        });
                        if ("true".equals(buf.readLine())) {
                            loadIn.setColor(new Color(0, 0, 0, 0));
                            wrongMessage.setForeground(new Color(215, 27, 71, 205));

                            LoginHome.registerAlready();
                            log.info("该用户已经注册过，注册失败");
                            new Thread() {
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/error.mp3");
                                }
                            }.start();
                            check = false;
                        } else {
                            out.println("register");
                            out.println(usernameAll[0]);
                            out.println(passwordAll[0]);
                            out.println(LoginHome.iconString);
                            if (Objects.equals(buf.readLine(), "true")) {
                                Thread.sleep(1000);
                                log.info("注册成功！");
                                LoginHome.registerFinish(usernameAll[0], passwordAll[0]);  //做出注册完反馈
                                new Thread() {
                                    @SneakyThrows
                                    @Override
                                    public void run() {
                                        PlaySound.play("sound/loginsuccess.mp3");
                                    }
                                }.start();
                            }
                            //创建好友表，以该用户名为表名
                            out.println("createFriendsTable");
                            check = false;  //登录失败标志位重置为false
                        }
                    }
                }

            }
            label2:
            {
                //打开客户端登录窗口
                new Home();

                while (true) {
                    Thread.sleep(100);  //加入多次点击延迟,防止卡服

                    if (getIcon) {
                        out.println("getIcon");
                        String iconString = buf.readLine();
                        BufferedImage icon = GetPicture.stringToImage(iconString);  //转成图片
                        Home.setIcon(icon);
                        log.info("获取用户头像....");
                        getIcon = false;  //修改标签
                    }
                    if (get) {
                        log.info("获取待同意的好友邀请....");
                        out.println("get");
                        out.println(usernameAll[0]);
                        int length = Integer.parseInt(buf.readLine());
                        Post[] posts = new Post[length];
                        for (int i = 0; i < length; i++) {
                            String post = buf.readLine();
                            String get = buf.readLine();
                            posts[i] = new Post(post, get, null);  //获取每一个请求
                        }
                        String flag = buf.readLine();
                        if (flag.equals("true")) {
                            isGetFinished = false;
                        }
                        MenuContent.getPosts(posts);  //发送所有请求

                        get = false;
                    }
                    if (getSbIcon) {
                        log.info("获得待同意邀请的好友头像....");
                        out.println("getSbIcon");
                        out.println(iconLength);
                        for (int i = 0; i < iconLength; i++) {
                            out.println(iconName[i]);
                            BufferedImage icon = GetPicture.stringToImage(buf.readLine());  //转成图片
                            MenuContent.images[i] = icon;
                        }
                        getSbIcon = false;
                        MenuContent.setContext();
//                        break label2;
                    }
                    if (getSbIconGet) {
                        log.info("获得已发送邀请的好友头像....");
                        out.println("getSbIconGet");
                        out.println(MenuContent.iconLengthGet);
                        for (int i = 0; i < MenuContent.iconLengthGet; i++) {
                            out.println(MenuContent.iconNameGet[i]);
                            BufferedImage icon = GetPicture.stringToImage(buf.readLine());  //转成图片
                            MenuContent.imagesGet[i] = icon;
                        }
                        getSbIconGet = false;
                        MenuContent.setContextGet();
//                        break label2;
                    }
                    if (addFriend) {
                        new Thread(){
                            @SneakyThrows
                            @Override
                            public void run() {
                                label:
                                {
                                    while (true) {
                                        if (!isSending&&!isCheckingHistory&&!isFlashing&&!isGetFriendAmount) {
                                            log.info("正在添加好友....");
                                            isAddingFriends = true;
                                            out.println("addFriend");
                                            out.println(MenuContent.friendName);
                                            String bool = buf.readLine();
//
                                            if ("true".equals(bool)) {
                                                log.info("邀请已经发送!");
                                                MenuContent.searchText.setText("已发送好友邀请");
                                                MenuContent.searchText.setForeground(new Color(62, 171, 159));
                                                new Thread() {
                                                    @SneakyThrows
                                                    @Override
                                                    public void run() {
                                                        PlaySound.play("sound/loginsuccess.mp3");
                                                    }
                                                }.start();
                                            } else if ("false".equals(bool)) {
                                                log.info("发送失败!用户名输入错误");
                                                MenuContent.searchText.setText("用户名输入错误");
                                                MenuContent.searchText.setForeground(new Color(161, 19, 19));
                                                new Thread() {
                                                    @SneakyThrows
                                                    @Override
                                                    public void run() {
                                                        PlaySound.play("sound/error.mp3");
                                                    }
                                                }.start();
                                            } else if ("already".equals(bool)) {  //已经存在了邀请
                                                log.info("发送失败!已经发送过该邀请");
                                                MenuContent.searchText.setText("已经发送过该邀请");
                                                MenuContent.searchText.setForeground(new Color(161, 19, 19));
                                                new Thread() {
                                                    @SneakyThrows
                                                    @Override
                                                    public void run() {
                                                        PlaySound.play("sound/error.mp3");
                                                    }
                                                }.start();
                                            }
                                            String isFinish = buf.readLine();  //读取是否结束
                                            if (isFinish.equals("finish")) {
                                                isAddingFriends = false;
                                                break label;
                                            }
                                        }
                                    }
                                }
                            }
                        }.start();

                        addFriend = false;
                    }
                    if (post) {
                        log.info("获取已发送的好友邀请记录....");
                        out.println("post");
                        out.println(usernameAll[0]);
                        int length = Integer.parseInt(buf.readLine());
                        Post[] posts = new Post[length];
                        for (int i = 0; i < length; i++) {
                            String post = buf.readLine();
                            String get = buf.readLine();
                            String state = buf.readLine();
                            posts[i] = new Post(post, get, state);  //获取每一个请求
                        }
                        String flag = buf.readLine();
                        if (flag.equals("true")) {
                            isPostFinished = false;
                        }
                        MenuContent.getGets(posts);  //发送所有请求
                        post = false;
                    }
                    if (addState) {  //修改addFriend中的状态
                        log.info("加好友成功，正在修改好友状态....");
                        new Thread() {
                            @Override
                            public void run() {
                                out.println("addState");
                                out.println(addStateName);
                                addState = false;
                                String BooleanFlag = null;
                                try {
                                    BooleanFlag = buf.readLine();
                                } catch (IOException e) {
                                    Controller.ServerClosed();  //服务器关闭方法
                                    e.printStackTrace();
                                }
                                if ("true".equals(BooleanFlag)) {
                                    MenuContent.searchText.setText("已同意好友邀请");
                                    log.info("修改好友状态成功!");
                                    MenuContent.searchText.setForeground(new Color(62, 171, 159));
                                    new Thread() {
                                        @SneakyThrows
                                        @Override
                                        public void run() {
                                            PlaySound.play("sound/loginsuccess.mp3");
                                        }
                                    }.start();
                                }


                            }
                        }.start();

                    }
                    if (deleteAddFriend) {
                        new Thread() {
                            @Override
                            public void run() {
                                log.info("已拒绝好友邀请，正在删除邀请信息....");
                                out.println("deleteAddFriend");
                                out.println(deleteAddFriendName);
                                MenuContent.searchText.setText("已拒绝好友邀请");
                                MenuContent.searchText.setForeground(new Color(102, 48, 180));
                                new Thread() {
                                    @SneakyThrows
                                    @Override
                                    public void run() {
                                        PlaySound.play("sound/loginsuccess.mp3");
                                    }
                                }.start();
                                deleteAddFriend = false;
                            }
                        }.start();
                    }
                    if (checkFriends) {
                        new Thread() {
                            @Override
                            public void run() {
                                log.info("正在获取好友列表....");
                                out.println("checkFriends");
                                try {
                                    int length = Integer.parseInt(buf.readLine());  //好友个数
                                    friends = new Friends[length];
                                    for (int i = 0; i < length; i++) {
                                        String friend = buf.readLine();
                                        String getChatLocation = buf.readLine();
                                        friends[i] = new Friends(0, friend, getChatLocation);
                                    }
                                    getChat(friends, true);  //把得到的全部好友（姓名+聊天表位置）传给getChat
                                    String flag = buf.readLine();
                                    log.info("获取好友列表成功!");
                                } catch (IOException e) {
                                    Controller.ServerClosed();  //服务器关闭方法
                                    e.printStackTrace();
                                }

                            }
                        }.start();
                        checkFriends = false;
                    }
                    if (checkFriendsNameOnly) {
                        new Thread() {
                            @Override
                            public void run() {
                                out.println("checkFriends");
                                try {
                                    int length = Integer.parseInt(buf.readLine());  //好友个数
                                    friends = new Friends[length];
                                    for (int i = 0; i < length; i++) {
                                        String friend = buf.readLine();
                                        String getChatLocation = buf.readLine();
                                        friends[i] = new Friends(0, friend, getChatLocation);
                                    }
                                    String flag = buf.readLine();
                                    getChat(friends, false);  //把得到的全部好友（姓名+聊天表位置）传给getChat
                                    labelWhile:
                                    {
                                        while (true) {
                                            if (flag.equals("true")) {
                                                isGetFriendAmount = false;
                                                break labelWhile;
                                            }
                                        }
                                    }
                                } catch (IOException e) {
                                    Controller.ServerClosed();  //服务器关闭方法
                                } catch (NumberFormatException e) {
                                    Controller.ClientError();  //客户端出错
                                }
                            }
                        }.start();
                        checkFriendsNameOnly = false;
                    }
                    if (getFriendIcon) {
                        new Thread() {
                            @Override
                            public void run() {
                                log.info("获取好友的头像....");
                                BufferedImage[] icons = new BufferedImage[friends.length];
                                out.println("getFriendIcon");
                                out.println(friends.length);
                                for (int i = 0; i < friends.length; i++) {
                                    out.println(friends[i].getFriends());
                                    BufferedImage icon = null;  //转成图片
                                    try {
                                        icon = GetPicture.stringToImage(buf.readLine());
                                    } catch (IOException e) {
                                        Controller.ServerClosed();  //服务器关闭方法
                                        e.printStackTrace();
                                    }
                                    icons[i] = icon;
                                }
                                MenuContent.setContextChat(icons);
                            }
                        }.start();
                        getFriendIcon = false;
                    }
                    if (getUserState) {
                        log.info("获取好友的在线状态....");
                        new Thread() {
                            @Override
                            public void run() {
                                out.println("getUserState");
                                int[] state = new int[friends.length];
                                for (int i = 0; i < friends.length; i++) {
                                    out.println(friends[i].getFriends());
                                    try {
                                        state[i] = Integer.parseInt(buf.readLine());
                                    } catch (IOException e) {
                                        Controller.ServerClosed();  //服务器关闭方法
                                        e.printStackTrace();
                                    }
                                }
                                MenuContent.setStateIcon(state);  //传入状态
                            }
                        }.start();
                        getUserState = false;
                    }

                    {
                        if (ifStartFlash) {   //这个boolean值可以让两个线程只运行一次
                            //执行一次刷新好友列表以及获取历史记录
                            new Thread() {
                                @Override
                                public void run() {
                                    Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            if (!Menu.isClick1_1[0] && !isCheckingHistory && !isSending&&!isAddingFriends) {  //需要不在加好友界面，并且不在进行查找历史记录
                                                log.info("--正在刷新好友邀请列表--");
                                                isFlashing = true;
                                                isGetFinished = true;
                                                isPostFinished = true;
                                                //刷新get画布
                                                for (int i = 0; i < iconLength; i++) {  //清空画布,为下一次刷新做准备
                                                    contextGet.remove(labels[i]);
                                                }
                                                height = 0;  //刷新高度
                                                contextGet.repaint();  //刷新面板
                                                Controller.get = true;  //获取请求
                                                try {
                                                    Thread.sleep(500);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                for (int i = 0; i < iconLength; i++) {  //刷新画布
                                                    labels[i].repaint();
                                                }

                                                //刷新post画布
                                                for (int i = 0; i < iconLengthGet; i++) {  //清空画布,为下一次刷新做准备
                                                    contextPost.remove(labelsGet[i]);
                                                }
                                                heightGet = 0;  //刷新高度
                                                contextPost.repaint();  //刷新面板
                                                Controller.post = true;  //获取发送
                                                try {
                                                    Thread.sleep(500);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                for (int i = 0; i < iconLengthGet; i++) {  //刷新画布
                                                    labelsGet[i].repaint();
                                                }
                                                labelWhile:
                                                {
                                                    while (true) {
                                                        if (!isGetFinished && !isPostFinished) {
                                                            log.info("--好友列表刷新成功--");
                                                            isFlashing = false;  //执行完刷新
                                                            break labelWhile;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }, 8000, 8000);


                                }
                            }.start();


                            //*开启更新聊天线程:
                            // 先获取好友数量，分别看每个好友是否有新消息
                            // */
                            new Thread() {
                                @Override
                                public void run() {
                                    Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            if (!isFlashing && !isSending&&!isAddingFriends) {  //需要在不刷新加好友页面时执行,并不能进行发送
                                                isCheckingHistory = true;  //正在查询记录
                                                isGetFriendAmount = true;
                                                log.info("--正在检查聊天记录--");

                                                Controller.checkFriendsNameOnly = true;  //获取请求
                                                labelWhile:
                                                {
                                                    while (true) {
                                                        try {
                                                            Thread.sleep(1000);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        if (!isGetFriendAmount) {
                                                            for (int i = 0; i < iconLengthChat; i++) {
                                                                try {
                                                                    Controller.getChatHistoryAmount(iconNameChat[i]);  //统计聊天记录内容
                                                                } catch (IOException e) {
                                                                    Controller.ServerClosed();  //服务器关闭方法
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                            while (true) {
                                                                if (isSomeBodyFinished.get(iconNameChat[iconLengthChat - 1])) {
                                                                    isCheckingHistory = false;  //查询完成
                                                                    log.info("--查询聊天记录完成--");
                                                                    break labelWhile;
                                                                }
                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }, 8000, 5000);

                                }
                            }.start();
                            ifStartFlash = false;
                        }

                    }

//                    i  f (Objects.equals(buf.readLine(), "getMessage")) {  //若接收到getMessage,则是有人发消息
//                        String message = buf.readLine();  //获取发送内容
//                        String sendName = buf.readLine();  //获取发送人姓名
//                        int index = MenuContent.userContent.get(sendName);  //获取位于哪一页内容,下标
//                        InnerLabel friend = Home.chatContent[index];  //获取聊天内容界面
//                        friend.send(InnerLabel.Type.LEFT,message,friend.friend);  //发送信息
//
//                        //加一个消息的红点，提示已经有新消息发送
//                        messageIcon[index].setSize(8,8);  //设置红点信息
//                        messageIcon[index].repaint();
//
//                        //将最新消息显示在主界面
//                        latestMessages[index].setTextDynamic(message);
//                        latestMessages[index].repaint();
//                    }

                }
            }

        } catch (IOException e) {
            Controller.ServerClosed();  //服务器关闭方法

        }
        }

    public static boolean isFlashing = false;  //是否正在执行刷新加好友画布
    public static boolean isCheckingHistory = false;  //是否正在执行查找历史记录
    public static boolean isGetFriendAmount = false;  //是否获取全部好友数量
    public static boolean isSending = false;  //是否正在发送
    public static boolean isAddingFriends = false;  //是否正在加好友
    public static int[] mouseXY = new int[2];  //存储当前鼠标的xy


    /**
     * 向服务器发送信息
     * @param message
     * @param geter
     */
    @SneakyThrows
    public static void send(String message, String geter) {
        new Thread(){
            @SneakyThrows
            @Override
            public void run() {
                PrintStream out = new PrintStream(
                        clien.getOutputStream());  //向服务器端输出信息
                BufferedReader buf = new BufferedReader(new InputStreamReader(clien.getInputStream())); //接收服务器返回的信息

                label:
                {
                    while (true) {
                        if (!isFlashing && !isCheckingHistory&&!isAddingFriends) {  //找到其他刷新任务不在的间隙进行
                            log.info("正在发送给-"+geter+"-: "+message);
                            isSending = true;  //正在发送
                            out.println("send");
                            out.println(message);
                            out.println(geter);
                            out.println(usernameAll[0]);
                            String flag = buf.readLine();
                            if (flag.equals("true")) {
                                log.info("发送成功!");
                                isSending = false;  //发送完成
                                break label;  //退出while循环
                            }
                        }
                    }
                }
            }
        }.start();

        }

    public static HashMap<String, Integer> historyAmount = new HashMap<>();
    public static HashMap<String, ArrayList<History>> historysFactory = new HashMap<>();
    /**
     * 定时获取该好友的所有历史记录，以及记录历史数量
     * @param friend
     */
    public static void getChatHistoryAmount(String friend) throws IOException {
        ArrayList<History> oldHistroys = historysFactory.get(friend);  //上一次的历史记录
        ArrayList<History> histroys = new ArrayList<>();    //新的历史记录

        PrintStream out = new PrintStream(
                clien.getOutputStream());  //向服务器端输出信息
        BufferedReader buf = new BufferedReader(new InputStreamReader(clien.getInputStream())); //接收服务器返回的信息

        out.println("getChatHistory");
        out.println(usernameAll[0]); //发送我的姓名
        out.println(friend);  //发送朋友的姓名

        //获取是在跟谁聊天
        int index = userContent.get(friend);//获取表下标

        InnerLabel friendContext = Home.chatContent[index];  //获取聊天界面
        int length = 0;
        try {
            length = Integer.parseInt(buf.readLine());  //消息长度
        } catch (NumberFormatException e) {
            log.info(friend+"没有聊天记录");
        }

        String message = null; //接收的信息
        for (int i = 0; i < length; i++) {
            String type = buf.readLine();   //获取是发送信息还是获取信息
            switch (type) {
                case "post":  //我发送信息
                    message = buf.readLine();  //获取发送的信息
                    histroys.add(new History("post", message));  //将历史记录加入数组

                    break;
                case "get":  //我获取信息
                    message = buf.readLine();  //获取别人发送的信息
                    histroys.add(new History("get", message));  //将历史记录加入数组

                    break;
            }
            if (i == length - 1&&length!=0) {  //如果是最后一句，显示在主界面的最新消息
                latestMessages[index].setTextDynamic(message);
            }
        }
            //判断如果记录是否比之前数量多
            if (histroys.size() > historyAmount.get(friend)) {  //数量确实多

                //将对方发送来的消息显示在聊天窗口上
                for (int j = historyAmount.get(friend); j < histroys.size(); j++) {
                    if ("get".equals(histroys.get(j).type)) {  //我获取信息
                        message = histroys.get(j).message;  //获取别人发送的信息

                        friendContext.send(InnerLabel.Type.LEFT, message, friendContext.friend);
                    }
                }

                //将状态红点标红：
                messageIcon[userContent.get(friend)].setColor(new Color(227, 34, 34));

            }

            historyAmount.put(friend, histroys.size());  //记录该用户的历史记录数量
            historysFactory.put(friend, histroys);  //将新历史记录保存

        String flag = buf.readLine();
        if (flag.equals("true")) {
            isSomeBodyFinished.put(friend, true);
        }

        }

    /**
     * 查询聊天历史记录
     */
    @SneakyThrows
    public static void getChatHistory(String friend) {
        ArrayList<History> histroys = new ArrayList<>();  //记录所有历史记录
        PrintStream out = new PrintStream(
                clien.getOutputStream());  //向服务器端输出信息
              BufferedReader buf = new BufferedReader(new InputStreamReader(clien.getInputStream())); //接收服务器返回的信息

        log.info("正在查询-" + friend + "-的聊天记录....");
            out.println("getChatHistory");
            out.println(usernameAll[0]); //发送我的姓名
            out.println(friend);  //发送朋友的姓名

        //获取是在跟谁聊天
        int index = userContent.get(friend);//获取表下标

        InnerLabel friendContext = Home.chatContent[index];  //获取聊天界面
        int length = 0;
        try {
            length = Integer.parseInt(buf.readLine());  //消息长度
        } catch (NumberFormatException e) {
            log.info(friend+"没有聊天记录");
        }

        String message = null; //接收的信息
        for (int i = 0; i < length; i++) {
            String type = buf.readLine();   //获取是发送信息还是获取信息
            switch (type) {
                case "post":  //我发送信息
                    message = buf.readLine();  //获取发送的信息
                    histroys.add(new History("post",message));  //将历史记录加入数组

                    friendContext.send(InnerLabel.Type.RIGHT,message,friendContext.mine);
                    break;
                case "get":  //我获取信息
                    message = buf.readLine();  //获取别人发送的信息
                    histroys.add(new History("get",message));  //将历史记录加入数组

                    friendContext.send(InnerLabel.Type.LEFT,message,friendContext.friend);
                    break;
            }


            if (i == length - 1&&length!=0) {  //如果是最后一句，显示在主界面的最新消息
                latestMessages[index].setTextDynamic(message);
            }
        }

        historyAmount.put(friend, histroys.size());  //记录该用户的历史记录数量
        historysFactory.put(friend, histroys);  //存储聊天记录全部--对应某个好友

        }

    /**
     * 服务器关闭时调用的方法
     */
    public static void ServerClosed() {
        log.info("服务器已经关闭!请重启客户端");
        serverClosedMessage.setForeground(new Color(250, 38, 38));

        ServerCloseLoad.show();

    }

    /**
     * 客户端出问题时调用的方法
     */
    public static void ClientError() {
        log.info("客户端出错!请重启客户端");
        serverClosedMessage.setForeground(new Color(250, 38, 38));
        serverClosedMessage.setTextDynamic("客户端出错");

        ServerCloseLoad.show();

    }

    /**
     * 让主界面左菜单收缩的功能，用于后台检测情况后主动收缩
     */
    public static void MenuShrink() {
        new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                int WIDTH = WIDTHNOW[0];
                int MENUWIDTH = 140;
                keepFlag[0] = false;
                menuBack.setBounds(110, 20, newMenuIcon.getIconWidth(), newMenuIcon.getIconHeight());

                label2:
                {
                    isShrink = true;  //正在收缩
                    while (WIDTH > -20 - 180 - (newMenuIcon.getIconWidth() - menuIcon.getIconWidth())) {
                        Thread.sleep(1);
                        menuTop.setBounds(WIDTH, 0, newMenuIcon.getIconWidth(), menuIcon.getIconHeight());
                        if (menuFlag[0] && MENUWIDTH > 12)
                            menuHomeUser.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());
                        if (menuFlag1[0] && MENUWIDTH > 12)
                            menuHomeUser1.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());
                        if (menuFlag2[0] && MENUWIDTH > 12)
                            menuHomeUser2.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());
                        if (menuFlag3[0] && MENUWIDTH > 12)
                            menuHomeUser3.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 300, menuIcon.getIconHeight());
                        if (menuFlag4[0] && MENUWIDTH > 12)
                            menuHomeUser4.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());
                        if (menuFlag5[0] && MENUWIDTH > 12)
                            menuHomeUser5.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());
                        if (menuFlag6[0] && MENUWIDTH > 12)
                            menuHomeUser6.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());

                        WIDTH -= 3;
                        if (menuFlag[0] || menuFlag1[0] || menuFlag2[0] || menuFlag3[0] || menuFlag4[0] || menuFlag5[0] || menuFlag6[0])
                            MENUWIDTH -= 3;

                        if (keepFlag[0]) {
                            WIDTHNOW[0] = WIDTH;
                            break label2;
                        }
                    }
                    isShrink = false;  //收缩完成
                    menuBack.setBounds(110, 20, 0, 0);
                    isOpen[0] = false;
                    WIDTHNOW[0] = -(newMenuIcon.getIconWidth() - menuIcon.getIconWidth());  //清零
                }
            }
        }.start();
    }
}