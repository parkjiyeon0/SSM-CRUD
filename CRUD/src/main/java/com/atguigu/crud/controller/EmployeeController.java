package com.atguigu.crud.controller;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date 2021/7/28 14:13
 * @Description  处理员工CRUD请求
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    /**
     * 员工删除
     * 单个批量二合一
     * 批量删除：1-2-3
     * 单个删除：1
     * @param ids
     */
    @RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteEmp(@PathVariable("ids") String ids) {
        //批量删除
        if (ids.contains("-")) {
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");

            for (String strId : str_ids) {
                del_ids.add(Integer.parseInt(strId));
            }
            //组装id的集合
            employeeService.deleteBatch(del_ids);
        } else {
            //单个删除
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }
        return Msg.success();
    }


    /**
     * 员工保存
     *
     * @param employee
     * @return 1、支持JSR303校验
     * 2、导入Hibernate-Validator
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            //校验失败，应该返回失败，在模态框显示校验失败的错误信息
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                System.out.println("错误的字段名：" + fieldError.getField());
                System.out.println("错误信息：" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        } else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }

    //根据id查询员工
    @ResponseBody
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    public Msg getEmp(@PathVariable("id")Integer id){
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }

    //检查用户名是否可用
    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkUser(@RequestParam("empName")String empName){
        //先判断用户名是否是合法的表达式，再判断是否重复
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if (!empName.matches(regx)) {
            return Msg.fail().add("va_msg", "用户名必须是6-16位数字，字母或者_-，也可以是2-5位中文组成");
        }
        //数据库用户名重复校验
        boolean b = employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg","用户名不可用");
        }
    }

    //员工查找  //现在只是实现通过名字查询，后面要实现自由查询
    @ResponseBody
    @RequestMapping("/empByName")
    public Msg searchEmps(String empName){
        List<Employee> searchList = employeeService.searchEmp(empName);
        return Msg.success().add("searchList", searchList);
    }

    /**
     * 如果直接发送ajax=PUT形式的请求
     * 数据的封装为Employee[empId=1011,empName=null,gender=null,email=null,dId=null]
     * 问题：请求体中有数据 ，employee封装不上
     *
     * 原因：tomcat将请求体中的数据，封装一个map，request.getParmeter("empName")就会从这个map中取值
     *      SpringMvc封装POJO对象的时候 会把POJO中每个属性的值 request.getParamter("email");
     *
     *  ajax发送PUT请求引发的血案
     *    PUT请求：请求体中的数据：request.getParamter("email")拿不到值
     *    tomcat一看是PUT请求就不会封装数据为map  只有POST请求 才封装为map
     *
     *   解决：web.xml中引入过滤器HttpPutFormContentFilter
     *
     *
     * 更新员工信息
     */
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg updateEmp(Employee employee) {
        employeeService.updateEmp(employee);
        return Msg.success();
    }


    //导入jackson包
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1") Integer pn) {
        //引入PageHelper分页插件
        //在查询之前只需调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 10);
        //startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
        //封装了详细的分页信息，包装有我们查询出来的数据，连续显示的页码
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", page);

    }

    //查询员工数据（分页）
//    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){
        //这不是一个分页查询
        //引入PageHelper分页插件
        //在查询之前只需调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        //startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
        //封装了详细的分页信息，包装有我们查询出来的数据，连续显示的页码
        PageInfo page = new PageInfo(emps, 5);
        model.addAttribute("pageInfo",page);

        return "list";
    }

}
