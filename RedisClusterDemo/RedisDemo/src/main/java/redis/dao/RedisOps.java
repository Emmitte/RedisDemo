package redis.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.JedisCluster;

public class RedisOps {
	
	private JedisCluster client;
	
	private Iterator iter;
	
	public RedisOps(JedisCluster client){
		this.client = client;
	}
	
	/**
	 * redis存储字符串
	 * */
	public void stringOps() {
		//-----添加数据----------
		String key = "2";
		String value = "bb";
		client.set(key, value);
		System.out.println(client.get(key));
		
		client.append(key, "cc");
		System.out.println(client.get(key));
		
		client.del(key);
		System.out.println(client.get(key));
		
		for(int i = 0;i < 3;i++){
			client.set(String.valueOf(i), "test"+i);
			System.out.println(client.get(String.valueOf(i)));
		}
	}
	
	/**
	 * redis操作Map
	 * */
	public void mapOps() {
		//-----添加数据----------
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "Emmitte");
		map.put("age", "25");
		map.put("gender", "male");
		client.hmset("user", map);
		//取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		//第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数  
		
		List<String> rsmap = client.hmget("user", "name","age","gender");
		System.out.println(rsmap);
		
		//删除map中的某个键值  
		client.hdel("user", "age");
		System.out.println(client.hmget("user", "age")); //因为删除了，所以返回的是null
		System.out.println(client.hlen("user")); //返回key为user的键中存放的值的个数2
		System.out.println(client.exists("user")); //是否存在key为user的记录 返回true
		System.out.println(client.hkeys("user")); //返回map对象中的所有key
		System.out.println(client.hvals("user")); //返回map对象中的所有value
		
		iter = client.hkeys("user").iterator();
		String key;
		while(iter.hasNext()){
			key = (String) iter.next();
			System.out.println(key + " -> " + client.hmget("user", key));
		}
	}
	
	/**
	 * jedis操作List
	 * */
	public void listOps() {
		//-----添加数据----------  
		client.lpush("city", "beijing");
		client.lpush("city", "shanghai");
		client.lpush("city", "guangdong");
		client.lpush("city", "shenzhen");
		//再取出所有数据jedis.lrange是按范围取出,第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
		System.out.println(client.lrange("city", 0, -1));
	}
	
	/**
	 * jedis操作Set 
	 * */
	public void setOps() {
		//-----添加数据----------  
		client.sadd("names", "Emmitte");
		client.sadd("names", "John");
		client.sadd("names", "Mate");
		client.sadd("names", "name");
		System.out.println(client.smembers("names")); //获取所有加入的value
		System.out.println(client.sismember("names", "Emmitte")); //判断 Emmitte是否是user集合的元素
		System.out.println(client.scard("names")); //返回集合的元素个数
		System.out.println(client.srandmember("names"));
		//移除name
		client.srem("names", "name");
		System.out.println(client.smembers("names"));
	}

}
