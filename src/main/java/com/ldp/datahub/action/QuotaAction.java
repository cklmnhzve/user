package com.ldp.datahub.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.entity.QuotaVo;
import com.ldp.datahub.entity.RepoVo;
import com.ldp.datahub.service.QuotaService;
import com.ldp.datahub.service.UserService;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
@Controller
public class QuotaAction extends BaseAction{

	@Autowired
	private QuotaService quotaService;
	@Autowired
	private UserService userService;
	
	private static Log log = LogFactory.getLog(QuotaAction.class);
	
	@RequestMapping(value = "/users/{loginName:.*}/repository", method = RequestMethod.GET)
	public void getRepo(@PathVariable String loginName,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			
			int userId = userService.getUserId(loginName);
			RepoVo vo = quotaService.getRepos(userId);
			
			if(vo==null){
				log.error(loginName+" repo配额信息 不存在");
				jsonMap.put(Constant.result_code, Constant.no_quota_code);
				jsonMap.put(Constant.result_msg, Constant.no_quota);
				return;
			}
			
			log.info("获取 "+loginName+" repo配额信息");
			jsonMap.put(Constant.result_code, Constant.sucess_code);
			jsonMap.put(Constant.result_msg, Constant.sucess);
			jsonMap.put(Constant.result_data, vo);
			
		} catch (Exception e) {
			log.error("获取repo配额失败 "+e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	
	@RequestMapping(value = "/users/{loginName:.*}/repository", method = RequestMethod.POST)
	public void addRepo(@PathVariable String loginName,@RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String me = request.getHeader("USER");
			
			boolean able = isAdmin(me, jsonMap);
			if(!able){
				return;
			}
			
			JSONObject requestJson = JSONObject.fromObject(body);
			String privateRepo = requestJson.getString("private");
			String publicRepo = requestJson.getString("public");
			
			
			int opUser = userService.getUserId(me);
			int userId = userService.getUserId(loginName);
			
			boolean s = quotaService.saveRepo(userId, opUser, Integer.parseInt(privateRepo.toString()), Integer.parseInt(publicRepo.toString()));
			
			if(s){
				jsonMap.put(Constant.result_code, Constant.sucess_code);
				jsonMap.put(Constant.result_msg, Constant.sucess);
				log.info(me+" 添加  "+loginName+" 的repo配额 成功");
			}else{
				log.error(me+" 添加  "+loginName+" 的repo配额 失败，配额已存在");
				jsonMap.put(Constant.result_code, Constant.exist_quota_code);
				jsonMap.put(Constant.result_msg, Constant.exist_quota);
			}
			
		}catch(JSONException je){
			log.error("保存repo配额失败，请求参数错误");
			jsonMap.put(Constant.result_code, Constant.param_err_code);
			jsonMap.put(Constant.result_msg, Constant.param_err);
		} 
		catch (Exception e) {
			log.error("保存repo配额失败，"+e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	
	@RequestMapping(value = "/users/{loginName:.*}/repository/quota", method = RequestMethod.PUT)
	public void updateRepoQuota(@PathVariable String loginName,@RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String me = request.getHeader("USER");
			boolean admin=isAdmin(me, jsonMap);
			if(!admin){
				return;
			}
			
			JSONObject requestJson = JSONObject.fromObject(body);
			Object privateRepo = requestJson.get("private");
			Object publicRepo = requestJson.get("public");
			
			if(privateRepo==null&&publicRepo==null){
				jsonMap.put(Constant.result_code, Constant.param_err_code);
				jsonMap.put(Constant.result_msg, Constant.param_err);
				return;
			}
			
			int opUser = userService.getUserId(me);
			int userId = userService.getUserId(loginName);
			Integer repo1 = null;
			Integer repo2 = null;
			if(privateRepo!=null){
				repo1 = Integer.parseInt(privateRepo.toString());
			}
			if(publicRepo!=null){
				repo2 = Integer.parseInt(publicRepo.toString());
			}
			quotaService.updateRepo(userId, opUser, repo1, repo2);
			
			jsonMap.put(Constant.result_code, Constant.sucess_code);
			jsonMap.put(Constant.result_msg, Constant.sucess);
			log.info(me+" 更新  "+loginName+" 的repo配额 成功");
			
			
		}catch (Exception e){
			log.error("更新repo配额失败，"+e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	@RequestMapping(value = "/users/{loginName:.*}/repository/use", method = RequestMethod.POST)
	public void updateRepoUse(@PathVariable String loginName,@RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String me = request.getHeader("USER");
		try {
			if(!isAdminOrI(me, loginName, jsonMap)){
				return;
			}
			JSONObject requestJson = JSONObject.fromObject(body);
			Object privateRepo = requestJson.get("private");
			Object publicRepo = requestJson.get("public");
			
			if(privateRepo==null&&publicRepo==null){
				jsonMap.put(Constant.result_code, Constant.param_err_code);
				jsonMap.put(Constant.result_msg, Constant.param_err);
				return;
			}
			
			
			int userId = userService.getUserId(loginName);
			Integer repo1 = null;
			Integer repo2 = null;
			if(privateRepo!=null){
				repo1 = Integer.parseInt(privateRepo.toString());
			}
			if(publicRepo!=null){
				repo2 = Integer.parseInt(publicRepo.toString());
			}
			
			quotaService.updateRepoUse(userId, repo1, repo2);
			
			jsonMap.put(Constant.result_code, Constant.sucess_code);
			jsonMap.put(Constant.result_msg, Constant.sucess);
			log.info(me+" 更新   "+loginName+" 的repo使用量 成功");
			
		} catch (Exception e) {
			log.error("更新repo使用量失败，"+e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	
	
	
	@RequestMapping(value = "/users/{loginName:.*}/deposit", method = RequestMethod.GET)
	public void getDeposit(@PathVariable String loginName,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			
			int userId = userService.getUserId(loginName);

			QuotaVo vo = quotaService.getQuota(userId, Constant.QutaName.DEPOSIT);
			
			if(vo==null){
				log.error(loginName+" 托管配额信息 不存在");
				jsonMap.put(Constant.result_code, Constant.no_quota_code);
				jsonMap.put(Constant.result_msg, Constant.no_quota);
				return;
			}
			log.info("获取 "+loginName+" 托管配额信息");
			jsonMap.put(Constant.result_code, Constant.sucess_code);
			jsonMap.put(Constant.result_msg, Constant.sucess);
			jsonMap.put(Constant.result_data, vo);
		
			
		} catch (Exception e) {
			log.error("获取托管配额失败 "+e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	
	@RequestMapping(value = "/users/{loginName:.*}/deposit", method = RequestMethod.POST)
	public void addDeposit(@PathVariable String loginName,@RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String me = request.getHeader("USER");
			
			boolean able = isAdmin(me, jsonMap);
			if(!able){
				return;
			}
			
			JSONObject requestJson = JSONObject.fromObject(body);
			String quota = requestJson.getString("quota");
			Object ounit = requestJson.get("unit");
			
			String unit="";
			if(ounit!=null){
				unit = ounit.toString();
			}
			
			int opUser = userService.getUserId(me);
			int userId = userService.getUserId(loginName);
			
			boolean s = quotaService.saveQuota(userId, opUser, Integer.parseInt(quota),Constant.QutaName.DEPOSIT, unit);
			if(s){
				jsonMap.put(Constant.result_code, Constant.sucess_code);
				jsonMap.put(Constant.result_msg, Constant.sucess);
				log.info(me+" 添加  "+loginName+" 的托管配额 成功");
			}else{
				log.error(me+" 添加  "+loginName+" 的托管配额 失败，配额已存在");
				jsonMap.put(Constant.result_code, Constant.exist_quota_code);
				jsonMap.put(Constant.result_msg, Constant.exist_quota);
			}
			
		}catch(JSONException je){
			log.error("保存托管配额失败，请求参数错误");
			jsonMap.put(Constant.result_code, Constant.param_err_code);
			jsonMap.put(Constant.result_msg, Constant.param_err);
		}
		catch (Exception e) {
			log.error("保存托管配额失败，"+e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	
	@RequestMapping(value = "/users/{loginName:.*}/deposit/quota", method = RequestMethod.PUT)
	public void updateDepositQuota(@PathVariable String loginName,@RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String me = request.getHeader("USER");
			boolean admin=isAdmin(me, jsonMap);
			if(!admin){
				return;
			}
			
			JSONObject requestJson = JSONObject.fromObject(body);
			String quota = requestJson.getString("quota");
			
			int opUser = userService.getUserId(me);
			int userId = userService.getUserId(loginName);
			quotaService.updateQuota(userId, opUser, Integer.parseInt(quota), Constant.QutaName.DEPOSIT);;
			
			jsonMap.put(Constant.result_code, Constant.sucess_code);
			jsonMap.put(Constant.result_msg, Constant.sucess);
			log.info(me+" 更新  "+loginName+" 的托管配额 成功");
			
			
		}catch(JSONException je){
			log.error("更新托管配额失败，请求参数错误");
			jsonMap.put(Constant.result_code, Constant.param_err_code);
			jsonMap.put(Constant.result_msg, Constant.param_err);
		}
		catch (Exception e){
			log.error("更新托管配额失败，"+e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	
	@RequestMapping(value = "/users/{loginName:.*}/pullnum", method = RequestMethod.GET)
	public void getPull(@PathVariable String loginName,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			
			int userId = userService.getUserId(loginName);

			QuotaVo vo = quotaService.getQuota(userId, Constant.QutaName.PULL_NUM);
			
			if(vo==null){
				log.error(loginName+" 下载量配额信息 不存在");
				jsonMap.put(Constant.result_code, Constant.no_quota_code);
				jsonMap.put(Constant.result_msg, Constant.no_quota);
				return;
			}
			log.info("获取 "+loginName+" 下载量配额信息");
			jsonMap.put(Constant.result_code, Constant.sucess_code);
			jsonMap.put(Constant.result_msg, Constant.sucess);
			jsonMap.put(Constant.result_data, vo);
		
		} catch (Exception e) {
			log.error("获取下载量配额失败 "+e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	
	@RequestMapping(value = "/users/{loginName:.*}/pullnum", method = RequestMethod.POST)
	public void addPull(@PathVariable String loginName,@RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String me = request.getHeader("USER");
			
			boolean able = isAdmin(me, jsonMap);
			if(!able){
				return;
			}
			
			JSONObject requestJson = JSONObject.fromObject(body);
			String quota = requestJson.getString("quota");
			
			int opUser = userService.getUserId(me);
			int userId = userService.getUserId(loginName);
			
			boolean s = quotaService.saveQuota(userId, opUser, Integer.parseInt(quota),Constant.QutaName.PULL_NUM, "");
			if(s){
				jsonMap.put(Constant.result_code, Constant.sucess_code);
				jsonMap.put(Constant.result_msg, Constant.sucess);
				log.info(me+" 添加  "+loginName+" 的下载量配额 成功");
			}else{
				log.error(me+" 添加  "+loginName+" 的下载量配额 失败，配额已存在");
				jsonMap.put(Constant.result_code, Constant.exist_quota_code);
				jsonMap.put(Constant.result_msg, Constant.exist_quota);
			}
			
		}catch(JSONException je){
			log.error("保存下载量配额失败，请求参数错误");
			jsonMap.put(Constant.result_code, Constant.param_err_code);
			jsonMap.put(Constant.result_msg, Constant.param_err);
		} 
		catch (Exception e) {
			log.error("保存下载量配额失败，"+e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	
	@RequestMapping(value = "/users/{loginName:.*}/pullnum/quota", method = RequestMethod.PUT)
	public void updatePullQuota(@PathVariable String loginName,@RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String me = request.getHeader("USER");
			boolean admin=isAdmin(me, jsonMap);
			if(!admin){
				return;
			}
			
			JSONObject requestJson = JSONObject.fromObject(body);
			String quota = requestJson.getString("quota");
			
			int opUser = userService.getUserId(me);
			int userId = userService.getUserId(loginName);
			quotaService.updateQuota(userId, opUser, Integer.parseInt(quota), Constant.QutaName.PULL_NUM);;
			
			jsonMap.put(Constant.result_code, Constant.sucess_code);
			jsonMap.put(Constant.result_msg, Constant.sucess);
			log.info(me+" 更新  "+loginName+" 的下载量配额 成功");
			
			
		}catch(JSONException je){
			log.error("更新下载量配额失败，请求参数错误");
			jsonMap.put(Constant.result_code, Constant.param_err_code);
			jsonMap.put(Constant.result_msg, Constant.param_err);
		}
		catch (Exception e){
			log.error("更新下载量配额失败，"+e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	
	@RequestMapping(value = "/users/{loginName:.*}/pullnum/use", method = RequestMethod.POST)
	public void updatePullUse(@PathVariable String loginName,@RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String me = request.getHeader("USER");
		try {
			if(!isAdminOrI(me, loginName, jsonMap)){
				return;
			}
			JSONObject requestJson = JSONObject.fromObject(body);
			String use = requestJson.getString("use");
			
			int userId = userService.getUserId(loginName);
			
			quotaService.updateQuotaUse(userId, Integer.parseInt(use), Constant.QutaName.PULL_NUM);
			
			jsonMap.put(Constant.result_code, Constant.sucess_code);
			jsonMap.put(Constant.result_msg, Constant.sucess);
			log.info(me+" 更新   "+loginName+" 的下载使用量 成功");
			
		}catch(JSONException je){
			log.error("更新下载使用量失败，请求参数错误");
			jsonMap.put(Constant.result_code, Constant.param_err_code);
			jsonMap.put(Constant.result_msg, Constant.param_err);
		} 
		catch (Exception e) {
			log.error("更新下载使用量失败，"+e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	
		
}
