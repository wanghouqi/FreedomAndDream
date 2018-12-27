package hq.fad.controller.back;

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
import hq.fad.utils.Definition;
import hq.fad.utils.condition.CondSetBean;
import hq.fad.utils.orderby.OrderBy;
import hq.fad.utils.orderby.Sort;

/**
 * 后台的文章管理的控制器
 * @author Administrator
 *
 */

@Controller // 声明当前为Controller
@RequestMapping(value = "/back/spittle") // 制定基础URL
public class BackSpittleController {
	private BaseDAO baseDao;

	@Autowired
	public BackSpittleController(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 所有文章列表页面
	 * @return
	 */
	@RequestMapping(value = "/spittleList", method = RequestMethod.GET)
	public ModelAndView userSpittleList(HttpServletRequest request) {
		String pageIndex = request.getParameter("pageIndex");
		if (StringUtils.isEmpty(pageIndex)) {
			pageIndex = "0";
		}
		// 读取当前用户的文章
		JSONObject splitPageSet = new JSONObject();
		splitPageSet.put("pageIndex", Integer.parseInt(pageIndex));
		splitPageSet.put("onePageNumber", 10);

		OrderBy orderBy = new OrderBy();
		orderBy.addSort(new Sort("CR_AUTHORIZED_FLAG", Sort.DESC));
		orderBy.addSort(new Sort("CN_TIME", Sort.DESC));
		ArrayList<Spittle> alSpittle = this.baseDao.query(Spittle.class, 2, new CondSetBean(), orderBy, splitPageSet);
		request.setAttribute("alSpittle", alSpittle);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("back.spittle.spittleList");
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
		ArrayList<FBoolean> alFBoolean = this.baseDao.query(FBoolean.class, new Sort("CN_NO"));
		request.setAttribute("alFBoolean", alFBoolean);
		request.setAttribute("spittle", spittle);
		request.setAttribute("alSpittleType", alSpittleType);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("back.spittle.spittleModify");
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
		return "redirect:/back/spittle/spittleList";
	}

	/**
	 * 审核通过文章
	 * @return
	 */
	@RequestMapping(value = "/authorizedSpittle", method = RequestMethod.POST)
	public String authorizedSpittle(HttpServletRequest request) {
		String primaryId = request.getParameter("id");
		String authorizedFlag = request.getParameter("authorizedFlag");

		Spittle spittle = BasePOJO.getUpdateInstance(Spittle.class, primaryId);// 得到一个用于Update的实例
		spittle.setAuthorizedFlag(new FBoolean(authorizedFlag));

		// 保存数据库
		this.baseDao.save(spittle);
		return "redirect:/back/spittle/spittleList";
	}
}
