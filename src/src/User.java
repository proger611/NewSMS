package src;

import java.net.InetAddress;

public class User {
	private String name = null;
	private String ip = null;

	User() throws Exception {
		InetAddress localhost = InetAddress.getLocalHost();
		this.setName(localhost.getHostName());
		this.setIp(localhost.getHostAddress());
		
	}

	public void setName(String s) {
		this.name = s;
	}

	public void setIp(String s) {
		this.ip = s;
	}

	public String getName() {
		return ip;
	}

	public String getIp() {
		return name;
	}

}
