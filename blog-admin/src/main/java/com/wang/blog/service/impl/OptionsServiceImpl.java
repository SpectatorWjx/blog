package com.wang.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wang.blog.vo.OptionVO;
import com.wang.blog.repository.OptionsRepository;
import com.wang.blog.service.OptionsService;
import com.wang.common.entity.blog.OptionsEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wjx
 */
@Service
public class OptionsServiceImpl implements OptionsService {
	@Autowired
	private OptionsRepository optionsRepository;
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public List<OptionsEntity> findAll() {
		List<OptionsEntity> list = optionsRepository.findAll();
		List<OptionsEntity> rets = new ArrayList<>();
		
		for (OptionsEntity po : list) {
			OptionsEntity r = new OptionsEntity();
			BeanUtils.copyProperties(po, r);
			rets.add(r);
		}
		return rets;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void update(Map<String, String> options) {
		if (options == null) {
			return;
		}

		options.forEach((key, value) -> {
			OptionsEntity entity = optionsRepository.findByKey(key);
			String val = StringUtils.trim(value);
			if (entity != null) {
				entity.setValue(val);
			} else {
				entity = new OptionsEntity();
				entity.setKey(key);
				entity.setValue(val);
			}
			optionsRepository.save(entity);
		});
	}

	@Override
	public OptionVO findOption() {
		List<OptionsEntity> list = optionsRepository.findAll();
		Map map = new HashMap();
		list.forEach(l -> {
			map.put(l.getKey(), l.getValue());
		});
		OptionVO optionVO = JSONObject.parseObject(JSON.toJSONString(map), OptionVO.class);
		return optionVO;
	}

	@Override
	public void reloadCache() {
		doPostOrGet("https://delpast.com/api/reset/cache");
	}

	/**
	 * 以post或get方式调用对方接口方法，
	 * @param pathUrl
	 */
	public static void doPostOrGet(String pathUrl){
		OutputStreamWriter out = null;
		BufferedReader br = null;
		OutputStream outputStream = null;
		String result = "";
		try {
			URL url = new URL(pathUrl);
			//打开和url之间的连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//请求方式
			conn.setRequestMethod("POST");
			//conn.setRequestMethod("GET");

			//设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

			//DoOutput设置是否向httpUrlConnection输出，DoInput设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
			conn.setDoOutput(true);
			conn.setDoInput(true);

			/**
			 * 下面的三句代码，就是调用第三方http接口
			 */
			//获取URLConnection对象对应的输出流
			outputStream = conn.getOutputStream();
			out = new OutputStreamWriter(outputStream, "UTF-8");
			//flush输出流的缓冲
			out.flush();

			/**
			 * 下面的代码相当于，获取调用第三方http接口后返回的结果
			 */
			//获取URLConnection对象对应的输入流
			InputStream is = conn.getInputStream();
			//构造一个字符流缓存
			br = new BufferedReader(new InputStreamReader(is));
			String str = "";
			while ((str = br.readLine()) != null){
				result += str;
			}
			System.out.println(result);
			//关闭流
			is.close();
			//断开连接，disconnect是在底层tcp socket链接空闲时才切断，如果正在被其他线程使用就不切断。
			conn.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (out != null){
					out.close();
				}
				if (outputStream != null){
					outputStream.close();
				}
				if (br != null){
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void initSettings(Resource resource) {
		Session session = entityManager.unwrap(Session.class);
		session.doWork(connection -> ScriptUtils.executeSqlScript(connection, resource));
	}
}
