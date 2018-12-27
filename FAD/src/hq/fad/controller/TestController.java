/**
 * 
 */
package hq.fad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hq.fad.dao.BaseDAO;

/**
 * 用于测试的Consoller
 * @author Administrator
 *
 */
@Controller // 声明当前为Controller
@RequestMapping(value = { "/test" })
public class TestController {
	private BaseDAO baseDao;

	@Autowired
	public TestController(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	/*
	 * 测试JDBC是否连接成功
	 */
	@RequestMapping(value = { "/JDBC" }, method = RequestMethod.GET)
	public String testJDBC() {
		this.baseDao.test();
		return "home"; // 返回逻辑视图名称"home"
	}
}
