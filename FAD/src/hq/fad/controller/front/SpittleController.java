package hq.fad.controller.front;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import hq.fad.dao.BaseDAO;
import hq.fad.dao.pojo.BasePOJO;
import hq.fad.dao.pojo.FBoolean;
import hq.fad.dao.pojo.Spittle;
import hq.fad.dao.pojo.SpittleFollow;
import hq.fad.dao.pojo.SpittleType;
import hq.fad.dao.pojo.User;
import hq.fad.dao.pojo.UserAdmireSpittleLog;
import hq.fad.dao.pojo.UserProfile;
import hq.fad.dao.pojo.UserViewSpittleLog;
import hq.fad.utils.Definition;
import hq.fad.utils.FadHelper;
import hq.fad.utils.condition.CondSetBean;
import hq.fad.utils.orderby.Sort;

/**
 * 文章的控制器
 * @author Administrator
 *
 */

@Controller // 声明当前为Controller
@RequestMapping(value = "/spittle") // 制定基础URL
public class SpittleController {
	private BaseDAO baseDao;

	@Autowired
	public SpittleController(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	@RequestMapping(value = { "/t/{spittleTypeId}/{pageIndex}", "/t/{pageIndex}" }, method = RequestMethod.GET)
	public ModelAndView showSpittle(@PathVariable String spittleTypeId, @PathVariable int pageIndex, HttpServletRequest request) {
		ArrayList<SpittleType> alSpittleType = this.baseDao.query(SpittleType.class, new Sort("CN_NO"));
		boolean existType = false;
		for (SpittleType spittleType : alSpittleType) {
			if (spittleType.getId().contentEquals(spittleTypeId)) {
				existType = true;
				break;
			}
		}
		// 读取当前用户的文章
		JSONObject splitPageSet = new JSONObject();
		splitPageSet.put("pageIndex", pageIndex);
		splitPageSet.put("onePageNumber", 30);

		CondSetBean csbSpittle = new CondSetBean();
		csbSpittle.addCondBean_equal("CR_ACTIVE_FLAG", FBoolean.YES);
		csbSpittle.addCondBean_and_equal("CR_AUTHORIZED_FLAG", FBoolean.YES);
		if (existType) {
			csbSpittle.addCondBean_and_equal("CR_SPITTLE_TYPE_ID", spittleTypeId);
		} else {
			spittleTypeId = "";
		}
		ArrayList<Spittle> alSpittle = this.baseDao.query(Spittle.class, 2, csbSpittle, new Sort("CN_TIME", Sort.DESC), splitPageSet);

		request.setAttribute("alSpittle", alSpittle);
		request.setAttribute("alSpittleType", alSpittleType);
		request.setAttribute("spittleTypeId", spittleTypeId);
		request.setAttribute("existType", existType);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("front.home");
		return mv;
	}

	@RequestMapping(value = "/i/{spittleId}", method = RequestMethod.GET)
	public ModelAndView showSpittle(@PathVariable String spittleId, HttpServletRequest request) {
		// 读取文章
		Spittle spittle = this.baseDao.queryObject(Spittle.class, 2, spittleId);
		spittle.setViewCount(spittle.getViewCount() + 1);
		request.setAttribute("spittle", spittle);

		// 更新浏览次数
		Spittle spittleSave = BasePOJO.getUpdateInstance(Spittle.class, spittleId);
		spittleSave.setViewCount(spittle.getViewCount());
		this.baseDao.save(spittleSave);
		// 更新用户浏览信息
		User user = (User) request.getSession().getAttribute(Definition.SESSION_ATTR_KEY_USER);
		if (user != null) {
			UserViewSpittleLog userViewSpittleLogSave = BasePOJO.getInsertInstance(UserViewSpittleLog.class);
			userViewSpittleLogSave.setSpittle(new Spittle(spittle.getId()));
			userViewSpittleLogSave.setTime(System.currentTimeMillis());
			userViewSpittleLogSave.setUser(new User(user.getId()));
			this.baseDao.save(userViewSpittleLogSave);
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("front.home.spittle");
		return mv;
	}

	/**
	 * 文章点赞
	 * @return
	 */
	@RequestMapping(value = "/ajax/admireSpittle", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> admirepittle(HttpServletRequest request) {
		HashMap<String, String> returnHM = new HashMap<String, String>();
		String spittleId = request.getParameter("spittleId");
		long admireCount = 0;
		if (StringUtils.isNotBlank(spittleId)) {
			CondSetBean csbSpittle = new CondSetBean();
			csbSpittle.addCondBean_equal("CN_ID", spittleId);
			Spittle spittle = this.baseDao.queryObject(Spittle.class, csbSpittle);
			admireCount = spittle.getAdmireCount() + 1;
			// 更新点赞次数
			Spittle spittleSave = BasePOJO.getUpdateInstance(Spittle.class, spittleId);
			spittleSave.setAdmireCount(admireCount);
			this.baseDao.save(spittleSave);
			// 更新用户点赞信息
			User user = (User) request.getSession().getAttribute(Definition.SESSION_ATTR_KEY_USER);
			if (user != null) {
				UserAdmireSpittleLog userAdmireSpittleLogSave = BasePOJO.getInsertInstance(UserAdmireSpittleLog.class);
				userAdmireSpittleLogSave.setSpittle(new Spittle(spittle.getId()));
				userAdmireSpittleLogSave.setTime(System.currentTimeMillis());
				userAdmireSpittleLogSave.setUser(new User(user.getId()));
				this.baseDao.save(userAdmireSpittleLogSave);
			}
			// todo : 目前不对点赞做任何限制,如果需要,将来可以加入[同一用户只能点赞一次,一个IP只能点赞一次].
		}
		returnHM.put("admireCount", String.valueOf(admireCount));
		return returnHM;
	}

	/**
	 * 文章跟帖点赞
	 * @return
	 */
	@RequestMapping(value = "/ajax/admireSpittleFollow", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> admireSpittleFollow(HttpServletRequest request) {
		HashMap<String, String> returnHM = new HashMap<String, String>();
		String spittleFollowId = request.getParameter("spittleFollowId");
		long admireCount = 0;
		if (StringUtils.isNotBlank(spittleFollowId)) {
			CondSetBean csbSpittleFollow = new CondSetBean();
			csbSpittleFollow.addCondBean_equal("CN_ID", spittleFollowId);
			SpittleFollow spittleFollow = this.baseDao.queryObject(SpittleFollow.class, csbSpittleFollow);
			admireCount = spittleFollow.getAdmireCount() + 1;
			// 更新点赞次数
			SpittleFollow spittleFollowSave = BasePOJO.getUpdateInstance(SpittleFollow.class, spittleFollowId);
			spittleFollowSave.setAdmireCount(admireCount);
			this.baseDao.save(spittleFollowSave);

			// todo : 目前不对点赞做任何限制,如果需要,将来可以加入[同一用户只能点赞一次,一个IP只能点赞一次].
		}
		returnHM.put("admireCount", String.valueOf(admireCount));
		return returnHM;
	}

	/**
	 * 发表跟帖
	 * @return
	 */
	@RequestMapping(value = "/spittleFollowNew", method = RequestMethod.POST)
	public String spittleFollowNew(HttpServletRequest request) {
		String spittleId = request.getParameter("spittleId");
		String content = request.getParameter("content");
		// 更新用户点赞信息
		User user = (User) request.getSession().getAttribute(Definition.SESSION_ATTR_KEY_USER);
		if (user == null) {
			// todo: 这里下载是提示,之后真的页面有了之后根据实际页面修改操作.
			request.getSession().setAttribute(Definition.SESSION_ATTR_KEY_ALERT_MSG, "登录超时,请重新登录!!");
		} else {
			/*
			 * 创建一个新的跟帖
			 */
			SpittleFollow spittleFollow = BasePOJO.getInsertInstance(SpittleFollow.class);
			spittleFollow.setContent(content);
			spittleFollow.setSpittle(new Spittle(spittleId));
			spittleFollow.setTime(System.currentTimeMillis());
			spittleFollow.setUser(new User(user.getId()));
			this.baseDao.save(spittleFollow);
		}
		return "redirect:/spittle/i/" + spittleId;
	}
}
