package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javen.service.IUserService;
import com.javen.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.javen.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 处理有关于用户的操作
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static Logger logger=Logger.getLogger(UserController.class);
    @Resource
    private IUserService userService;

    /**
     * 根据用户ID返回用户
     * @param request
     * @param response
     */
    @RequestMapping("/get")
    public void getUserById(HttpServletRequest request, HttpServletResponse response){
        User user = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int userId = Integer.parseInt(request.getParameter("id"));
            user = (User) this.userService.get(userId);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        logger.info("in /user/get! user="+user);
        myUtils.printQueryMsg(response,user);
    }

    /**
     * 获取当前登录用户
     * @param request
     * @param response
     */
    @RequestMapping("/getCurrent")
    public void getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            user = myUtils.getCurrentLocalUser(request);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        logger.info("in /user/getCurrent! user="+user);
        String listjson = JsonUtil.objectToJson(user);
        myUtils.printObjectMsg(request,response,listjson);
    }


    /**
     * 查询所有用户
     * @param request
     * @param response
     */
    @RequestMapping("/query")
    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            List<User> listUser =  userService.query();
            myUtils.printListMsg(response,listUser);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 添加用户。参数由前台编码，role默认为1
     * @param request
     * @param response
     */
    @RequestMapping("/add")
    public void addUser(HttpServletRequest request, HttpServletResponse response) {
        String message;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            User user = new User();
            String username = request.getParameter("username");
            username = java.net.URLDecoder.decode(username, "UTF-8");
            String password = request.getParameter("password");
            Date birthday;
            try {
                birthday = sdf.parse(request.getParameter("birthday"));
            }catch (Exception e){
                birthday=new Date();
            }
            String gender = request.getParameter("gender");
            gender = java.net.URLDecoder.decode(gender, "UTF-8");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            int role;
            try {
                role = Integer.parseInt(request.getParameter("role"));
            }catch (Exception e){
                role=1;
            }
            int level;
            try {
                level = Integer.parseInt(request.getParameter("level"));
            }catch (Exception e){
                level=0;
            }
            user.setUsername(username);
            user.setPassword(password);
            user.setBirthday(birthday);
            user.setGender(gender);
            user.setPhone(phone);
            user.setEmail(email);
            user.setRole(role);
            user.setLevel(level);
            user.setIsdeleted(false);
            this.userService.add(user);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
            logger.error(e.getMessage());
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 删除指定用户，更新其删除标志位
     * @param request
     * @param response
     */
    @RequestMapping("/delete")
    public void deleteUserById(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            this.userService.delete(id);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 更新用户信息，并更新ssession缓存
     * @param request
     * @param response
     */
    @RequestMapping("/update")
    public void updateUser(HttpServletRequest request, HttpServletResponse response) {
        String message;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            return;
        }
        HttpSession session=null;
        if (cookies!=null && cookies.length>0) {
            for (Cookie c : cookies) {
                if (c.getName().equals("sessionid")) {
                    String sessionid = c.getValue();
                    session = (HttpSession) config.sessionmap.get(sessionid);
                }
            }
        }
        try {
            if (session==null){
                return;
            }
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            User user = new User();
            int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            username = java.net.URLDecoder.decode(username, "UTF-8");
            String password = request.getParameter("password");
            Date birthday = sdf.parse(request.getParameter("birthday"));
            String gender = request.getParameter("gender");
            gender = java.net.URLDecoder.decode(gender, "UTF-8");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            int role = Integer.parseInt(request.getParameter("role"));
            int level = Integer.parseInt(request.getParameter("level"));
            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);
            user.setBirthday(birthday);
            user.setGender(gender);
            user.setPhone(phone);
            user.setEmail(email);
            user.setRole(role);
            user.setLevel(level);
            this.userService.update(user);
            if (((User)session.getAttribute("userinfo")).getRole()!=0){
                session.setAttribute("id", user.getId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("userinfo",user);
            }
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
            logger.error(e.getMessage());
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 检查用户是否合法登录，记录日志并保存在session中，并将sessionid返回
     * @param request
     * @param response
     */
    @RequestMapping("/login")
    public void userLogin(HttpServletRequest request, HttpServletResponse response) {
        String message = null;
        User u = null;
        String sessionid = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username==null || password==null || username.equals("") || password.equals("")){
                message=config.NO;
            }
            else if(username.indexOf("'")>-1 || password.indexOf("'")>-1 ||
                    username.indexOf("\"")>-1 || password.indexOf("\"")>-1){
                message=config.NO;
            }
            else if(username.indexOf("=")>-1 || password.indexOf("=")>-1 ||
                    username.indexOf("|")>-1 || password.indexOf("|")>-1 ||
                    username.indexOf("\\")>-1 || password.indexOf("\\")>-1 ||
                    username.indexOf("&")>-1 || password.indexOf("&")>-1 ||
                    username.indexOf(">")>-1 || password.indexOf(">")>-1 ||
                    username.indexOf("<")>-1 || password.indexOf("<")>-1){
                message=config.NO;
            }
            else {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                List<User> list = this.userService.login(user);
                if (list.size() == 1) {
                    u = list.get(0);
                    message = config.SUCCESS;
                    HttpSession session = request.getSession();
                    logger.info("用户" + u.getId() + " " + u.getUsername() + "在时间" + new Date() + "成功登录系统！");
                    session.setAttribute("id", u.getId());
                    session.setAttribute("username", username);
                    session.setAttribute("userinfo", u);
                    sessionid = session.getId();
                    config.sessionmap.put(session.getId(), session);
                    config.userip.put(u.getId(), request.getRemoteAddr());
                } else {
                    message = config.ERROR;
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        try {
            String listjson = JsonUtil.objectToJson(u);
            String jsonstring="{\"info\":\""+message+"\",\"data\":"+listjson+",\"sessionid\":\"" + sessionid + "\"}";
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
        }catch (IOException io){
            logger.error(io.getMessage());
        }
    }

    /**
     * 用户退出系统，并清除cookie
     * @param request
     * @param response
     */
    @RequestMapping("/loginout")
    public void userLoginOut(HttpServletRequest request, HttpServletResponse response) {
        String sessionid=null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Cookie[] cookies = request.getCookies();
            if (cookies!=null && cookies.length>0) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("sessionid")){
                        sessionid=c.getValue();
                        HttpSession session = (HttpSession)config.sessionmap.get(sessionid);
                        if (session==null){
                            logger.info("未知用户在时间"+new Date()+"退出系统！");
                        }
                        else {
                            User user=(User) session.getAttribute("userinfo");
                            logger.info("用户"+user.getId()+" "+user.getUsername()+"在时间"+new Date()+"退出系统！");
                            config.userip.remove(user.getId());
                            session.invalidate();
                        }
                        break;
                    }
                }
                logger.info("未知用户在时间"+new Date()+"退出系统！");
            }
            else {
                logger.info("未知用户在时间"+new Date()+"退出系统！");
            }
            if (sessionid!=null){
                config.sessionmap.remove(sessionid);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        String message=config.SUCCESS;
        myUtils.printMsg(request,response,message);
    }

    /**
     * 判断用户是否允许登录，若当前会话有效，则说明该用户已登录，拒绝重复登录
     * @param request
     * @param response
     */
    @RequestMapping("/islogin")
    public void checkSession(HttpServletRequest request, HttpServletResponse response) {
        String sessionid = request.getParameter("sessionid");
        HttpSession session = (HttpSession)config.sessionmap.get(sessionid);
        String message;
        if (session==null){
            message=config.YES;
        }
        else {
            message=config.NO;
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 判断权限并进行权限控制，根据前端URL进行路由控制，最后判断是否异地登录
     * @param request
     * @param response
     * 仅用户、管理员具有查看用户信息的权限
     * 仅用户、管理员具有查看用户订单的权限
     * 仅销售商、管理员具有商品类别管理的权限
     * 仅销售商、管理员具有产品信息管理的权限
     * 仅销售商、管理员具有订单管理的权限
     * 仅管理员具有用户管理的权限
     * 仅管理员具有等级管理的权限
     * 仅管理员具有权限管理的权限
     */
    @RequestMapping("/check")
    public void checkTest(HttpServletRequest request, HttpServletResponse response) {
        String sessionid = request.getParameter("sessionid");
        String href = request.getParameter("href");
        logger.info("check href:"+href);
        HttpSession session = (HttpSession)config.sessionmap.get(sessionid);
        String message;
        if (session==null){
            message=config.ERROR;
        }
        else {
            User user=(User) session.getAttribute("userinfo");
            logger.info("当前用户为:"+user.getUsername()+" 用户角色为："+user.getRole());
            if (href.indexOf("userinfo")>0 && user.getRole()!=0 && user.getRole()!=1 ){
                logger.info("当前用户没有userinfo权限");
                message=config.NO;
            }
            else if (href.indexOf("personalOrder")>0 && user.getRole()!=0 && user.getRole()!=1 ){
                logger.info("当前用户没有personalOrder权限");
                message=config.NO;
            }
            else if (href.indexOf("categorysManagement")>0 && user.getRole()!=0 && user.getRole()!=2 ){
                logger.info("当前用户没有categorysManagement权限");
                message=config.NO;
            }
            else if (href.indexOf("productsManagement")>0 && user.getRole()!=0 && user.getRole()!=2 ){
                logger.info("当前用户没有productsManagement权限");
                message=config.NO;
            }
            else if (href.indexOf("ordersManagement")>0 && user.getRole()!=0 && user.getRole()!=2 ){
                logger.info("当前用户没有ordersManagement权限");
                message=config.NO;
            }
            else if (href.indexOf("usersManagement")>0 && user.getRole()!=0){
                logger.info("当前用户没有usersManagement权限");
                message=config.NO;
            }
            else if (href.indexOf("levelManagement")>0 && user.getRole()!=0){
                logger.info("当前用户没有levelManagement权限");
                message=config.NO;
            }
            else if (href.indexOf("authorizationManagement")>0 && user.getRole()!=0){
                logger.info("当前用户没有authorizationManagement权限");
                message=config.NO;
            }
            else {
                String userip = (String) config.userip.get(user.getId());
                String remoteaddress = request.getRemoteAddr();
                logger.info("原始userip:" + userip + " 客户端remoteaddress:" + remoteaddress);
                if (userip.equals(remoteaddress)) {
                    message = config.SUCCESS;
                } else {
                    message = config.BAD;
                    logger.info("当前用户在"+new Date()+"异地登录！");
                }
                if (!message.equals(config.SUCCESS)){
                    session.invalidate();
                }
            }
        }
        myUtils.printMsg(request,response,message);
    }
}
