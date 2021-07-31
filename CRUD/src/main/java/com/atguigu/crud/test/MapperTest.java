package com.atguigu.crud.test;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.EmployeeExample;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.dao.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * @Author
 * @Date 2021/7/28 13:53
 * @Description 推荐Spring项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
 *  * 1、导入SpringTest模块
 *  * 2、@ContextConfiguration指定Spring配置文件的位置
 *  * 3、直接autowired要使用的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;
    //测试Department
    @Test
    public void testCRUD() {
//        //1、创建SpringIOC容器
//        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//        //2、从容器中获取mapper
//        DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);
        System.out.println(departmentMapper);

        departmentMapper.insertSelective(new Department(null,"设计部"));
    }

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void testSearch(){
        String empName = "Jerry";
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        List<Employee> employees = employeeMapper.selectByExampleWithDept(employeeExample);
        for (Employee employee : employees) {
            System.out.println(employee.getDepartment().getDeptName());
        }
    }
}
