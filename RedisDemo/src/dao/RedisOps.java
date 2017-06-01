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
	 * 建立连接
	 * */
	public void setup() {
		//连接redis服务器，127.0.0.1:6379
		String host = "127.0.0.1";
		int port = 6379;
		jedis = new Jedis(host, port);
		//权限认证
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
	 * redis存储字符串
	 * */
	public void stringOps() {
		//-----添加数据----------
		String key = "key";
		String value = "value";
		jedis.set(key, value); //向key-->"key"中放入了value-->"value"
		System.out.println(jedis.get(key));
		
		jedis.append(key, "01"); //拼接
		System.out.println(jedis.get(key));
		
		jedis.del(key); //删除某个键
		System.out.println(jedis.get(key));
		
		//设置多个键值对
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
	 * redis操作Map
	 * */
	public void mapOps() {
		//-----添加数据----------
		Map<String, String> map = new HashMap<>();
		map.put("name", "Emmitte");
		map.put("age", "25");
		map.put("gender", "male");
		jedis.hmset("user", map);
		//取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		//第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数  
		
		List<String> rsmap = jedis.hmget("user", "name","age","gender");
		System.out.println(rsmap);
		
		//删除map中的某个键值  
		jedis.hdel("user", "age");
		System.out.println(jedis.hmget("user", "age")); //因为删除了，所以返回的是null
		System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数2
		System.out.println(jedis.exists("user")); //是否存在key为user的记录 返回true
		System.out.println(jedis.hkeys("user")); //返回map对象中的所有key
		System.out.println(jedis.hvals("user")); //返回map对象中的所有value
		
		iter = jedis.hkeys("user").iterator();
		String key;
		while(iter.hasNext()){
			key = iter.next();
			System.out.println(key + " -> " + jedis.hmget("user", key));
		}
	}
	
	/**
	 * jedis操作List
	 * */
	public void listOps() {
		//-----添加数据----------  
		jedis.lpush("city", "beijing");
		jedis.lpush("city", "shanghai");
		jedis.lpush("city", "guangdong");
		jedis.lpush("city", "shenzhen");
		//再取出所有数据jedis.lrange是按范围取出,第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
		System.out.println(jedis.lrange("city", 0, -1));
	}
	
	public void setListK_V(String key,String value) {
		jedis.lpush(key, value);
	}
	
	public List<String> getListK_V(String key) {
		return jedis.lrange(key, 0, -1);
	}
	
	/**
	 * jedis操作Set 
	 * */
	public void setOps() {
		//-----添加数据----------  
		jedis.sadd("names", "Emmitte");
		jedis.sadd("names", "John");
		jedis.sadd("names", "Mate");
		jedis.sadd("names", "name");
		System.out.println(jedis.smembers("names")); //获取所有加入的value
		System.out.println(jedis.sismember("names", "Emmitte")); //判断 Emmitte是否是user集合的元素
		System.out.println(jedis.scard("names")); //返回集合的元素个数
		System.out.println(jedis.srandmember("names"));
		//移除name
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
