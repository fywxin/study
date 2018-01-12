package com.wjs.study.memSize;

import java.io.Serializable;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.wjs.study.memSize.usapCacheSize.JvmCacheService;


/**
 * 获取对象占用内存的大小
 * 
 * 使用方法：
 * 1. 将工程导出为jar包，
 * 2. 修改jar包中的MANIFEST.MF 文件，增加： 【根据 MANIFEST.MF 规范，在这个文件的最后必须有两个空行，且每个 key 的冒号后面必须有一个空格，否则均认为是无效的 MANIFEST.MF 文件。】
 * 		Can-Redefine-Classes: true
		Can-Retransform-Classes: true
		Premain-Class: com.wjs.study.memSize.ObjectSize
		Main-Class: com.wjs.study.memSize.ObjectSize
		
 * 3. 进入jar文件目录，执行jar命令
 * 	    java -javaagent:study.jar -jar study.jar
 * 
 * 4. 参考网址
		http://ju.outofmemory.cn/entry/29680
		http://www.ibm.com/developerworks/cn/java/j-lo-instrumentation/
		http://www.csdn.net/article/2013-05-22/2815377-developing-a-jvm-agent
		http://www.tuicool.com/articles/vYrARj
		http://www.cnblogs.com/adolfmc/archive/2012/10/07/2713562.html
		http://bbs.csdn.net/topics/390654373
 * 
 * 
 * 命令运行发现错误
 * 		size.jar中没有主清单属性
 * 
 * MANIFEST.MF 写错
 * 			根据 MANIFEST.MF 规范，在这个文件的最后必须有两个空行，且每个 key 的冒号后面必须有一个空格，否则均认为是无效的 MANIFEST.MF 文件。
 * @author 王金绍
 * 2015年5月8日 下午12:16:03
 */
public class JavaSizeOf {
	private static Instrumentation inst ;

	public static void premain(String agentArgs, Instrumentation inst) {
		JavaSizeOf.inst = inst;
	}

	public static long sizeof(Object o) {
		assert inst != null;
		Map<Object, Object> visited = new IdentityHashMap<Object, Object>();
		Stack<Object> visiting = new Stack<Object>();
		visiting.add(o);
		long size = 0;
		while (!visiting.isEmpty()) {
			size += analysis(visiting, visited);
		}
		return size;
	}

	protected static long analysis(Stack<Object> visiting,
			Map<Object, Object> visited) {
		Object o = visiting.pop();
		if (skip(o, visited)) {
			return 0;
		}
		visited.put(o, null);
		// array.
		if (o.getClass().isArray()
				&& !o.getClass().getComponentType().isPrimitive()) {
			if (o.getClass().getName().length() != 2) {
				for (int i = 0; i < Array.getLength(o); i++) {
					visiting.add(Array.get(o, i));
				}
			}
		}
		// object.
		else {
			Class<?> clazz = o.getClass();
			while (clazz != null) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					if (Modifier.isStatic(field.getModifiers())) {
						continue;
					}
					if (field.getType().isPrimitive()) {
						continue;
					}
					field.setAccessible(true);
					try {
						visiting.add(field.get(o));
					} catch (Exception e) {
						assert false;
					}
				}
				clazz = clazz.getSuperclass();
			}
		}
		return inst.getObjectSize(o);
	}

	protected static boolean skip(Object o, Map<Object, Object> visited) {
		if (o instanceof String) {
			if (o == ((String) o).intern()) {
				return true;
			}
		}
		return o == null || visited.containsKey(o);
	}

	
	public static void main(String[] args) {
		System.out.println(JavaSizeOf.sizeof(new Long(1L)));
        System.out.println(JavaSizeOf.sizeof(new String("232323")));
        System.out.println(JavaSizeOf.sizeof(new Object()));
		ArrayBlockingQueue queue = new ArrayBlockingQueue(1024);

		for (int i=0; i<1024; i++){
			RpcLog rpcLog = new RpcLog();
			rpcLog.setTraceId(UUID.randomUUID().toString()+"@192.168.15.58@"+System.currentTimeMillis()+"@10000");
			rpcLog.setSpanId("0.02");
			rpcLog.setSrcAppId("dsfasdfadsfdssfa");
			rpcLog.setAimAppId("dsfasdfadsfdssfa");
			rpcLog.setAimService("com.onemt.cyous.sgfasd.sfgsd.dgersdfa");
			rpcLog.setAimMethod("sdfgasfgas");
			rpcLog.setAimVersion("0.0.0");
			rpcLog.setSrcAddress("128.159.348.45");
			rpcLog.setAimAddress("128.159.348.45");
			rpcLog.setReqLength(1552);
			rpcLog.setRespLength(155);
			rpcLog.setClientSide(false);
			rpcLog.setStartTime(System.currentTimeMillis());
			rpcLog.setEndTime(System.currentTimeMillis());
			rpcLog.setRs(1);
			queue.add(rpcLog);
		}
		System.out.println(JavaSizeOf.sizeof(queue)/1024);  //11M


        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
        for(int i=0 ;i < 100000; i++){
        	map.put(UUID.randomUUID().toString(), "0.25"+String.valueOf(i));
        }
        System.out.println(JavaSizeOf.sizeof(map)/1024);
        
        JvmCacheService jvmCacheService  = new JvmCacheService<Serializable>();
		
        
		for(int i=0 ;i < 100000; i++){
			jvmCacheService.put("2", UUID.randomUUID().toString(), "0.25"+String.valueOf(i), 1000);
        }
        System.out.println(JavaSizeOf.sizeof(jvmCacheService)/1024);  //11M
        
        ConcurrentHashMap<String, String> map2 = new ConcurrentHashMap<String, String>(1024);
        
        System.out.println(JavaSizeOf.sizeof(map2)/1024);  //11M
        
	}
}
