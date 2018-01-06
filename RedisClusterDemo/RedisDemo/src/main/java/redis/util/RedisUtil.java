package redis.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	
	//连接池配置
	private static JedisPoolConfig poolConfig = null;
	//连接池对象
	private static JedisCluster cluster = null;
	//最大连接数
	private static int MAX_TOTAL = 10;
	//最大空闲数
	private static int MAX_IDLE = 10;
	//最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常
	private static long MAX_WAITMILLIS = 5000;
	
	/**
	 * 获取Jedis实例
	 * */
	public synchronized static JedisCluster getInstance() {
		try {
			if(cluster == null){
				try {
					poolConfig = new JedisPoolConfig();
					//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		            poolConfig.setBlockWhenExhausted(true);
					poolConfig.setMaxTotal(MAX_TOTAL);
					poolConfig.setMaxIdle(MAX_IDLE);
					poolConfig.setMaxWaitMillis(MAX_WAITMILLIS);
					String path = RedisUtil.class.getResource("/").getPath() + "hosts";
					Set<HostAndPort> nodes = readHostsFromFile(path); //从文件中读取hosts信息
					/*
					nodes.add(new HostAndPort("192.168.52.130", 7000));
					nodes.add(new HostAndPort("192.168.52.130", 7001));
					nodes.add(new HostAndPort("192.168.52.130", 7002));
					nodes.add(new HostAndPort("192.168.52.131", 7003));
					nodes.add(new HostAndPort("192.168.52.131", 7004));
					nodes.add(new HostAndPort("192.168.52.131", 7005));
					*/
					//cluster = new JedisCluster(nodes, poolConfig);
					// 节点，超时时间，最多重定向次数，链接池
					cluster = new JedisCluster(nodes, 200, 10, poolConfig);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return cluster;
			}else {
				return cluster;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void close() {
		try {
			if (cluster != null) {
				cluster.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Set<HostAndPort> readHostsFromFile(String path) throws NumberFormatException, IOException {
		Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
		
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String line=null;
		
		while((line = br.readLine()) != null){
			String[] arr = line.split("\t");
			nodes.add(new HostAndPort(arr[0], Integer.parseInt(arr[1])));
		}
		
		br.close();
		fr.close();
		
		return nodes;
	}
}
