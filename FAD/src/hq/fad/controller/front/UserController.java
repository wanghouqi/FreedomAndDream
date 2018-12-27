package hq.fad.controller.front;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import hq.fad.dao.BaseDAO;
import hq.fad.dao.pojo.BasePOJO;
import hq.fad.dao.pojo.FBoolean;
import hq.fad.dao.pojo.Spittle;
import hq.fad.dao.pojo.SpittleType;
import hq.fad.dao.pojo.User;
import hq.fad.dao.pojo.UserC;
import hq.fad.dao.pojo.UserProfile;
import hq.fad.utils.Definition;
import hq.fad.utils.FadHelper;
import hq.fad.utils.condition.CondSetBean;
import hq.fad.utils.orderby.Sort;

/**
 * 用户管理的控制器
 * @author Administrator
 *
 */

@Controller // 声明当前为Controller
@RequestMapping(value = "/u") // 制定基础URL
public class UserController {
	private BaseDAO baseDao;

	@Autowired
	public UserController(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView userRegister() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("front.user.register");
		return mv;
	}

	/**
	 * 注册保存
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String userRegisterSave(HttpServletRequest request) {
		String loginName = request.getParameter("c");
		String email = request.getParameter("d");
		String mobileNo = request.getParameter("e");
		String password = request.getParameter("a");
		password = FadHelper.encryptPassword(password);

		/*
		 * 构建一个用户基础信息.昵称等于登录名
		 */
		UserProfile userProfile = BasePOJO.getInsertInstance(UserProfile.class);
		userProfile.setNickName(loginName);

		/*
		 * 创建一个用户
		 */
		User user = BasePOJO.getInsertInstance(User.class);
		user.setActiveFlag(new FBoolean(FBoolean.YES));
		user.setEmail(email);
		user.setLoginName(loginName);
		user.setMobileNo(mobileNo);
		user.setPassword(password);
		user.setUserProfile(userProfile);
		this.baseDao.save(user);
		request.getSession().setAttribute(Definition.SESSION_ATTR_KEY_USER, user);
		return "redirect:/";
	}

	/**
	 * 用户的基础信息页面
	 * @return
	 */
	@RequestMapping(value = "/base", method = RequestMethod.GET)
	public ModelAndView userBase() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("front.user.base");
		return mv;
	}

	/**
	 * 用户的个人信息页面
	 * @return
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView userProfile() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("front.user.profile");
		return mv;
	}

	/**
	 * 用户的文章列表页面
	 * @return
	 */
	@RequestMapping(value = "/spittleList", method = RequestMethod.GET)
	public ModelAndView userSpittleList(HttpServletRequest request) {
		String pageIndex = request.getParameter("pageIndex");
		if (StringUtils.isEmpty(pageIndex)) {
			pageIndex = "0";
		}
		User user = (User) request.getSession().getAttribute(Definition.SESSION_ATTR_KEY_USER);
		// 读取当前用户的文章
		JSONObject splitPageSet = new JSONObject();
		splitPageSet.put("pageIndex", Integer.parseInt(pageIndex));
		splitPageSet.put("onePageNumber", 10);

		CondSetBean csbSpittle = new CondSetBean();
		csbSpittle.addCondBean_equal("CR_USER_ID", user.getId());
		csbSpittle.addCondBean_and_equal("CR_ACTIVE_FLAG", FBoolean.YES);
		ArrayList<Spittle> alSpittle = this.baseDao.query(Spittle.class, 2, csbSpittle, new Sort("CN_TIME", Sort.DESC), splitPageSet);
		request.setAttribute("alSpittle", alSpittle);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("front.user.spittleList");
		return mv;
	}

	/**
	 * 文章维护页面
	 * @return
	 */
	@RequestMapping(value = "/spittleModify/{spittleId}", method = RequestMethod.GET)
	public ModelAndView spittleModify(@PathVariable String spittleId, HttpServletRequest request) {
		// 读取系统中所有活动的管理员
		Spittle spittle = this.baseDao.queryObject(Spittle.class, 2, spittleId);
		ArrayList<SpittleType> alSpittleType = this.baseDao.query(SpittleType.class, new Sort("CN_NO"));
		request.setAttribute("spittle", spittle);
		request.setAttribute("alSpittleType", alSpittleType);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("front.user.spittleModify");
		return mv;
	}

	/**
	 * 保存文章数据
	 * @return
	 */
	@RequestMapping(value = "/spittleSave", method = RequestMethod.POST)
	public String spittleSave(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Definition.SESSION_ATTR_KEY_USER);
		String primaryId = request.getParameter("id");
		String spittleTypeId = request.getParameter("spittleTypeId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		Spittle spittle;
		if (StringUtils.isBlank(primaryId)) {
			spittle = BasePOJO.getInsertInstance(Spittle.class);// 得到一个用户Insert的实例
		} else {
			spittle = BasePOJO.getUpdateInstance(Spittle.class, primaryId);// 得到一个用于Update的实例
		}
		spittle.setActiveFlag(new FBoolean(FBoolean.YES));
		spittle.setAdmireCount(0L);
		spittle.setAuthorizedFlag(new FBoolean(FBoolean.NO));
		spittle.setContent(content);
		spittle.setSpittleType(new SpittleType(spittleTypeId));
		spittle.setTime(System.currentTimeMillis());
		spittle.setTitle(title);
		spittle.setUser(new User(user.getId()));
		spittle.setViewCount(0L);
		// 保存数据库
		this.baseDao.save(spittle);
		return "redirect:/u/spittleList";
	}

}
