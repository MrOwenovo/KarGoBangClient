package com.Karchat.util.ComponentUtil.CompositeComponent;

import com.Karchat.entity.Friends;
import com.Karchat.entity.Post;
import com.Karchat.service.MusicService;
import com.Karchat.util.ColorUtil.ChangeToColor;
import com.Karchat.util.ComponentUtil.Label.*;
import com.Karchat.util.ComponentUtil.Loading.LoadingSmall;
import com.Karchat.util.Constant;
import com.Karchat.util.Controller.Controller;
import com.Karchat.util.PictureUtil.RemoveBackground;
import com.Karchat.util.PictureUtil.ToBufferedImage;
import com.Karchat.util.SoundUtil.PlaySound;
import com.Karchat.view.Home;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import static com.Karchat.util.ComponentUtil.CompositeComponent.Menu.newMenuIcon;
import static com.Karchat.util.Constant.*;
import static com.Karchat.view.Home.*;

@Component
@Slf4j
public class MenuContent extends Observable {

    public static RadioJLabel background3;
    private static RadioTextJLabel style1;
    private static RadioTextJLabel style2;
    private static RadioTextJLabel style3;
    private static RadioTextJLabel style4;
    private static RadioTextJLabel style5;
    private static RadioTextJLabel style6;
    public static RadioJLabel background2 = new RadioJLabel("");
    public static RadioJLabel background1;
    public static RadioJLabel background = new RadioJLabel("");
    public static RadioJLabel background4 = new RadioJLabel("");
    public static RadioJLabel background5 = new RadioJLabel("");
    public static RadioJLabel background6 = new RadioJLabel("");

    public static RadioJLabel contextGet;


    public static RadioJLabel[] labels;  //3.用户标签

    public static RadioJLabel[] labelsGet;  //3.用户标签

    public static String[] iconName;  //3.用户名称

    public static String[] iconNameGet;  //3.getter用户名称

    public static RadioJLabel[] accept = new RadioJLabel[20];  //3.接收按钮

    public static RadioJLabel[] acceptGet = new RadioJLabel[20];  //3.接收按钮

    public static RadioJLabel[] refuse = new RadioJLabel[20];  //3.接收按钮

    public static RadioJLabel[] refuseGet = new RadioJLabel[20];  //3.接收按钮

    public static int iconLength;  //3.查到的用户个数

    public static BufferedImage[] images = new BufferedImage[20];  //3.头像

    public static BufferedImage[] imagesGet = new BufferedImage[20];  //3.头像


    public static JLabel[] iconLabels;  //3.头像图片

    public static JLabel[] iconLabelsGet;  //3.头像图片

    public static DynamicJLabel[] texts;  //3.用户姓名

    public static DynamicJLabel[] textsGet;  //3.用户姓名
    public static int iconLengthGet;
    private static Post[] getsPosts;
    private static JTextField searchTextChat;
    private static RadioJLabel contextChat;
    private static Friends[] getsChats;
    public static int iconLengthChat;
    public static String[] iconNameChat;
    private static ArrayList<RadioJLabel> labelsChar;
    private static ArrayList<JLabel> iconLabelsChar;
    private static ArrayList<DynamicJLabel> textsChar;
    private static ArrayList<RadioJLabel> stateIcon;  //是否在线的图标
    public static ArrayList<RadioJLabel> messageIcon;  //是否有新消息的图标
    private static ArrayList<RadioJLabel> stateIconBack;

    public static HashMap<String, Integer> userContent;

    //储存好友头像
    public static ArrayList<DynamicJLabel> latestMessages;

    static {  //创造容纳邀请的标签
        labels = new RadioJLabel[20];
        labelsGet = new RadioJLabel[20];
        iconName = new String[20];  //要查的用户名称，用来查头像
        iconNameGet = new String[20];  //要查的用户名称，用来查头像

        iconLabels = new JLabel[20];
        iconLabelsGet = new JLabel[20];

        texts = new DynamicJLabel[20];
        textsGet = new DynamicJLabel[20];

        //判断用户名对应的哪个表
        userContent = new HashMap<>();

    }

    public static RadioJLabel chooseBack;
    public static RadioJLabel chooseGet;
    public static RadioJLabel choosePost;
    public static String friendName;
    public static JTextField searchText;
    public static RadioJLabel contextPost;
    private static RadioJLabel blackBack;
    private RadioJLabel blackBackLeft;
    public static BufferedImage[] Chaticons;

    public static final int[] OPENINDEX = {-1};  //记录上一次是谁在发消息


    @Resource
    MusicService musicService;
    private RadioJLabel blackBackRight;

    /**
     * 初始化用户信息
     */
    public static void InitUserInfo(RadioJLabelNew home) {

    }

    /**
     * 初始化加好友页面
     */
    @SneakyThrows
    public void InitAddFriends(RadioJLabelNew home) {
//        System.setProperty("sun.java2d.noddraw", "true");  //防止输入法输入时白屏，禁用DirectDraw
        background1 = new RadioJLabel("");
        background1.setColor(new Color(252, 249, 249));
        background1.setArc(25, 25);
        background1.setBounds(0, 0, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());

        //上半部分
        DynamicJLabel LeftTopMessage = new DynamicJLabel("搜索好友", new Font("Serif", Font.BOLD, 22), 25);
        LeftTopMessage.setCenter(background1.getWidth() / 2 - 45 - 10);

        //搜索框
        ImageIcon searchIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/search.png")));
        RadioJLabel search = new RadioJLabel("");
        search.setColor(new Color(229, 227, 227));
        search.setBounds(30, 80, background1.getWidth() - 30 - 20, 70);
        search.setArc(60, 60);
        RadioJLabel searchIconLabel = new RadioJLabel(searchIcon);
        searchIconLabel.setColor(new Color(229, 227, 227));
        searchIconLabel.setBounds(14, 18, searchIcon.getIconWidth(), searchIcon.getIconHeight());
        search.add(searchIconLabel);

        searchText = new JTextField();
        search.add(searchText);  //加入搜索框
        searchText.setBounds(64, 5, search.getWidth() - 70, search.getHeight() - 10);
        searchText.setFont(new Font("Serif", Font.BOLD, 20));
        searchText.setForeground(new Color(79, 78, 78));
        searchText.setText("输入用户名");
//        searchText.setHorizontalAlignment(JTextField.HORIZONTAL);
        searchText.setOpaque(false);
        searchText.setBorder(null);

        searchText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (searchText.getText().contains("输入用户名") || searchText.getText().contains("已发送好友邀请") || searchText.getText().contains("用户名不能为空") || searchText.getText().contains("用户名输入错误") || searchText.getText().equals("已同意好友邀请") || searchText.getText().equals("已拒绝好友邀请")|| searchText.getText().equals("已经发送过该邀请")) {  //若要输入账号则取消提示
                    searchText.setForeground(new Color(79, 78, 78));
                    searchText.setText("");
                }
            }
        });
        searchText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchText.getText().equals("输入用户名") || searchText.getText().equals("已发送好友邀请") || searchText.getText().equals("用户名不能为空") || searchText.getText().equals("用户名输入错误") || searchText.getText().equals("已同意好友邀请") || searchText.getText().equals("已拒绝好友邀请")|| searchText.getText().equals("已经发送过该邀请")) {  //若要输入账号则取消提示
                    searchText.setForeground(new Color(79, 78, 78));
                    searchText.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchText.getText().equals("")) {   //若未输入内容则重新提示
                    searchText.setForeground(new Color(79, 78, 78));
                    searchText.setText("输入用户名");
                }
            }
        });

        //搜索加入键盘监听
        searchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {  //搜索
                    friendName = searchText.getText();  //储存用户名
                    if (friendName.length() >= 6) {
                        Constant.addFriend = true;


                    } else {
                        searchText.setText("用户名不能为空");
                        searchText.setForeground(new Color(161, 19, 19));
                        if (musicService!=null)
                            musicService.playErrorMP3();
                    }
                }
            }
        });

        //搜索图标加入点击事件
        searchIconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                friendName = searchText.getText();  //储存用户名
                if (friendName.length() >= 6) {
                    Constant.addFriend = true;
                    //播放发送音效
                    musicService.playSendMessageMP3();
                } else {
                    searchText.setText("用户名不能为空");
                    searchText.setForeground(new Color(161, 19, 19));
                    musicService.playErrorMP3();
                }
            }
        });

        background1.add(LeftTopMessage);
        background1.add(search);

        //加入刷新按钮
        LoadingSmall flash = new LoadingSmall();
        flash.setLayout(null);
        flash.setBounds(230, 225, background1.getWidth() - 30 - 20, 70);

        flash.setColor(new Color(115, 175, 197));
        flash.show();
