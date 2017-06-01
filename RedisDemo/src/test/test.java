package test;

import dao.RedisOps;
import util.RedisUtil;

public class test {

	public static void main(String[] args) {
		RedisOps ops = new RedisOps();
		ops.setup();
		
		/*
		//测试redis对字符串的操作
		//ops.stringOps();
		String key = "01";
		String value = "aaa";
		ops.setString(key, value);
		System.out.println(ops.getString(key));
		*/
		
		//测试redis对map的操作
		//ops.mapOps();
		
		/*
		//测试redis对list的操作
		//ops.listOps();
		String key = "country";
		ops.setListK_V(key, "China");
		ops.setListK_V(key, "America");
		System.out.println(ops.getListK_V(key));
		*/
		
		/*
		//测试redis对set的操作
		//ops.setOps();
		String key = "names";
		ops.setSetK_V(key, "Anna");
		System.out.println(ops.getSetK_V(key));
		*/
		
		/*
		//测试RedisPool
		RedisUtil.getJedis().set("key", "中文测试");
		System.out.println(RedisUtil.getJedis().get("key"));
		*/
	}

}
