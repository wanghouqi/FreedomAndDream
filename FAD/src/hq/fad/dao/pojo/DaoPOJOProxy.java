package hq.fad.dao.pojo;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import hq.fad.utils.FadHelper;

public class DaoPOJOProxy implements MethodInterceptor {

	// 相当于JDK动态代理中的绑定
	public BasePOJO getInstance(Class<?> cls) {
		Enhancer enhancer = new Enhancer(); // 创建加强器，用来创建动态代理类
		enhancer.setSuperclass(cls); // 为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
		// 设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
		enhancer.setCallback(this);
		// 创建动态代理类对象并返回
		return (BasePOJO) enhancer.create();
	}

	// 实现回调方法
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object retVal = proxy.invokeSuper(obj, args); // 调用业务类（父类中）的方法
		setModifyFlag((BasePOJO) obj, method);
		return retVal;
	}

	public void setModifyFlag(BasePOJO obj, Method method) {
		String methodName = method.getName();
		if (methodName.startsWith("set")) {
			String fieldName = FadHelper.toLowerCaseFirstOne(methodName.replace("set", ""));
			obj.addModifyFieldName(fieldName);
		}
	}
}