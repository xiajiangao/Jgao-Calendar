//package top.jgao.controller;
//
//import top.jgao.annotation.MyLogger;
//import top.jgao.basic.Result;
//import top.jgao.persistence.request.PageParams;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("user")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @MyLogger("获取用户列表")
//    @RequestMapping("count")
//    public Result getUserList(PageParams pageParams) {
//        return userService.getUserList(pageParams);
//    }
//}
