package test;

import dao.RedisOps;
import util.RedisUtil;

public class test {

	public static void main(String[] args) {
		RedisOps ops = new RedisOps();
		ops.setup();
		
		/*
		//����redis���ַ����Ĳ���
		//ops.stringOps();
		String key = "01";
		String value = "aaa";
		ops.setString(key, value);
		System.out.println(ops.getString(key));
		*/
		
		//����redis��map�Ĳ���
		//ops.mapOps();
		
		/*
		//����redis��list�Ĳ���
		//ops.listOps();
		String key = "country";
		ops.setListK_V(key, "China");
		ops.setListK_V(key, "America");
		System.out.println(ops.getListK_V(key));
		*/
		
		/*
		//����redis��set�Ĳ���
		//ops.setOps();
		String key = "names";
		ops.setSetK_V(key, "Anna");
		System.out.println(ops.getSetK_V(key));
		*/
		
		/*
		//����RedisPool
		RedisUtil.getJedis().set("key", "���Ĳ���");
		System.out.println(RedisUtil.getJedis().get("key"));
		*/
	}

}