//        background1.add(flash);


        //加入刷新按钮
        ImageIcon refreshIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("chat/refresh.png")));
        ImageIcon refreshIcon1 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("chat/loading/loading1.png")));
        ImageIcon refreshIcon2 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("chat/loading/loading2.png")));
        ImageIcon refreshIcon3 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("chat/loading/loading3.png")));
        ImageIcon refreshIcon4 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("chat/loading/loading4.png")));
        ImageIcon[] loadings = new ImageIcon[4];
        loadings[0] = refreshIcon1;
        loadings[1] = refreshIcon2;
        loadings[2] = refreshIcon3;
        loadings[3] = refreshIcon4;

        JLabel refresh = new JLabel(refreshIcon);
        refresh.setBounds(190, 205, background1.getWidth() - 30 - 20, 70);
        background1.add(refresh);


        refresh.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        background1.add(flash);
                        refresh.setBounds(190, 205, 0, 0);

                        flash.repaint();
                        refresh.repaint();


                        //刷新get画布
                        for (int i = 0; i < iconLength; i++) {  //清空画布,为下一次刷新做准备
                            contextGet.remove(labels[i]);
                        }
                        height = 0;  //刷新高度
                        contextGet.repaint();  //刷新面板
                        Constant.get = true;  //获取请求
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException f) {
                            f.printStackTrace();
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
                        Constant.post = true;  //获取发送
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException f) {
                            f.printStackTrace();
                        }
                        for (int i = 0; i < iconLengthGet; i++) {  //刷新画布
                            labelsGet[i].repaint();
                        }

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                        background1.remove(flash);
//                        background1.add(refresh);
                        refresh.setBounds(190, 205, background1.getWidth() - 30 - 20, 70);
                        flash.repaint();
                        refresh.repaint();

                        //全部刷新
                        blackBackLeft.repaint();
                        blackBackRight.repaint();
                        contextGet.repaint();
                        contextPost.repaint();
                        background1.repaint();

                    }
                }.start();


            }
        });


        //下半部分
        //选择
        chooseBack = new RadioJLabel("");
        chooseBack.setColor(new Color(239, 238, 238));
        chooseGet = new RadioJLabel("");
        chooseGet.setColor(new Color(239, 238, 238));
        choosePost = new RadioJLabel("");
        choosePost.setColor(new Color(239, 238, 238));
        chooseBack.add(chooseGet);
        chooseBack.add(choosePost);
        chooseBack.setBounds(20, 150, background1.getWidth() - 20 - 20, 60);
        chooseGet.setBounds(10, 10, background1.getWidth() / 2 - 20 - 10, 60);
        choosePost.setBounds(194, 10, background1.getWidth() / 2 - 20 - 10, 60);
        DynamicJLabel chooseMess1 = new DynamicJLabel("好友请求", new Font("Serif", Font.BOLD, 18), 10);
        chooseMess1.setCenter(chooseGet.getWidth());
        DynamicJLabel chooseMess2 = new DynamicJLabel("已发送请求", new Font("Serif", Font.BOLD, 18), 10);
        chooseMess2.setCenter(chooseGet.getWidth() * 2 - 166);
        chooseGet.add(chooseMess1);
        choosePost.add(chooseMess2);

        background1.add(chooseBack);

        blackBack = new RadioJLabel("");
        blackBackLeft = new RadioJLabel("");
        blackBack.setColor(new Color(229, 227, 227));
        blackBackRight = new RadioJLabel("");
        blackBackLeft.setColor(new Color(28, 27, 27));
        blackBackLeft.setArc(5, 5);
        blackBackRight.setArc(5, 5);
        blackBackRight.setColor(new Color(28, 27, 27));
        blackBack.add(blackBackLeft);
        blackBack.add(blackBackRight);
        blackBack.setBounds(20, 170 + 40, background1.getWidth() - 20 - 20, 9);
        blackBackLeft.setBounds(0, 0, blackBack.getWidth() / 2, 9);
        //background1.getWidth() - 20 - 20
        blackBackRight.setBounds(blackBack.getWidth() / 2, 0, 0, 9);

        background1.add(blackBack);


        contextGet = new RadioJLabel("");
        contextGet.setColor(new Color(239, 238, 238));
        contextGet.setBounds(0, blackBack.getY() + blackBack.getHeight() + 10, background1.getWidth(), background1.getHeight() - (blackBack.getY() + blackBack.getHeight() + 10));

        contextPost = new RadioJLabel("");
//        contextPost.setColor(new Color(7, 7, 7));
        contextPost.setColor(new Color(239, 238, 238));
        contextPost.setBounds(background1.getWidth(), blackBack.getY() + blackBack.getHeight() + 10, background1.getWidth(), background1.getHeight() - (blackBack.getY() + blackBack.getHeight() + 10));


        Constant.get = true;  //获取请求
        Constant.post = true;  //获取已发送

        JScrollPane scr = new JScrollPane(contextGet);  //加入滚轮
        JScrollPane scrPost = new JScrollPane(contextPost);  //加入滚轮
        scr.setBorder(null);
        scrPost.setBorder(null);
        scr.setBackground(new Color(239, 238, 238));
        scrPost.setBackground(new Color(239, 238, 238));

