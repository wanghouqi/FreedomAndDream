package hq.fad.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ ElementType.TYPE }) // 定义注解的作用目标**作用范围字段、枚举的常量/方法
@Documented // 说明该注解将被包含在javadoc中
public @interface ClassMeta {

	/**
	 * 数据库表名称
	 * 
	 * @return
	 */
	String tablename() default "";

	/**
	 * 数据库子表
	 * 	用于删除时删除管理子表,JSON形式
	 *  单一子表: "{className:'hq.fad.dao.pojo.SpittleFollow', columnName: 'CR_SPITTLE_ID'}"
	 *  多子表: "[{className:'hq.fad.dao.pojo.SpittleFollow', columnName: 'CR_SPITTLE_ID'}]"
	 * @return
	 */
	String deleteSubTable() default "";

}