package com.wjs.study.memSize;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 获取对象占用内存的大小
 * 
 * 使用方法：
 * 1. 将工程导出为jar包，
 * 2. 修改jar包中的MANIFEST.MF 文件，增加：
 * 		Can-Redefine-Classes: true
		Can-Retransform-Classes: true
		Premain-Class: com.wjs.study.memSize.ObjectSize
		Main-Class: com.wjs.study.memSize.ObjectSize
		
 * 3. 进入jar文件目录，执行jar命令
 * 	    java -javaagent:size.jar -jar size.jar
 * 
 * 4. 参考网址
		http://ju.outofmemory.cn/entry/29680
		http://www.ibm.com/developerworks/cn/java/j-lo-instrumentation/
		http://www.csdn.net/article/2013-05-22/2815377-developing-a-jvm-agent
		http://www.tuicool.com/articles/vYrARj
		http://www.cnblogs.com/adolfmc/archive/2012/10/07/2713562.html
 * 
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
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
        for(int i=0 ;i < 100000; i++){
        	map.put(UUID.randomUUID().toString(), "0.25"+String.valueOf(i));
        }
        System.out.println(JavaSizeOf.sizeof(map));
	}
}
