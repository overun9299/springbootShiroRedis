package overun.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import overun.Md5Utils;
import overun.shiro.ShiroService;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * https://blog.csdn.net/qq_20954959?t=1
 * Created by ZhangPY on 2019/3/31
 * Belong Organization OVERUN-9299
 * overun9299@163.com
 */
@Controller
public class LoginController {

    @Autowired
    private ShiroService shiroService;

    /**
     * 跳转到登录表单页面
     * @return
     */
    @RequestMapping(value="login")
    public String login() {
        return "login";
    }

    /**
     * ajax登录请求
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value="ajaxLogin",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> submitLogin(String username, String password, Model model) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {

            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(token);
            resultMap.put("status", 200);
            resultMap.put("message", "登录成功");

        } catch (AuthenticationException a) {
            resultMap.put("status", 500);
            resultMap.put("message", "账号密码不匹配");
        } catch (Exception e) {
            resultMap.put("status", 500);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    /**
     * 跳转到主页
     * @return
     */
    @RequestMapping(value="index")
    public String index() {
        return "index";
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value="logout",method =RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> logout(){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            //退出
            SecurityUtils.getSubject().logout();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping(value="add")
    public String add() {
        return "add";
    }

    /**
     * 更新权限
     */
    @RequestMapping(value="updateshiro")
    @ResponseBody
    public void updateShiro() {
        shiroService.updatePermission();
    }


    /**
     * 获取加密后密码
     */
    @RequestMapping(value = "md5")
    @ResponseBody
    public void getMd5(String s, String p) {
        Md5Utils.getMd5(s,p);
    }
}
