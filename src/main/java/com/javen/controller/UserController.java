package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javen.service.IUserService;
import com.javen.util.JsonUtil;
import com.javen.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.javen.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//@CrossOrigin(origins = "*",maxAge = 3600)        //允许跨域
@Controller
@RequestMapping("/user")    //"/user/**"
public class UserController {

    @Resource
    private IUserService userService;

    @RequestMapping("/get")
    public void getUserById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = (User) this.userService.get(userId);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(user));
        response.getWriter().close();
    }

    @RequestMapping("/getCurrent")
    public void getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

//        Cookie[] cookies = request.getCookies();    //从用户的cookie中取出用户信息
//        System.out.println("getCurrentUser! cookies:"+cookies);
//        User user=null;
//        if (cookies!=null && cookies.length>0) {
//            for (Cookie c : cookies) {
//                System.out.println(c.getName() + "--->" + c.getValue());
//                if (c.getName().equals("sessionid")) {
//                    String sessionid = c.getValue();
//                    HttpSession session = (HttpSession) config.sessionmap.get(sessionid);
//                    if (session == null) {
//                        System.out.println("session为空！");
//                    } else {
//                        System.out.println("session不为空！");
//                        int id = (Integer) session.getAttribute("id");
//                        user = (User) userService.get(id);
//                    }
//                    break;
//                }
//            }
//        }
//        User user = (User) config.sessionmap.get("userinfo");
//        System.out.println("当前user信息："+user.getId()+" "+user.getUsername());
        User user = myUtils.getCurrentLocalUser(request);
        String listjson = JsonUtil.objectToJson(user);
        myUtils.printObjectMsg(request,response,listjson);
//        String callback = request.getParameter("callback");
//        String listjson = JsonUtil.objectToJson(user);
//        String jsonstring=callback+"("+"{\"data\":"+listjson+"}"+")";
//        PrintWriter out = response.getWriter();
//        out.print(jsonstring);
//        out.flush();
//        out.close();
    }



    @RequestMapping("/query")
    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setHeader("Access-Control-Allow-Origin", "*");

        List<User> listUser =  userService.query();
        System.out.println("listUser.size():"+listUser.size());
//        for (int i=0;i<listUser.size();i++){
//            System.out.println(listUser.get(i).toString());
//        }
        response.setCharacterEncoding("UTF-8");
        String listjson = JsonUtil.listToJson(listUser);
        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listUser.size()+",\"recordsFiltered\":"+listUser.size()+"}";
//        String jsonstring="{data:"+listUser+",draw:1,recordsTotal:"+listUser.size()+",recordsFiltered:"+listUser.size()+"}";
//
        System.out.println("jsonstring:"+jsonstring);
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("/add")
    public void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("addUser!");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            User user = new User();
            String username = request.getParameter("username");
            username = java.net.URLDecoder.decode(username, "UTF-8");  //前台编码
            String password = request.getParameter("password");
            Date birthday;
            try {
                birthday = sdf.parse(request.getParameter("birthday"));
            }catch (Exception e){
                birthday=new Date();
            }
            String gender = request.getParameter("gender");
            gender = java.net.URLDecoder.decode(gender, "UTF-8");  //前台编码
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            int role;     //默认角色role=1，普通用户
            try {
                role = Integer.parseInt(request.getParameter("role"));
            }catch (Exception e){
                role=1;
//                e.printStackTrace();
            }
            int level;    //默认等级level=0，最低等级
            try {
                level = Integer.parseInt(request.getParameter("level"));
            }catch (Exception e){
                level=0;
//                e.printStackTrace();
            }
            user.setUsername(username);
            user.setPassword(password);
            user.setBirthday(birthday);
            user.setGender(gender);
            user.setPhone(phone);
            user.setEmail(email);
            user.setRole(role);
            user.settLevel(level);
            user.setIsdeleted(false);
            this.userService.add(user);
            message="success";
        }catch (Exception e){
            message="error";
            e.printStackTrace();
        }finally {
//            String jsonstring = JsonUtil.msgToJson(message);
            myUtils.printMsg(request,response,message);
        }
    }

    @RequestMapping("/delete")
    public void deleteUserById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("deleteUserById! id="+id);
            this.userService.delete(id);
            message="success";
        }catch (Exception e){
            message="error";
        }finally {
//            String jsonstring = JsonUtil.msgToJson(message);
//            String callback = request.getParameter("callback");
//            String jsonstring=callback+"({\"msg\":\""+message+"\"})";
//            PrintWriter out = response.getWriter();
//            out.print(jsonstring);
//            out.flush();
//            out.close();
            myUtils.printMsg(request,response,message);

        }
    }

    @RequestMapping("/update")
    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            User user = new User();
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("updateUser! id="+id);
            String username = request.getParameter("username");
            username = java.net.URLDecoder.decode(username, "UTF-8");  //前台编码
