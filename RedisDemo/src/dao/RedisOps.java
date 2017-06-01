package dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisOps {
	
	private Jedis jedis;
	
	private Iterator<String> iter;
	
	/**
	 * ��������
	 * */
	public void setup() {
		//����redis��������127.0.0.1:6379
		String host = "127.0.0.1";
		int port = 6379;
		jedis = new Jedis(host, port);
		//Ȩ����֤
		//String password = "root";
		//jedis.auth(password);
		if(jedis.ping().equals("PONG")){
			System.out.println("connected!");
		}else {
			System.out.println("not connected!");
			System.exit(0);
		}
	}
	
	/**
	 * redis�洢�ַ���
	 * */
	public void stringOps() {
		//-----�������----------
		String key = "key";
		String value = "value";
		jedis.set(key, value); //��key-->"key"�з�����value-->"value"
		System.out.println(jedis.get(key));
		
		jedis.append(key, "01"); //ƴ��
		System.out.println(jedis.get(key));
		
		jedis.del(key); //ɾ��ĳ����
		System.out.println(jedis.get(key));
		
		//���ö����ֵ��
		jedis.mset("key01","value01","key02","value02","key03","value03");
		Set<String> keys = jedis.keys("*");
		iter = keys.iterator();
		while(iter.hasNext()){
			key = iter.next();
			System.out.println(key + " -> " + jedis.get(key));
		}
	}
	
	public void setString(String key,String value) {
		jedis.set(key, value);
	}
	
	public String getString(String key) {
		return jedis.get(key);
	}
	
	/**
	 * redis����Map
	 * */
	public void mapOps() {
		//-----�������----------
		Map<String, String> map = new HashMap<>();
		map.put("name", "Emmitte");
		map.put("age", "25");
		map.put("gender", "male");
		jedis.hmset("user", map);
		//ȡ��user�е�name��ִ�н��:[minxr]-->ע������һ�����͵�List
		//��һ�������Ǵ���redis��map�����key����������Ƿ���map�еĶ����key�������key���Ը�������ǿɱ����  
		
		List<String> rsmap = jedis.hmget("user", "name","age","gender");
		System.out.println(rsmap);
		
		//ɾ��map�е�ĳ����ֵ  
		jedis.hdel("user", "age");
		System.out.println(jedis.hmget("user", "age")); //��Ϊɾ���ˣ����Է��ص���null
		System.out.println(jedis.hlen("user")); //����keyΪuser�ļ��д�ŵ�ֵ�ĸ���2
		System.out.println(jedis.exists("user")); //�Ƿ����keyΪuser�ļ�¼ ����true
		System.out.println(jedis.hkeys("user")); //����map�����е�����key
		System.out.println(jedis.hvals("user")); //����map�����е�����value
		
		iter = jedis.hkeys("user").iterator();
		String key;
		while(iter.hasNext()){
			key = iter.next();
			System.out.println(key + " -> " + jedis.hmget("user", key));
		}
	}
	
	/**
	 * jedis����List
	 * */
	public void listOps() {
		//-----�������----------  
		jedis.lpush("city", "beijing");
		jedis.lpush("city", "shanghai");
		jedis.lpush("city", "guangdong");
		jedis.lpush("city", "shenzhen");
		//��ȡ����������jedis.lrange�ǰ���Χȡ��,��һ����key���ڶ�������ʼλ�ã��������ǽ���λ�ã�jedis.llen��ȡ���� -1��ʾȡ������
		System.out.println(jedis.lrange("city", 0, -1));
	}
	
	public void setListK_V(String key,String value) {
		jedis.lpush(key, value);
	}
	
	public List<String> getListK_V(String key) {
		return jedis.lrange(key, 0, -1);
	}
	
	/**
	 * jedis����Set 
	 * */
	public void setOps() {
		//-----�������----------  
		jedis.sadd("names", "Emmitte");
		jedis.sadd("names", "John");
		jedis.sadd("names", "Mate");
		jedis.sadd("names", "name");
		System.out.println(jedis.smembers("names")); //��ȡ���м����value
		System.out.println(jedis.sismember("names", "Emmitte")); //�ж� Emmitte�Ƿ���user���ϵ�Ԫ��
		System.out.println(jedis.scard("names")); //���ؼ��ϵ�Ԫ�ظ���
		System.out.println(jedis.srandmember("names"));
		//�Ƴ�name
		jedis.srem("names", "name");
		System.out.println(jedis.smembers("names"));
	}
	
	public void setSetK_V(String key,String value) {
		jedis.sadd(key, value);
	}
	
	public Set<String> getSetK_V(String key) {
		return jedis.smembers(key);
	}

}
