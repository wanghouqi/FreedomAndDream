package hq.fad.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hq.fad.dao.BaseDAO;

/**
 * 创建后台的主页的控制器
 * @author Administrator
 *
 */
@Controller // 声明当前为Controller
@RequestMapping(value = "/back") // 制定基础URL
public class BackHomeController {
	private BaseDAO baseDao;

	@Autowired
	public BackHomeController(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 后台的登录相应时间
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView cLogin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("back.home");
		return mv;
	}

}
