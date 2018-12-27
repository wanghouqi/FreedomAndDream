package hq.fad.controller.front;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import hq.fad.dao.BaseDAO;
import hq.fad.dao.pojo.BasePOJO;
import hq.fad.dao.pojo.FBoolean;
import hq.fad.dao.pojo.Spittle;
import hq.fad.dao.pojo.SpittleType;
import hq.fad.dao.pojo.User;
import hq.fad.utils.condition.CondSetBean;
import hq.fad.utils.orderby.Sort;

/**
 * 创建Homepage的控制器
 * @author Administrator
 *
 */
@Controller // 声明当前为Controller
@RequestMapping(value = "/") // 制定基础URL
public class HomeController {
	private BaseDAO baseDao;

	@Autowired
	public HomeController(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "forward:/spittle/t/new/0";
	}

}
