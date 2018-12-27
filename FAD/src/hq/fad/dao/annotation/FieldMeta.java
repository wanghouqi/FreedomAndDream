package hq.fad.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ ElementType.FIELD, ElementType.METHOD }) // 定义注解的作用目标**作用范围字段、枚举的常量/方法
@Documented // 说明该注解将被包含在javadoc中
public @interface FieldMeta {

	/**
	 * 是否为主键
	 * 
	 * @return
	 */
	boolean id() default false;

	/**
	 * 字段名称
	 * 
	 * @return
	 */
	String columnName() default "";

	/**
	 * 是否为父亲表,非父即子.
	 * 
	 * @return
	 */
	boolean isParent() default false;

	/**
	 * 当前属性如果是外键,这里可以记录外键信息
	 * 	用于关联查询子表,JSON形式
	 *   子表: "{className:'hq.fad.dao.pojo.SpittleFollow', columnName: 'CR_SPITTLE_ID', orderBy: {columnName:'CN_TIME', sort:'DESC'}}"
	 *   子表: "{className:'hq.fad.dao.pojo.SpittleFollow', columnName: 'CR_SPITTLE_ID', orderBy: [{columnName:'CN_TIME', sort:'DESC'},{columnName:'CR_PARENT_ID', sort:'ASC'}]}"
	 * @return
	 */
	String foreignKey() default "";

	/**
	 * 是否需要查询
	 * 
	 * @return
	 */
	boolean needQuery() default true;

	/**
	 * 是否需要保存.
	 * 
	 * @return
	 */
	boolean needSave() default true;

	/**
	 * 字段描述
	 * 
	 * @return
	 */
	String description() default "";

	/**
	 * 排序字段
	 * 
	 * @return
	 */
	int order() default 0;

}