//        scr.setBounds(0, blackBack.getY() + blackBack.getHeight() + 10, background1.getWidth(), background1.getHeight() - (blackBack.getY() + blackBack.getHeight() + 10));
        scrPost.setBounds(0, blackBack.getY() + blackBack.getHeight() + 10, background1.getWidth(), background1.getHeight() - (blackBack.getY() + blackBack.getHeight() + 10));

        background1.add(contextGet);
        background1.add(contextPost);

        home.add(background1);

        final boolean[] RightIsFinish = new boolean[]{true, true};
        //切换框加入点击事件
        choosePost.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (RightIsFinish[0] && RightIsFinish[1] && blackBackLeft.getWidth() != 0) {
                    RightIsFinish[0] = false;  //右边缘动画未加载完
                    RightIsFinish[1] = false;  //右边缘动画未加载完

                    final int[] LOCATIONRight = {0};  //右边延展了多少
                    final int[] LOCATIONLeft = {blackBack.getWidth() / 2};  //左边延展了多少
                    final int[] TIMERight = {1};  //速度
                    final int[] TIMELeft = {1};  //速度
                    new Thread() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            while (LOCATIONRight[0] <= blackBack.getWidth() / 2) {
                                Thread.sleep(TIMERight[0]);
                                blackBackRight.setBounds(blackBack.getWidth() / 2, 0, LOCATIONRight[0], 9);

                                LOCATIONRight[0] += 3;
                                blackBackRight.repaint();
                                if (LOCATIONRight[0] >= blackBack.getWidth() / 4)
                                    TIMERight[0] += 1;
                            }
                            RightIsFinish[0] = true;

                        }
                    }.start();
                    new Thread() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            while (LOCATIONLeft[0] >= 0) {
                                Thread.sleep(TIMELeft[0]);
                                blackBackLeft.setBounds((blackBack.getWidth() / 2) - LOCATIONLeft[0], 0, LOCATIONLeft[0], 9);

                                LOCATIONLeft[0] -= 3;
                                blackBackLeft.repaint();
                                if (LOCATIONLeft[0] <= blackBack.getWidth() / 4)
                                    TIMELeft[0] += 1;
                            }
                            RightIsFinish[1] = true;

                        }
                    }.start();
                    final int[] HORIZON = {background1.getWidth()};
                    //切换页面
                    new Thread() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            while (HORIZON[0] >= 0) {
                                Thread.sleep(1);

                                contextGet.setBounds(HORIZON[0] - background1.getWidth(), blackBack.getY() + blackBack.getHeight() + 10, background1.getWidth(), background1.getHeight() - (blackBack.getY() + blackBack.getHeight() + 10));
                                contextPost.setBounds(HORIZON[0], blackBack.getY() + blackBack.getHeight() + 10, background1.getWidth(), background1.getHeight() - (blackBack.getY() + blackBack.getHeight() + 10));
                                contextGet.repaint();
                                contextPost.repaint();
                                background1.repaint();
                                HORIZON[0] -= 6;
                            }

                        }
                    }.start();


                }
            }
        });

        //切换框加入点击事件
        chooseGet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (RightIsFinish[0] && RightIsFinish[1] && blackBackRight.getWidth() != 0) {
                    RightIsFinish[0] = false;  //右边缘动画未加载完
                    final int[] LOCATIONRight = {blackBack.getWidth() / 2};  //右边延展了多少
                    final int[] LOCATIONLeft = {0};  //左边延展了多少
                    final int[] TIMERight = {1};  //速度
                    final int[] TIMELeft = {1};  //速度
                    new Thread() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            while (LOCATIONRight[0] >= 0) {
                                Thread.sleep(TIMERight[0]);
                                blackBackRight.setBounds(blackBack.getWidth() / 2, 0, LOCATIONRight[0], 9);


                                LOCATIONRight[0] -= 3;
                                blackBackRight.repaint();
                                if (LOCATIONRight[0] <= blackBack.getWidth() / 4)
                                    TIMERight[0] += 1;
                            }
                            RightIsFinish[0] = true;

                        }
                    }.start();
                    new Thread() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            while (LOCATIONLeft[0] <= blackBack.getWidth() / 2) {
                                Thread.sleep(TIMELeft[0]);
                                blackBackLeft.setBounds((blackBack.getWidth() / 2) - LOCATIONLeft[0], 0, LOCATIONLeft[0], 9);

                                LOCATIONLeft[0] += 3;
                                blackBackLeft.repaint();
                                if (LOCATIONLeft[0] >= blackBack.getWidth() / 4)
                                    TIMELeft[0] += 1;
                            }
                            RightIsFinish[1] = true;

                        }
                    }.start();

                    //切换页面
                    background1.remove(scrPost);
