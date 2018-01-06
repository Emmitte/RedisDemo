package redis.test;

import redis.clients.jedis.JedisCluster;
import redis.dao.RedisOps;
import redis.util.RedisUtil;


public class test {

	public static void main(String[] args) {
		JedisCluster client = RedisUtil.getInstance();
		RedisOps ops = new RedisOps(client);
		
		//测试redis对字符串的操作
		//ops.stringOps();
		
		//测试redis对list的操作
		//ops.listOps();
		
		//测试redis对set的操作
		//ops.setOps();

		//测试redis对map的操作
		//ops.mapOps();
	}

}
