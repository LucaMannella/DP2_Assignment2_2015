package it.polito.dp2.WF.sol2.util;

/**
 * This is an Utility class for automatic Log with tag of this format: ClassName separator MethodName 
 * <p>
 * Usage: 
 * <p>
 * 
 * <code>
 * Log log =  new Log(this);
 * <p>
 * log.print("Hello, I am your Log System");
 * </code>
 * <p>
 * Customization of separator string:
 *  <code>
 *  <p>
 * Log log =  new Log(this,"::");
 * <p>
 * log.print("Hello, I am your Log System");
 * </code>
 * @author Luca
 *
 */
public class Log {
	private final String defaultTag = "default Tag";
	private String ClassName;
	private String MethodName;
	private String separator = ".";
	String Message = "This is a test message";
	
	public String getSeparator() {
		return separator;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getClassName() {
		return ClassName;
	}
	
	public String getClassSmallName() {
		String[] clsName = ClassName.split("\\.");
		return clsName[clsName.length-1];
	}
	
	public void setClassName(String className) {
		ClassName = className;
	}
	
	public String getMethodName() {
		return MethodName;
	}
	
	public void setMethodName(String methodName) {
		MethodName = methodName;
	}
	
	public String getMessage() {
		return Message;
	}
	
	public void setMessage(String message) {
		Message = message;
	}
		
	public Log(Object cls) {
		super();
		if(cls != null)
		{
			setClassName(cls.getClass().getSimpleName());
			searchMethodName();
		}		
	}
		
	public Log(Object cls,String separator) {
		super();
		if(cls != null)
		{
			setClassName(cls.getClass().getName());
			searchMethodName();
			setSeparator(separator);
		}		
	}
		
	/**
	 * @param cls the current class --> you obtain it by: 'this'
	 */
	public void setClass(Object cls)
	{
		setClassName(cls.getClass().toString());		
	}
		
	public void print(String msg) {		
		if(msg!=null)
		{
			setMessage(msg);
			String tag = defaultTag;
			if(getMethodName()!=null)
			{
				tag = getClassSmallName()+separator+getMethodName();			
			}
			else if(getClassName()!=null)
			{
				tag = getClassName();
			}		
			//Logger.i(tag, msg);
		}		
	}
		
	private void searchMethodName()    
	{    
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();    
	    if(sts == null)    
	    {    
	    	return;    
	    }    
	    for(StackTraceElement st : sts)    
	    {       
	    	if(st.getClassName().equals(getClassName()))    
	        {    
	          	setMethodName(st.getMethodName());  
	            break;    
	        }      
	    }    
	 }    
		
}