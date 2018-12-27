package hq.fad.controller.back;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import hq.fad.dao.BaseDAO;
import hq.fad.dao.pojo.BasePOJO;
import hq.fad.dao.pojo.FBoolean;
import hq.fad.dao.pojo.User;
import hq.fad.dao.pojo.UserC;
import hq.fad.dao.pojo.UserProfile;
import hq.fad.utils.FadHelper;
import hq.fad.utils.condition.CondSetBean;
import hq.fad.utils.orderby.Sort;

/**
 * 创建后台的用户管理的控制器
 * @author Administrator
 *
 */

@Controller // 声明当前为Controller
@RequestMapping(value = "/back") // 制定基础URL
public class BackUserController {
	private BaseDAO baseDao;

	@Autowired
	public BackUserController(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 管理员列表页面
	 * @return
	 */
	@RequestMapping(value = "/userCList", method = RequestMethod.GET)
	public ModelAndView userCList(HttpServletRequest request) {
		// 读取系统中所有活动的管理员
		ArrayList<UserC> alUserC = this.baseDao.query(UserC.class, 2, new Sort("CN_NO"));
		request.setAttribute("alUserC", alUserC);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("back.userC.list");
		return mv;
	}

	/**
	 * 管理员维护页面
	 * @return
	 */
	@RequestMapping(value = "/userCModify/{cUserId}", method = RequestMethod.GET)
	public ModelAndView userCModify(@PathVariable String cUserId, HttpServletRequest request) {
		// 读取系统中所有活动的管理员
		UserC userC = this.baseDao.queryObject(UserC.class, 2, cUserId);
		ArrayList<FBoolean> alFBoolean = this.baseDao.query(FBoolean.class, new Sort("CN_NO"));
		request.setAttribute("alFBoolean", alFBoolean);
		request.setAttribute("userC", userC);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("back.userC.modify");
		return mv;
	}

	/**
	 * 保存管理员数据
	 * @return
	 */
	@RequestMapping(value = "/userCSave", method = RequestMethod.POST)
	public String userCSave(HttpServletRequest request) {
		String primaryId = request.getParameter("id");
		String no = request.getParameter("no");
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String supperFlag = request.getParameter("supperFlag");
		String activeFlag = request.getParameter("activeFlag");
		password = FadHelper.encryptPassword(password);

		UserC userC;
		if (StringUtils.isBlank(primaryId)) {
			/*
			 * 创建管理员的同时,给其创建一个同名的管理普通用户.
			 */
			UserProfile userProfile = BasePOJO.getInsertInstance(UserProfile.class);
			userProfile.setNickName(loginName);

			User user = BasePOJO.getInsertInstance(User.class);
			user.setLoginName(loginName);
			user.setUserProfile(userProfile);
			user.setPassword(password);
			user.setActiveFlag(new FBoolean(activeFlag));

			userC = BasePOJO.getInsertInstance(UserC.class);// 得到一个用户Insert的实例
			userC.setUser(user);
		} else {
			userC = BasePOJO.getUpdateInstance(UserC.class, primaryId);// 得到一个用于Update的实例
		}
		userC.setNo(no);
		userC.setLoginName(loginName);
		userC.setPassword(password);
		userC.setName(name);
		userC.setSupperFlag(new FBoolean(supperFlag));
		userC.setActiveFlag(new FBoolean(activeFlag));
		// 保存数据库
		this.baseDao.save(userC);
		return "redirect:/back/userCList";
	}

	/**
	 * 删除管理员数据
	 * @return
	 */
	@RequestMapping(value = "/userCDelete", method = RequestMethod.POST)
	public String userCDelete(@RequestParam("primaryId") String[] primaryIds) {
		this.baseDao.deleteByIds(UserC.class, primaryIds);
		return "redirect:/back/userCList";
	}

	/**
	 * 保存管理员数据
	 * @return
	 */
	@RequestMapping(value = "/ajax/getUserByLoginName", method = RequestMethod.POST)
	public @ResponseBody List<User> getUserByLoginName(HttpServletRequest request) {
		String valueInput = request.getParameter("valueInput");
		List<User> alUser = new ArrayList<User>();
		if (StringUtils.isNotBlank(valueInput)) {
			CondSetBean csbUser = new CondSetBean();
			csbUser.addCondBean_equal("CN_LOGIN_NAME", valueInput);
			alUser = this.baseDao.query(User.class, csbUser, new Sort("CN_LOGIN_NAME"));
		}
		return alUser;
	}

}
