package hq.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import hq.fad.dao.annotation.FieldMeta;
import hq.fad.dao.pojo.BasePOJO;
import hq.fad.dao.pojo.User;
import hq.fad.dao.pojo.UserProfile;

public class TestDaoPOJOProxy {
	public static void main(String[] aa) {
		User user = User.getInsertInstance(User.class);
		user.setId("aaaa");
		user.setLoginName("dddd");
		printFiled(user);
		System.out.println("userLonginName  = " + user.getLoginName());
		try {
//			Field[] fields = user.getClass().getSuperclass().getDeclaredFields();
//			for (Field f : fields) { // 获取字段中包含fieldMeta的注解 FieldMeta meta =
//				System.out.println(f.getName());
//			}
			Field f = user.getClass().getSuperclass().getDeclaredField("userProfile");
			f.setAccessible(true);//允许访问私有字段 
			Object o = f.get(user);
			if (o == null) {
				System.out.println("in");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void printFiled(User base) {
		Field[] fields = base.getClass().getSuperclass().getDeclaredFields();
		//
		for (Field f : fields) {
			// 获取字段中包含fieldMeta的注解
			FieldMeta meta = f.getAnnotation(FieldMeta.class);
			if (meta != null) {
				SortableField l = new SortableField(meta, f);
				System.out.println("字段名称：" + l.getName() + "\t字段类型：" + l.getType() + "\t注解名称：" + l.getMeta().columnName() + "\t注解描述：" + l.getMeta().description());
			}
		}
	}

	public static void changeName(BasePOJO base, String value) {
		try {
			// 获取Bar的val字段
			Field field = base.getClass().getDeclaredField("name");
			// 获取val字段上的Foo注解实例
			FieldMeta foo = field.getAnnotation(FieldMeta.class);
			// 获取 foo 这个代理实例所持有的 InvocationHandler
			InvocationHandler h = Proxy.getInvocationHandler(foo);
			// 获取 AnnotationInvocationHandler 的 memberValues 字段
			Field hField = h.getClass().getDeclaredField("memberValues");
			// 因为这个字段事 private final 修饰，所以要打开权限
			hField.setAccessible(true);
			// 获取 memberValues
			Map memberValues = (Map) hField.get(h);
			// 修改 value 属性值
			memberValues.put("name", value);
			// // 获取 foo 的 value 属性值
			// String value = foo.value();
			// System.out.println(value); // ddd
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
