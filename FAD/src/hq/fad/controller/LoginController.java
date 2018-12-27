package hq.fad.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hq.fad.dao.BaseDAO;
import hq.fad.dao.pojo.FBoolean;
import hq.fad.dao.pojo.User;
import hq.fad.dao.pojo.UserC;
import hq.fad.utils.Definition;
import hq.fad.utils.FadHelper;
import hq.fad.utils.condition.CondSetBean;

/**
 * 用户登录控制器,包括前台和后台
 * @author Administrator
 *
 */
@Controller // 声明当前为Controller
public class LoginController {
	private BaseDAO baseDao;

	@Autowired
	public LoginController(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 后台的主页(登录页面)
	 * @return
	 */
	@RequestMapping(value = "/backCLogin", method = RequestMethod.GET)
	public String backCLogin() {
		return "back/cLogin"; // 返回逻辑视图名称"back/cLogin"
	}

	/**
	 * 后台的登录相应时间
	 * @return
	 */
	@RequestMapping(value = "/backCLogin", method = RequestMethod.POST)
	public String cLogin(@RequestParam("u") String userName, @RequestParam("p") String passwordString, HttpServletRequest request) {
		String redirect = "redirect:";
		CondSetBean csbUserC = new CondSetBean();
		csbUserC.addCondBean_equal("CN_LOGIN_NAME", userName);
		csbUserC.addCondBean_and_equal("CR_ACTIVE_FLAG", FBoolean.YES);
		UserC cUser = this.baseDao.queryObject(UserC.class, csbUserC);
		if (cUser != null && FBoolean.YES.equals(cUser.getActiveFlag().getId()) && FadHelper.encryptPassword(passwordString).contentEquals(cUser.getPassword())) {
			User user = cUser.getUser();
			request.getSession().setAttribute(Definition.SESSION_ATTR_KEY_USER, user);
			redirect += "/back/home";// 进入后台主页
		} else {
			redirect += "/backCLogin";// 返回登录页面
			request.getSession().setAttribute(Definition.SESSION_ATTR_KEY_ALERT_MSG, "用户名或密码错误!");
		}
		return redirect;
	}

	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping(value = "/u/login", method = RequestMethod.GET)
	public ModelAndView userlogin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("front.user.login");
		return mv;
	}

	/**
	 * 前台的登录相应时间
	 * @return
	 */
	@RequestMapping(value = "/u/login", method = RequestMethod.POST)
	public String userloginDo(@RequestParam("c") String userName, @RequestParam("a") String passwordString, HttpServletRequest request) {
		String redirect = "redirect:";
		CondSetBean csbUser = new CondSetBean();
		csbUser.addCondBean_equal("CN_LOGIN_NAME", userName);
		csbUser.addCondBean_or_equal("CN_EMAIL", userName);
		csbUser.addCondBean_or_equal("CN_MOBILE_NO", userName);
		User user = this.baseDao.queryObject(User.class, 2, csbUser);
		if (user != null && FBoolean.YES.equals(user.getActiveFlag().getId()) && FadHelper.encryptPassword(passwordString).contentEquals(user.getPassword())) {
			request.getSession().setAttribute(Definition.SESSION_ATTR_KEY_USER, user);
			redirect += "/";// 进入主页
		} else {
			redirect += "/u/login";// 返回登录页面
			request.getSession().setAttribute(Definition.SESSION_ATTR_KEY_ALERT_MSG, "用户名或密码错误!");
		}
		return redirect;
	}

	/**
	 * 前台的登录相应时间
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String userloginDo(HttpServletRequest request) {
		request.getSession().removeAttribute(Definition.SESSION_ATTR_KEY_USER);
		return "redirect:/";
	}
}
