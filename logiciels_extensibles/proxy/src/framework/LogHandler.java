package framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LogHandler implements InvocationHandler{
	Object target;
	boolean active;
	
	public LogHandler(Object o) {
		target = o;
		active = false;
		
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Object[] convert(Object... args){
		 Object[] tab = new Object[args.length];
		int i = 0;
		for(Object arg : args){
			if(arg instanceof String){
				tab[i] = (Object) "TRUC MUCHE";
			}else{
				tab[i] = arg;
			}
			++i;
		}
		return tab;
	}
	
	
	
	public Object invoke(Object proxy, Method m, Object... args){
		
		Object ret = null;
		
		if(m.getName().equals("active")){
			active = !active;
			return ret;	
		}else{
			if(active){

				try {
					if(args!=null){
						Object[] objs = convert(args);
						ret = m.invoke(target, objs);
					}else{
						ret = m.invoke(target);
					}
					
					if(ret instanceof String) ret = "Chien !";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ret;
			}else{
				try {
					return m.invoke(target, args);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
		}
		return null;
	}
	
	

}
