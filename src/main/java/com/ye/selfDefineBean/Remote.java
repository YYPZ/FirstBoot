package com.ye.selfDefineBean;

public class Remote {
	public String ip;
	public String port;

    public Remote() {
        
    }
    
    public void printInfo() {
    	  System.out.println("服务信息 ip:"+ip+" 端口"+port);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
    
    
}