package hq.test;

import java.lang.reflect.Field;

import hq.fad.dao.pojo.BasePOJO;
import hq.fad.dao.pojo.User;
import hq.fad.dao.pojo.UserC;

public class TestField {

	public static void main(String[] aa) {
		try {
			Class<?> clazz = UserC.class;
			for (Field field : clazz.getDeclaredFields()) {
				Class<?> secondClass = field.getType();
				if (BasePOJO.class.isAssignableFrom(secondClass)) {
					System.out.println("getType() == AbstractBasePOJO");
				} else if (field.getType() == User.class) {
					System.out.println("getType() == User");
				} else {
					System.out.println("Type == " + field.getType().getName());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
