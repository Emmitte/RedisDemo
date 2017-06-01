package util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	
	//Redis������IP
	private static String HOST = "127.0.0.1";
	
	//Redis�Ķ˿ں�
	private static int PORT = 6379;
	
	//��������
	//private static String AUTH = "root";
	
	//��������ʵ���������Ŀ��Ĭ��ֵΪ8��
	//�����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)��
	private static int MAX_ACTIVE = 1024;
	
	//����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����Ĭ��ֵҲ��8��
	private static int MAX_IDLE = 200;
	
	//�ȴ��������ӵ����ʱ�䣬��λ���룬Ĭ��ֵΪ-1����ʾ������ʱ����������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException��
	private static int MAX_WAIT = 10000;
	private static int TIMEOUT = 10000;
	
	//��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�
	private static boolean TEST_ON_BORROW = true;
	private static JedisPool jedisPoll = null;
	
	/**
	 * ��ʼ��Redis���ӳ�
	 * */
	static{
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxActive(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWait(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPoll = new JedisPool(config, HOST, PORT, TIMEOUT);
			//jedisPoll = new JedisPool(config, HOST, PORT, TIMEOUT, AUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡJedisʵ��
	 * */
	public synchronized static Jedis getJedis() {
		try {
			if(jedisPoll != null){
				Jedis recourse = jedisPoll.getResource();
				return recourse;
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * �ͷ�jedis��Դ
	 * */
	public static void releaseRecourse(final Jedis jedis) {
		if(jedis != null){
			jedisPoll.returnResource(jedis);
		}
	}
}