//                    background1.add(scr);
                    scr.setBackground(new Color(0, 0, 0));

                    background1.repaint();
                    final int[] HORIZON = {0};
                    //切换页面
                    new Thread() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            while (HORIZON[0] <= background1.getWidth()) {
                                Thread.sleep(1);

                                contextGet.setBounds(-background1.getWidth() + HORIZON[0], blackBack.getY() + blackBack.getHeight() + 10, background1.getWidth(), background1.getHeight() - (blackBack.getY() + blackBack.getHeight() + 10));
                                contextPost.setBounds(HORIZON[0], blackBack.getY() + blackBack.getHeight() + 10, background1.getWidth(), background1.getHeight() - (blackBack.getY() + blackBack.getHeight() + 10));
                                contextGet.repaint();
                                contextPost.repaint();
                                background1.repaint();
                                HORIZON[0] += 6;
                            }

                        }
                    }.start();
                }
            }
        });

    }

    public static int height = 0;  //邀请的高度


    /**
     * 看服务器是否识别到请求，识别到则调用
     */
    @SneakyThrows
    public static void getPosts(Post[] posts) {
        iconLength = posts.length;

        //获取所有发送请求的人的姓名
        for (int i = 0; i < iconLength; i++) {
            iconName[i] = posts[i].getPost();
        }

        Constant.getSbIcon = true;
    }

    static boolean setContextLabelsFlag = true;//setContext中的每个标签是否第一次声明

    /**
     * 设置标签内容
     */
    @SneakyThrows
    public static void setContext() {

        //上下拖动条
        ImageIcon downIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/down.png")));
        ImageIcon upIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/up.png")));

        JLabel downIconLabel = new JLabel();
        downIconLabel.setIcon(downIcon);
        downIconLabel.setBounds(contextGet.getWidth() - 30, 0, 30, 30);
        contextGet.add(downIconLabel);

        JLabel upIconLabel = new JLabel();
        upIconLabel.setIcon(upIcon);
        upIconLabel.setBounds(contextGet.getWidth() - 30, contextGet.getHeight() - 30, 30, 30);
        contextGet.add(upIconLabel);

        for (int i = 0; i < iconLength; i++) {
            labels[i] = new RadioJLabel("");
            labels[i].setColor(new Color(253, 252, 252));
            contextGet.add(labels[i]);
            labels[i].setBounds(15, height, background1.getWidth() - 20 - 10, 140);

            //修改一下图像大小
            BufferedImage realIcon = ToBufferedImage.toBufferedImage(images[0].getScaledInstance(50, 50, 0));  //将图片改为合适的大小，并转化为BufferedImage
            //去除黑色背景
            BufferedImage transparencyIcon = RemoveBackground.ByteToBufferedImage(RemoveBackground.transferAlpha(realIcon));

            iconLabels[i] = new JLabel();
            iconLabels[i].setIcon(new ImageIcon(transparencyIcon));
            iconLabels[i].setBounds(15, 15, 50, 50);
            labels[i].add(iconLabels[i]);

            texts[i] = new DynamicJLabel(iconName[i], new Font("Serif", Font.BOLD, 22), 23);
            texts[i].setCenter(260);
            texts[i].setForeground(new Color(7, 7, 7));
            labels[i].add(texts[i]);

            //接收按钮
            accept[i] = new RadioJLabel("");
            accept[i].setColor(new Color(69, 31, 124));
            accept[i].setBounds(15, 74, 170, 50);
            accept[i].setArc(40, 40);
            labels[i].add(accept[i]);

            refuse[i] = new RadioJLabel("");
            refuse[i].setColor(new Color(229, 227, 227));
            refuse[i].setBounds(205, 74, 170, 50);
            refuse[i].setArc(40, 40);
            labels[i].add(refuse[i]);

            //接收按钮和拒绝按钮设置事件
            //接收按钮:
            //同意后会发送给EchoClient,EchoClient发送给服务器，让服务器创建一个名为username-friend的表，并把这个表存在两个人的好友表中
            int finalI = i;
            final int[] d = {0};
            HashMap<Integer, Boolean> finishedLabel = new HashMap<>(iconLength);
            for (int j = 0; j < iconLength; j++) {
                finishedLabel.put(j, false);
            }
            accept[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new Thread() {
                        @Override
                        public void run() {
                            //修改addFriend中state为1,并加到两个人的好友列表中
                            Constant.addStateName = iconName[finalI];
                            Constant.addState = true;
                            contextGet.remove(labels[finalI]);
                            contextGet.repaint();
                            background1.repaint();

                            if (finalI != iconLength - 1) {  //移动剩余的标签，且标签不能为最后一个
                                for (int j = finalI + 1; j < iconLength; j++) {
                                    if (!finishedLabel.get(finalI)) {
                                        labels[j].setBounds(labels[j].getX(), labels[j].getY() - 150, labels[j].getWidth(), labels[j].getHeight());
                                        for (int k = 0; k < iconLength; k++) {
                                            if (!finishedLabel.get(finalI)) {
                                                labels[j].repaint();
                                                contextGet.repaint();
                                                background1.repaint();
                                            }
                                        }
                                    }
                                }
                            }
                            finishedLabel.put(finalI, true);
                            searchText.setText("已同意好友邀请");
                            searchText.setForeground(new Color(62, 171, 159));

                        }
                    }.start();
                }
            });

            //拒绝事件处理
            int finalI1 = i;
            refuse[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //从addFriend中删除该条指令，并让所有标签上移
                    Constant.deleteAddFriendName = iconName[finalI1];
                    Constant.deleteAddFriend = true;

                    contextGet.remove(labels[finalI]);
                    contextGet.repaint();
                    background1.repaint();

                    if (finalI != iconLength - 1) {  //移动剩余的标签，且标签不能为最后一个
                        for (int j = finalI + 1; j < iconLength; j++) {
                            if (!finishedLabel.get(finalI)) {
                                labels[j].setBounds(labels[j].getX(), labels[j].getY() - 150, labels[j].getWidth(), labels[j].getHeight());
                                for (int k = 0; k < iconLength; k++) {
                                    if (!finishedLabel.get(finalI)) {
                                        labels[j].repaint();
                                        contextGet.repaint();
                                        background1.repaint();
                                    }
                                }
                            }
                        }
                    }
                    finishedLabel.put(finalI, true);
                    searchText.setText("已拒绝好友邀请");
                    searchText.setForeground(new Color(102, 48, 180));

                }
            });


            DynamicJLabel acceptMessage = new DynamicJLabel("Accept", new Font("Serif", Font.BOLD, 18), 6);
            acceptMessage.setForeground(new Color(255, 255, 255));
            acceptMessage.setCenter(accept[i].getWidth());
            accept[i].add(acceptMessage);

            DynamicJLabel refuseMessage = new DynamicJLabel("Refuse", new Font("Serif", Font.BOLD, 18), 6);
            refuseMessage.setForeground(new Color(7, 7, 7));
            refuseMessage.setCenter(refuse[i].getWidth());
            refuse[i].add(refuseMessage);

            height += 150;
        }

        //上升下降添加点击事件
        contextGet.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() == 1) {  //向前
                    for (int i = 0; i < iconLength; i++) {
                        labels[i].setBounds(labels[i].getX(), labels[i].getY() - 10, labels[i].getWidth(), labels[i].getHeight());
                    }
                }
                if (e.getWheelRotation() == -1) {
                    for (int i = 0; i < iconLength; i++) {
                        labels[i].setBounds(labels[i].getX(), labels[i].getY() + 10, labels[i].getWidth(), labels[i].getHeight());
                    }
                }
            }
        });
        downIconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < iconLength; i++) {
                    labels[i].setBounds(labels[i].getX(), labels[i].getY() - 1, labels[i].getWidth(), labels[i].getHeight());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                for (int j = 0; j < 10; j++) {
                    for (int i = 0; i < iconLength; i++) {
                        labels[i].setBounds(labels[i].getX(), labels[i].getY() - 1, labels[i].getWidth(), labels[i].getHeight());
                    }
                }
            }


        });

        upIconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < iconLength; i++) {
                    labels[i].setBounds(labels[i].getX(), labels[i].getY() + 1, labels[i].getWidth(), labels[i].getHeight());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                for (int j = 0; j < 10; j++) {
                    for (int i = 0; i < iconLength; i++) {
                        labels[i].setBounds(labels[i].getX(), labels[i].getY() + 1, labels[i].getWidth(), labels[i].getHeight());
                    }
                }
            }


        });
    }

    public static int heightGet = 0;  //邀请的高度

    /**
     * 获取所有已发送的请求
     */
    @SneakyThrows
    public static void getGets(Post[] posts) {
        //储存我发送的请求
        getsPosts = posts;
        iconLengthGet = posts.length;

        //获取所有发送请求的人的姓名
        for (int i = 0; i < iconLengthGet; i++) {
            iconNameGet[i] = posts[i].getGeter();
        }

        Constant.postSbIcon = true;
    }

    static boolean setContextGetLabelsFlag = true;  //是否是第一次声明标签

    /**
     * 设置标签内容
     */
    @SneakyThrows
    public static void setContextGet() {
        ImageIcon downIconGet;
        ImageIcon upIconGet;
        JLabel downIconLabelGet = null;
        JLabel upIconLabelGet = null;
        //上下拖动条
        downIconGet = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/down.png")));
        upIconGet = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/up.png")));

        downIconLabelGet = new JLabel();
        downIconLabelGet.setIcon(downIconGet);
        downIconLabelGet.setBounds(contextPost.getWidth() - 30, 0, 30, 30);
        contextPost.add(downIconLabelGet);

        upIconLabelGet = new JLabel();
        upIconLabelGet.setIcon(upIconGet);
        upIconLabelGet.setBounds(contextPost.getWidth() - 30, contextPost.getHeight() - 30, 30, 30);
        contextPost.add(upIconLabelGet);

        for (int i = 0; i < iconLengthGet; i++) {
            labelsGet[i] = new RadioJLabel("");
            labelsGet[i].setColor(new Color(253, 252, 252));
            labelsGet[i].setBounds(15, heightGet, background1.getWidth() - 20 - 10, 80);

            contextPost.add(labelsGet[i]);

            //修改一下图像大小
            BufferedImage realIcon = ToBufferedImage.toBufferedImage(imagesGet[0].getScaledInstance(50, 50, 0));  //将图片改为合适的大小，并转化为BufferedImage
            //去除黑色背景
            BufferedImage transparencyIcon = RemoveBackground.ByteToBufferedImage(RemoveBackground.transferAlpha(realIcon));

            iconLabelsGet[i] = new JLabel();
            iconLabelsGet[i].setBounds(15, 15, 50, 50);
            labelsGet[i].add(iconLabelsGet[i]);
            iconLabelsGet[i].setIcon(new ImageIcon(transparencyIcon));

            textsGet[i] = new DynamicJLabel(iconNameGet[i], new Font("Serif", Font.BOLD, 22), 14);
            textsGet[i].setCenter(240);
            textsGet[i].setForeground(new Color(7, 7, 7));

            labelsGet[i].add(textsGet[i]);


            DynamicJLabel sendMessageGet;
            if (getsPosts[i].getState().equals("0")) {
                sendMessageGet = new DynamicJLabel("邀请等待同意···", new Font("Serif", Font.BOLD, 13), 50);
            } else {
                sendMessageGet = new DynamicJLabel("邀请已通过", new Font("Serif", Font.BOLD, 13), 50);
            }
            sendMessageGet.setForeground(new Color(62, 171, 159));
            sendMessageGet.setCenter(labelsGet[i].getWidth() - 140);
            labelsGet[i].add(sendMessageGet);


            heightGet += 90;
        }


        //上升下降添加点击事件
        contextPost.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() == 1) {  //向前
                    for (int i = 0; i < iconLengthGet; i++) {
                        labelsGet[i].setBounds(labelsGet[i].getX(), labelsGet[i].getY() - 10, labelsGet[i].getWidth(), labelsGet[i].getHeight());
                    }
                }
                if (e.getWheelRotation() == -1) {
                    for (int i = 0; i < iconLengthGet; i++) {
                        labelsGet[i].setBounds(labelsGet[i].getX(), labelsGet[i].getY() + 10, labelsGet[i].getWidth(), labelsGet[i].getHeight());
                    }
                }
            }
        });
        downIconLabelGet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < iconLengthGet; i++) {
                    labelsGet[i].setBounds(labelsGet[i].getX(), labelsGet[i].getY() - 1, labelsGet[i].getWidth(), labelsGet[i].getHeight());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                for (int j = 0; j < 10; j++) {
                    for (int i = 0; i < iconLengthGet; i++) {
                        labelsGet[i].setBounds(labelsGet[i].getX(), labelsGet[i].getY() - 1, labelsGet[i].getWidth(), labelsGet[i].getHeight());
                    }
                }
            }


        });

        upIconLabelGet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < iconLengthGet; i++) {
                    labelsGet[i].setBounds(labelsGet[i].getX(), labelsGet[i].getY() + 1, labelsGet[i].getWidth(), labelsGet[i].getHeight());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                for (int j = 0; j < 10; j++) {
                    for (int i = 0; i < iconLengthGet; i++) {
                        labelsGet[i].setBounds(labelsGet[i].getX(), labelsGet[i].getY() + 1, labelsGet[i].getWidth(), labelsGet[i].getHeight());
                    }
                }
            }


        });
    }

    /**
     * 初始化聊天界面
     */
    @SneakyThrows
    public static void InitChat(RadioJLabelNew home) {

        background2 = new RadioJLabel("");
        background2.setColor(new Color(239, 238, 238));
        background2.setArc(25, 25);
        background2.setBounds(0, 0, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());

        //上半部分
        DynamicJLabel LeftTopMessageChat = new DynamicJLabel("好友聊天", new Font("Serif", Font.BOLD, 22), 25);
        LeftTopMessageChat.setCenter(background2.getWidth() / 2 - 45 - 10);

        //搜索框
        ImageIcon searchIconChat = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/search.png")));
        RadioJLabel searchChat = new RadioJLabel("");
        searchChat.setColor(new Color(229, 227, 227));
        searchChat.setBounds(30, 80, background2.getWidth() - 30 - 20, 70);
        searchChat.setArc(60, 60);
        RadioJLabel searchIconLabelChat = new RadioJLabel(searchIconChat);
        searchIconLabelChat.setColor(new Color(229, 227, 227));
        searchIconLabelChat.setBounds(14, 18, searchIconChat.getIconWidth(), searchIconChat.getIconHeight());
        searchChat.add(searchIconLabelChat);

        searchTextChat = new JTextField();
        searchChat.add(searchTextChat);  //加入搜索框
        searchTextChat.setBounds(64, 5, searchChat.getWidth() - 70, searchChat.getHeight() - 10);
        searchTextChat.setFont(new Font("Serif", Font.BOLD, 20));
        searchTextChat.setForeground(new Color(79, 78, 78));
        searchTextChat.setText("输入用户名");
//        searchTextChat.setHorizontalAlignment(JTextField.HORIZONTAL);
        searchTextChat.setOpaque(false);
        searchTextChat.setBorder(null);

        searchTextChat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (searchTextChat.getText().contains("输入用户名") || searchTextChat.getText().contains("已发送好友邀请") || searchTextChat.getText().contains("用户名不能为空") || searchTextChat.getText().contains("用户名输入错误") || searchTextChat.getText().equals("已同意好友邀请") || searchTextChat.getText().equals("已拒绝好友邀请")) {  //若要输入账号则取消提示
                    searchTextChat.setForeground(new Color(79, 78, 78));
                    searchTextChat.setText("");
                }
            }
        });
        searchTextChat.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchTextChat.getText().equals("输入用户名") || searchTextChat.getText().equals("已发送好友邀请") || searchTextChat.getText().equals("用户名不能为空") || searchTextChat.getText().equals("用户名输入错误") || searchTextChat.getText().equals("已同意好友邀请") || searchTextChat.getText().equals("已拒绝好友邀请")) {  //若要输入账号则取消提示
                    searchTextChat.setForeground(new Color(79, 78, 78));
                    searchTextChat.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchTextChat.getText().equals("")) {   //若未输入内容则重新提示
                    searchTextChat.setForeground(new Color(79, 78, 78));
                    searchTextChat.setText("输入用户名");
                }
            }
        });

        //搜索加入键盘监听
        searchTextChat.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {  //搜索
                    friendName = searchTextChat.getText();  //储存用户名
                    if (friendName.length() >= 6) {
                        Constant.addFriend = true;
                    } else {
                        searchTextChat.setText("用户名不能为空");
                        searchTextChat.setForeground(new Color(161, 19, 19));
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                PlaySound.play("sound/error.mp3");
                            }
                        }.start();
                    }
                }
            }
        });

        //搜索图标加入点击事件
        searchIconLabelChat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String friendNameChat = searchTextChat.getText();  //储存用户名
                if (friendNameChat.length() >= 6) {


                } else {
                    searchTextChat.setText("用户名不能为空");
                    searchTextChat.setForeground(new Color(161, 19, 19));
                    new Thread() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            PlaySound.play("sound/error.mp3");
                        }
                    }.start();
                }
            }
        });

        background2.add(LeftTopMessageChat);
        background2.add(searchChat);

        //下半部分
        contextChat = new RadioJLabel("");
        contextChat.setColor(new Color(239, 238, 238));
        contextChat.setBounds(0, 150 + 20, background2.getWidth(), background2.getHeight() - (blackBack.getY() + blackBack.getHeight() + 10));


        Constant.checkFriends = true;  //获取请求

        background2.add(contextChat);

        home.add(background2);

    }


    public static int heightChat = 0;  //邀请的高度

    /**
     * 获得所有好友的姓名
     * 新加入:boolean flag
     * 如果flag是true,则获取所有名字的同时加载头像并创建标签
     * 如果flag是false,则只获得所有名字
     */
    @SneakyThrows
    public static void getChat(Friends[] friends, boolean flag) {
        //储存我发送的请求
        getsChats = friends;  //所有好友信息
        iconLengthChat = friends.length;  //好友长度
        iconNameChat = new String[iconLengthChat]; //存储用户姓名信息
        //获取所有发送请求的人的姓名
        for (int i = 0; i < iconLengthChat; i++) {
            iconNameChat[i] = friends[i].getFriends();
        }

        if (flag) {
            Constant.friendsAmount = iconLengthChat;
            Constant.getFriendIcon = true;
        }
    }

    /**
     * 设置标签内容
     */
    @SneakyThrows
    public static void setContextChat(BufferedImage[] icons, String type) {
        Chaticons = icons;  //储存全部好友头像
        Constant.getFriendIconsSuccess = true;

        if (type.equals("all")) {  //只更新新加入的头像
            {  //初始化状态图像
                stateIcon = new ArrayList<>();
                messageIcon = new ArrayList<>();
                stateIconBack = new ArrayList<>();
                for (int i = 0; i < iconLengthChat; i++) {  //初始化所有
                    stateIcon.add(new RadioJLabel(""));
                    messageIcon.add(new RadioJLabel(""));
                    stateIconBack.add(new RadioJLabel(""));
                }
                //所有最新显示消息
                latestMessages = new ArrayList<>();
            }

            //上下拖动条
            ImageIcon downIconChat = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/down.png")));
            ImageIcon upIconChar = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/up.png")));

            JLabel downIconLabelChar = new JLabel();
            downIconLabelChar.setIcon(downIconChat);
            downIconLabelChar.setBounds(contextGet.getWidth() - 30, 0, 30, 30);
            contextChat.add(downIconLabelChar);

            JLabel upIconLabelChar = new JLabel();
            upIconLabelChar.setIcon(upIconChar);
            upIconLabelChar.setBounds(contextGet.getWidth() - 30, contextGet.getHeight() - 30, 30, 30);
            contextChat.add(upIconLabelChar);

            labelsChar = new ArrayList<>();
            iconLabelsChar = new ArrayList<>();
            textsChar = new ArrayList<>();
            for (int i = 0; i < iconLengthChat; i++) {
                labelsChar.add(new RadioJLabel(""));
                labelsChar.get(i).setColor(new Color(253, 252, 252));
                labelsChar.get(i).add(stateIcon.get(i));
                labelsChar.get(i).add(messageIcon.get(i));
                labelsChar.get(i).add(stateIconBack.get(i));
                stateIconBack.get(i).setSize(0, 0);
                contextChat.add(labelsChar.get(i));
                labelsChar.get(i).setBounds(15, heightChat, background1.getWidth() - 20 - 10, 80);

                //修改一下图像大小
                BufferedImage realIcon = ToBufferedImage.toBufferedImage(icons[i].getScaledInstance(50, 50, 0));  //将图片改为合适的大小，并转化为BufferedImage
                //去除黑色背景
                BufferedImage transparencyIcon = RemoveBackground.ByteToBufferedImage(RemoveBackground.transferAlpha(realIcon));

                //好友头像
                iconLabelsChar.add(new JLabel());
                iconLabelsChar.get(i).setIcon(new ImageIcon(transparencyIcon));
                iconLabelsChar.get(i).setBounds(15, 15, 50, 50);
                labelsChar.get(i).add(iconLabelsChar.get(i));

                //好友姓名
                textsChar.add(new DynamicJLabel(iconNameChat[i], new Font("Serif", Font.BOLD, 22), 14));
                textsChar.get(i).setCenter(240);
                textsChar.get(i).setForeground(new Color(7, 7, 7));
                labelsChar.get(i).add(textsChar.get(i));

                //存储用户名与界面的关系
                userContent.put(iconNameChat[i], i);
                putInUserContentIsFinished = true;  //已经成功放入姓名到userContent



                //聊天标签添加点击事件
                int finalI = i;
                labelsChar.get(i).addMouseListener(new MouseAdapter() {
                    @SneakyThrows
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {  //双击后
                            //关闭上一次打开的页面
                            if (OPENINDEX[0] != -1) {  //若不是第一次打开
                                chatContent.get(OPENINDEX[0]).setSize(0, 0);  //关闭界面
                            }
                            OPENINDEX[0] = finalI;  //记录当前打开位置

                            chatContent.get(finalI).setBounds(360, 70, 700, 700);
                            //判断是否新消息红点亮了，如果亮了就取消红点
                            if (messageIcon.get(finalI).getSize().getHeight() == 8) {  //若亮了则高度和宽度为8
                                messageIcon.get(finalI).setSize(0, 0);  //取消红点
                            }

//                        MenuContent.initMessage(iconNameChat[finalI]);  //初始化聊天内容
                        }
                    }

                });

                //显示最近的消息
                latestMessages.add(new DynamicJLabel("aaa", new Font("Serif", Font.BOLD, 22), 39));

                latestMessages.get(i).setForeground(new Color(98, 97, 97));
                latestMessages.get(i).setCenter(230);
                labelsChar.get(i).add(latestMessages.get(i));

                heightChat += 90;


            }


            //上升下降添加点击事件
            contextChat.addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    if (e.getWheelRotation() == 1) {  //向前
                        for (int i = 0; i < iconLengthChat; i++) {
                            labelsChar.get(i).setBounds(labelsChar.get(i).getX(), labelsChar.get(i).getY() - 10, labelsChar.get(i).getWidth(), labelsChar.get(i).getHeight());
                        }
                    }
                    if (e.getWheelRotation() == -1) {
                        for (int i = 0; i < iconLengthChat; i++) {
                            labelsChar.get(i).setBounds(labelsChar.get(i).getX(), labelsChar.get(i).getY() + 10, labelsChar.get(i).getWidth(), labelsChar.get(i).getHeight());
                        }
                    }
                }
            });
            downIconLabelChar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (int i = 0; i < iconLengthChat; i++) {
                        labelsChar.get(i).setBounds(labelsChar.get(i).getX(), labelsChar.get(i).getY() - 1, labelsChar.get(i).getWidth(), labelsChar.get(i).getHeight());
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    for (int j = 0; j < 10; j++) {
                        for (int i = 0; i < iconLengthChat; i++) {
                            labelsChar.get(i).setBounds(labelsChar.get(i).getX(), labelsChar.get(i).getY() - 1, labelsChar.get(i).getWidth(), labelsChar.get(i).getHeight());
                        }
                    }
                }


            });

            upIconLabelChar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (int i = 0; i < iconLengthChat; i++) {
                        labelsChar.get(i).setBounds(labelsChar.get(i).getX(), labelsChar.get(i).getY() + 1, labelsChar.get(i).getWidth(), labelsChar.get(i).getHeight());
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    for (int j = 0; j < 10; j++) {
                        for (int i = 0; i < iconLengthChat; i++) {
                            labelsChar.get(i).setBounds(labelsChar.get(i).getX(), labelsChar.get(i).getY() + 1, labelsChar.get(i).getWidth(), labelsChar.get(i).getHeight());
                        }
                    }
                }


            });
            Constant.getUserState = true;
            Constant.whetherFriendsToTableIndex = true;
        }else if (type.equals("new")) {
            {  //初始化状态图像
                for (int i = Constant.friendsAmount; i < iconLengthChat; i++) {  //初始化所有
                    stateIcon.add(new RadioJLabel(""));
                    messageIcon.add(new RadioJLabel(""));
                    stateIconBack.add(new RadioJLabel(""));
                }
            }

            for (int i = Constant.friendsAmount; i < iconLengthChat; i++) {
                labelsChar.add(new RadioJLabel(""));
                labelsChar.get(i).setColor(new Color(253, 252, 252));
                labelsChar.get(i).add(stateIcon.get(i));
                labelsChar.get(i).add(messageIcon.get(i));
                labelsChar.get(i).add(stateIconBack.get(i));
                stateIconBack.get(i).setSize(0, 0);
                contextChat.add(labelsChar.get(i));
                labelsChar.get(i).setBounds(15, heightChat, background1.getWidth() - 20 - 10, 80);

                //修改一下图像大小
                BufferedImage realIcon = ToBufferedImage.toBufferedImage(icons[i].getScaledInstance(50, 50, 0));  //将图片改为合适的大小，并转化为BufferedImage
                //去除黑色背景
                BufferedImage transparencyIcon = RemoveBackground.ByteToBufferedImage(RemoveBackground.transferAlpha(realIcon));

                //好友头像
                iconLabelsChar.add(new JLabel());
                iconLabelsChar.get(i).setIcon(new ImageIcon(transparencyIcon));
                iconLabelsChar.get(i).setBounds(15, 15, 50, 50);
                labelsChar.get(i).add(iconLabelsChar.get(i));

                //好友姓名
                textsChar.add(new DynamicJLabel(iconNameChat[i], new Font("Serif", Font.BOLD, 22), 14));
                textsChar.get(i).setCenter(240);
                textsChar.get(i).setForeground(new Color(7, 7, 7));
                labelsChar.get(i).add(textsChar.get(i));

                //存储用户名与界面的关系
                userContent.put(iconNameChat[i], i);
                putInUserContentIsFinished = true;  //已经成功放入姓名到userContent


                //增加新的聊天界面
                chatContent.add(new InnerLabel());
                chatContent.get(i).setSize(0, 0);
                back.add(chatContent.get(i));


                //初始化用户聊天界面的存储信息
                //修改一下图像大小
                BufferedImage chatIcon = ToBufferedImage.toBufferedImage(Home.transparencyIcon.getScaledInstance(45, 45, 0));  //将图片改为合适的大小，并转化为BufferedImage
                //去除黑色背景
                BufferedImage newChatIcon = RemoveBackground.ByteToBufferedImage(RemoveBackground.transferAlpha(chatIcon));
                chatContent.get(i).mine = newChatIcon;  //我的头像

                //修改一下图像大小
                BufferedImage friendIcon = ToBufferedImage.toBufferedImage(MenuContent.Chaticons[i].getScaledInstance(45, 45, 0));  //将图片改为合适的大小，并转化为BufferedImage
                //去除黑色背景
                BufferedImage newFriendIcon = RemoveBackground.ByteToBufferedImage(RemoveBackground.transferAlpha(friendIcon));

                chatContent.get(i).friend = newFriendIcon;  //好友头像
                chatContent.get(i).friendName = iconNameChat[i];  //储存好友姓名

                MenuContent.initMessage(iconNameChat[i]);  //初始化聊天内容

                heightChat += 90;

                //聊天标签添加点击事件
                int finalI = i;
                labelsChar.get(i).addMouseListener(new MouseAdapter() {
                    @SneakyThrows
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {  //双击后
                            //关闭上一次打开的页面
                            if (OPENINDEX[0] != -1) {  //若不是第一次打开
                                chatContent.get(OPENINDEX[0]).setSize(0, 0);  //关闭界面
                            }
                            OPENINDEX[0] = finalI;  //记录当前打开位置

                            chatContent.get(finalI).setBounds(360, 70, 700, 700);
                            //判断是否新消息红点亮了，如果亮了就取消红点
                            if (messageIcon.get(finalI).getSize().getHeight() == 8) {  //若亮了则高度和宽度为8
                                messageIcon.get(finalI).setSize(0, 0);  //取消红点
                            }

                        }
                    }

                });

                //显示最近的消息
                latestMessages.add(new DynamicJLabel("", new Font("Serif", Font.BOLD, 22), 39));

                latestMessages.get(i).setForeground(new Color(98, 97, 97));
                latestMessages.get(i).setCenter(230);
                labelsChar.get(i).add(latestMessages.get(i));

            }

            {
                //这里进行所有界面的重新绘制，来让聊天界面在最上面
                back.remove(menu);
//                back.remove(menuTop);
                back.remove(menuBack);
                back.remove(menuHomeBack);
                back.remove(homeBack);
                back.remove(home);

                back.add(menu);  //加入菜单
//                back.add(menuTop);  //加入背景
                back.add(menuBack);  //加入背景
                back.add(menuHomeBack);  //点击菜单展开的内容背景
                menuHomeBack.repaint();
                back.add(homeBack); //加入背景
                back.add(home);  //加入主页面
            }

            while (true) {
                if (isSomeBodyFinishedFirstTime.get(iconNameChat[iconLengthChat - 1])) {
                    log.info("--创建新好友聊天界面成功!--");
                    isCreateNewInnerLabel = false;  //进程不在进行
                    break;
                }
            }
            Constant.getUserState = true;

            Constant.whetherFriendsToTableIndex = true;



        }
    }

    /**
     * 添加是否在线的绿点点
     */
    public static void setStateIcon(int[] states) {
        if (iconLengthChat == 0) {
            isInitializedChatWindowSuccessfully = true;  //初始化聊天窗口成功
            isHomeInitInnerLabelCanJump = true;   //可以跳过初始化聊天界面了
            initFinishAndCanFlashChatHistory = true;  //查询完成

        }
        Constant.getFriendStatesSuccess = true;
        for (int i = 0; i < iconLengthChat; i++) {
            if (states[i] == 0) {  //账号不在线
                stateIcon.get(i).setColor(new Color(110, 108, 108));
                stateIcon.get(i).setBounds(54, 51, 15, 15);
                stateIcon.get(i).setArc(15, 15);
                stateIcon.get(i).repaint();
            } else {  //账号在线
                stateIcon.get(i).setColor(new Color(47, 199, 47));
                stateIconBack.get(i).setColor(new Color(238, 237, 237));
                stateIcon.get(i).setBounds(54, 51, 15, 15);
                stateIconBack.get(i).setBounds(52, 49, 19, 19);
                stateIcon.get(i).setArc(15, 15);
                stateIconBack.get(i).setArc(19, 19);
                stateIcon.get(i).repaint();
                stateIconBack.get(i).repaint();

            }
//            //设置新发送信息的红点
//            messageIcon.get(i).setColor(new Color(227, 34, 34, 0));
//            messageIcon.get(i).setBounds(57, 19, 8, 8);
////            messageIcon[i].setSize(0,0);
//            messageIcon.get(i).setArc(8, 8);
//            messageIcon.get(i).repaint();

        }
    }

    /**
     * 初始化信息，即登录时就获得与所有好友的聊天记录
     */
    public static void initMessage(String friendName) {
        Constant.context.getBean(Controller.class).getChatHistory(friendName);
    }


    /**
     * 初始化颜色界面
     */
    @SneakyThrows
    public static void InitColor(RadioJLabelNew home) {
        ImageIcon icon1 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/naiyou.png")));
        ImageIcon icon2 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/haiyan.png")));
        ImageIcon icon3 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/heian.png")));
        ImageIcon icon4 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/zhezhi.png")));
        ImageIcon icon5 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/tonghua.png")));
        ImageIcon icon6 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/default.png")));

        background3 = new RadioJLabel("");
        background3.setColor(new Color(239, 238, 238));
        background3.setArc(25, 25);
        background3.setBounds(0, 0, menuIcon.getIconWidth() + 300, menuIcon.getIconHeight());

        style1 = new RadioTextJLabel("奶油", icon1, 145, 165);
        style2 = new RadioTextJLabel("海盐", icon2, 145, 165);
        style3 = new RadioTextJLabel("黑暗", icon3, 145, 165);
        style4 = new RadioTextJLabel("折纸", icon4, 145, 165);
        style5 = new RadioTextJLabel("童话", icon5, 145, 165);
        style6 = new RadioTextJLabel("默认", icon6, 145, 165);

        style1.setTextY(132);
        style2.setTextY(132);
        style3.setTextY(132);
        style4.setTextY(132);
        style5.setTextY(132);
        style6.setTextY(132);

        background3.add(style1);
        background3.add(style2);
        background3.add(style3);
        background3.add(style4);
        background3.add(style5);
        background3.add(style6);
        home.add(background3);  //加入背景

        style1.setLocation(home.getWidth() / 5 + 40, home.getHeight() / 5 + 40);
        style2.setLocation(2 * (home.getWidth() / 5 + 40) + 125, home.getHeight() / 5 + 40);
        style3.setLocation(home.getWidth() / 5 + 40, 2 * (home.getHeight() / 5 + 40) + 145);
        style4.setLocation(2 * (home.getWidth() / 5 + 40) + 125, 2 * (home.getHeight() / 5 + 40) + 145);
        style5.setLocation(home.getWidth() / 5 + 40, 3 * (home.getHeight() / 5 + 40) + 290);
        style6.setLocation(2 * (home.getWidth() / 5 + 40) + 125, 3 * (home.getHeight() / 5 + 40) + 290);

        addIconAction(style1.getIconContent(), ChangeToColor.getColorFromHex("#AD8B73"), ChangeToColor.getColorFromHex("#CEAB93"), ChangeToColor.getColorFromHex("#E3CAA5"), ChangeToColor.getColorFromHex("#FFFBE9"));
        addIconAction(style2.getIconContent(), ChangeToColor.getColorFromHex("#42C2FF"), ChangeToColor.getColorFromHex("#B8FFF9"), ChangeToColor.getColorFromHex("#B8FFF9"), ChangeToColor.getColorFromHex("#EFFFFD"));
        addIconAction(style3.getIconContent(), ChangeToColor.getColorFromHex("#21325E"), ChangeToColor.getColorFromHex("#3E497A"), ChangeToColor.getColorFromHex("#F1D00A"), ChangeToColor.getColorFromHex("#F0F0F0"));
        addIconAction(style4.getIconContent(), ChangeToColor.getColorFromHex("#C0D8C0"), ChangeToColor.getColorFromHex("#F5EEDC"), ChangeToColor.getColorFromHex("#DD4A48"), ChangeToColor.getColorFromHex("#ECB390"));
        addIconAction(style5.getIconContent(), ChangeToColor.getColorFromHex("#533E85"), ChangeToColor.getColorFromHex("#488FB1"), ChangeToColor.getColorFromHex("#4FD3C4"), ChangeToColor.getColorFromHex("#C1F8CF"));
        addIconAction(style6.getIconContent(), new Color(166, 163, 163), new Color(239, 238, 238), new Color(239, 238, 238), new Color(215, 210, 210));
    }

    /**
     * 添加点击事件处理
     */
    public static void addIconAction(RadioJLabel context, Color homePageBack, Color homePage, Color menuPage, Color menuPageBack) {
        context.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //更换主页颜色
                Menu.homeColor1 = homePageBack.getRed();
                Menu.homeColor2 = homePageBack.getGreen();
                Menu.homeColor3 = homePageBack.getBlue();
                Menu.homeColorBack1 = homePage.getRed();
                Menu.homeColorBack2 = homePage.getGreen();
                Menu.homeColorBack3 = homePage.getBlue();
                Menu.menuHomeColor1 = menuPage.getRed();
                Menu.menuHomeColor2 = menuPage.getGreen();
                Menu.menuHomeColor3 = menuPage.getBlue();

                home.setColor(new Color(homePageBack.getRed(), homePageBack.getGreen(), homePageBack.getBlue(), 100));
                homeBack.setColor(new Color(homePage.getRed(), homePage.getGreen(), homePage.getBlue(), 100));
                menuHomeUser.setColor(new Color(menuPage.getRed(), menuPage.getGreen(), menuPage.getBlue(), 0));
                menuHomeUser1.setColor(new Color(menuPage.getRed(), menuPage.getGreen(), menuPage.getBlue(), 0));
                menuHomeUser2.setColor(new Color(menuPage.getRed(), menuPage.getGreen(), menuPage.getBlue(), 0));
                menuHomeUser3.setColor(new Color(menuPage.getRed(), menuPage.getGreen(), menuPage.getBlue()));
                menuHomeUser4.setColor(new Color(menuPage.getRed(), menuPage.getGreen(), menuPage.getBlue(), 0));
                menuHomeUser5.setColor(new Color(menuPage.getRed(), menuPage.getGreen(), menuPage.getBlue(), 0));
                menuHomeUser6.setColor(new Color(menuPage.getRed(), menuPage.getGreen(), menuPage.getBlue(), 0));
                background3.setColor(new Color(menuPage.getRed(), menuPage.getGreen(), menuPage.getBlue(), 0));
                background1.setColor(new Color(menuPage.getRed(), menuPage.getGreen(), menuPage.getBlue(), 0));
                chooseBack.setColor(menuPageBack);
                chooseGet.setColor(menuPageBack);
                choosePost.setColor(menuPageBack);

                style1.setRadioColor(menuPageBack);
                style2.setRadioColor(menuPageBack);
                style3.setRadioColor(menuPageBack);
                style4.setRadioColor(menuPageBack);
                style5.setRadioColor(menuPageBack);
                style6.setRadioColor(menuPageBack);

                home.repaint();
                homeBack.repaint();
                menuHomeUser.repaint();
                menuHomeUser1.repaint();
                menuHomeUser2.repaint();
                menuHomeUser3.repaint();
                menuHomeUser4.repaint();
                menuHomeUser5.repaint();
                menuHomeUser6.repaint();
                background3.repaint();
                background1.repaint();
                style1.repaint();
                style2.repaint();
                style3.repaint();
                style4.repaint();
                style5.repaint();
                style6.repaint();
            }
        });
    }


    /**
     * 初始化网站界面
     */
    public static void InitOfficial(RadioJLabel home) {

    }

    /**
     * 初始化商城界面
     */
    public static void InitMarket(RadioJLabel home) {

    }

    /**
     * 初始化设置界面
     */
    public static void InitSetting(RadioJLabel home) {

        background6 = new RadioJLabel("");
        background6.setColor(new Color(252, 249, 249));
        background6.setArc(25, 25);
        background6.setBounds(0, 0, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());

        home.add(background6);

    }


}