//            System.out.println("username:"+username+" "+java.net.URLDecoder.decode(username, "UTF-8")+
//                        " "+new String(username.getBytes("UTF-8"),"ISO-8859-1")+
//                        " "+new String(username.getBytes("UTF-8"),"GB2312")+
//                        " "+new String(username.getBytes("UTF-8"),"GBK")+
//                        " "+new String(username.getBytes("UTF-8"),"Unicode"));
            String password = request.getParameter("password");
            Date birthday = sdf.parse(request.getParameter("birthday"));
            String gender = request.getParameter("gender");
            gender = java.net.URLDecoder.decode(gender, "UTF-8");  //前台编码
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            int role = Integer.parseInt(request.getParameter("role"));
            int level = Integer.parseInt(request.getParameter("level"));
//            boolean delete = Boolean.parseBoolean(request.getParameter("delete"));
            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);
            user.setBirthday(birthday);
            user.setGender(gender);
            user.setPhone(phone);
            user.setEmail(email);
            user.setRole(role);
            user.settLevel(level);
//            user.setDeleted(delete);
            this.userService.update(user);
//            config.sessionmap.put("userinfo",user);     //更新userinfo
            message="success";
        }catch (Exception e){
            message="error";
            e.printStackTrace();
        }finally {
            myUtils.printMsg(request,response,message);
        }
    }

    @RequestMapping("/login")
    public void userLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        List<User> list = this.userService.login(user);
        int size = list.size();
        User u=null;
        String sessionid=null;
//        System.out.println("发现"+size+"个用户登录！");
        if (size==1){
            message="success";
            //创建session对象
            HttpSession session = request.getSession();
            //把用户数据保存在session域对象中
            u = list.get(0);
            System.out.println("用户"+u.getUsername()+"已登录成功！");
            session.setAttribute("id", u.getId());     //将用户信息存储在session中
            session.setAttribute("username", username);     //将用户信息存储在session中
            session.setAttribute("userinfo",u);

            System.out.println( "session:"+session.getAttribute("id")+session.getAttribute("username")+
                                " sessionID:"+session.getId()+" getRemoteAddr:"+request.getRemoteAddr());
//            config.sessionID=session.getId();   //保存当前sessionID及用户ID
            sessionid=session.getId();      //保存当前sessionID并返回前端
            //自动登录cookie
//            Cookie sessionid = new Cookie("sessionid", session.getId());
//            sessionid.setMaxAge(3600);  //过期时间1小时
//            sessionid.setPath("/");
//            response.addCookie(sessionid);
            config.sessionmap.put(session.getId(),session);
            config.userip.put(u.getId(),request.getRemoteAddr());
//            session.setAttribute("userip",request.getRemoteAddr()); //获取客户端IP

//            config.sessionmap.put("userinfo",list.get(0));
        }
        else{
            message="error";
        }
        String listjson = JsonUtil.objectToJson(u);
        String jsonstring="{\"info\":\""+message+"\",\"data\":"+listjson+",\"sessionid\":\"" + sessionid + "\"}";
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("/loginout")
    public void userLoginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("loginout!");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String sessionid=null;
//删除登录cookie
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length>0) {
            for (Cookie c : cookies) {
                System.out.println(c.getName() + "--->" + c.getValue());
                if (c.getName().equals("sessionid")){
                    sessionid=c.getValue();
                    HttpSession session = (HttpSession)config.sessionmap.get(sessionid);
                    if (session==null){
                        System.out.println("session为空！");
                    }
                    else {
                        System.out.println("session不为空！");
                        User user=(User) session.getAttribute("userinfo");
                        config.userip.remove(user.getId());
                        session.invalidate();
                    }
                    break;
                }
            }
            System.out.println("cookie遍历结束！");
        }
        if (sessionid!=null){
            config.sessionmap.remove(sessionid);
        }
