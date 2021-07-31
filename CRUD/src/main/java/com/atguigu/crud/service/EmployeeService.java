package com.atguigu.crud.service;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.EmployeeExample;
import com.atguigu.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author
 * @Date 2021/7/28 14:50
 * @Description
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     *查询所有员工
     */
    public List<Employee> getAll() {

        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.setOrderByClause("emp_id");
        return employeeMapper.selectByExampleWithDept(employeeExample);
    }

    /**
     * 员工保存
     * @param employee
     */
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    /**
     * 员工查找（按照员工名字查）
     * @param empName
     */
    public List<Employee> searchEmp(String empName) {

        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        return employeeMapper.selectByExampleWithDept(employeeExample);
    }

    /**
     * 检验用户名是否可用
     * 如果等于0 返回true  就是可用
     * @param empName
     */
    public boolean checkUser(String empName) {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(employeeExample);
        return count == 0;
    }

    /**
     * 按照员工id查询
     * @param id
     */
    public Employee getEmp(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    /**
     * 更新员工信息
     * @param employee
     */
    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 员工删除
     * @param id
     */
    public void deleteEmp(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param ids
     */
    public void deleteBatch(List<Integer> ids) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //delete from xxx where emp_id in (1,2,3)
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }
}