//        User user = (User) config.sessionmap.get("userinfo");
//        Cookie userNameCookie = new Cookie("sessionid", user.getUserName());
//        Cookie passwordCookie = new Cookie("loginPassword", loginUser.getPassword());
//        userNameCookie.setMaxAge(0);
//        userNameCookie.setPath("/");
//        passwordCookie.setMaxAge(0);
//        passwordCookie.setPath("/");
//        response.addCookie(userNameCookie);
//        if (config.sessionID!=null){
//            HttpSession session = (HttpSession)config.sessionmap.get(config.sessionID);
//            session.invalidate();
//        }
//        config.sessionmap.remove(config.sessionID);         //清除session信息
//        config.sessionmap.remove("userinfo");         //清除用户信息
//        config.sessionmap.remove("orderinfo");        //清除订单信息
//        config.sessionID=null;
        String message="success";
        myUtils.printMsg(request,response,message);
    }

    @RequestMapping("/islogin")
    public void checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("in islogin()!");
        String sessionid = request.getParameter("sessionid");
        HttpSession session = (HttpSession)config.sessionmap.get(sessionid);
        String message=null;
        if (session==null){     //若当前会话有效，则说明该用户已登录
            message="yes";
        }
        else {
            message="no";
        }
        myUtils.printMsg(request,response,message);
    }

    @RequestMapping("/check")
    public void checkTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("in checkTest()!");
        String sessionid = request.getParameter("sessionid");
        String href = request.getParameter("href");
        System.out.println("href:"+href);

        HttpSession session = (HttpSession)config.sessionmap.get(sessionid);
        String message=null;
        if (session==null){
            message="error";
        }
        else {              //判断权限，根据前端URL进行路由控制
            User user=(User) session.getAttribute("userinfo");
            System.out.println("当前用户为:"+user.getUsername()+"用户角色为："+user.getRole());
            if (href.indexOf("userinfo")>0 && user.getRole()!=0 && user.getRole()!=1 ){      //仅用户、管理员具有查看用户信息的权限
                System.out.println("当前用户没有userinfo权限");
                message="no";
            }
            else if (href.indexOf("personalOrder")>0 && user.getRole()!=0 && user.getRole()!=1 ){      //仅用户、管理员具有查看用户订单的权限
                System.out.println("当前用户没有personalOrder权限");
                message="no";
            }
            else if (href.indexOf("categorysManagement")>0 && user.getRole()!=0 && user.getRole()!=2 ){      //仅销售商、管理员具有商品类别管理的权限
                System.out.println("当前用户没有categorysManagement权限");
                message="no";
            }
            else if (href.indexOf("productsManagement")>0 && user.getRole()!=0 && user.getRole()!=2 ){      //仅销售商、管理员具有产品信息管理的权限
                System.out.println("当前用户没有productsManagement权限");
                message="no";
            }
            else if (href.indexOf("ordersManagement")>0 && user.getRole()!=0 && user.getRole()!=2 ){      //仅销售商、管理员具有订单管理的权限
                System.out.println("当前用户没有ordersManagement权限");
                message="no";
            }
            else if (href.indexOf("usersManagement")>0 && user.getRole()!=0){      //仅管理员具有用户管理的权限
                System.out.println("当前用户没有usersManagement权限");
                message="no";
            }
            else if (href.indexOf("levelManagement")>0 && user.getRole()!=0){      //仅管理员具有等级管理的权限
                System.out.println("当前用户没有levelManagement权限");
                message="no";
            }
            else if (href.indexOf("authorizationManagement")>0 && user.getRole()!=0){      //仅管理员具有权限管理的权限
                System.out.println("当前用户没有authorizationManagement权限");
                message="no";
            }
            else {          //判断登录情况（是否为异地登录）
                String userip = (String) config.userip.get(user.getId());   //取出原始IP
                String remoteaddress = request.getRemoteAddr();             //客户端IP
                System.out.println("原始userip:" + userip + " 客户端remoteaddress:" + remoteaddress);
                if (userip.equals(remoteaddress)) {      //IP相同说明是本人访问
                    message = "success";
                } else {          //不同则意味着已经在其他地方登录
                    message = "bad";
                }
                if (!message.equals("success")){
                    session.invalidate();
                }
            }
        }
        myUtils.printMsg(request,response,message);

    }

}
