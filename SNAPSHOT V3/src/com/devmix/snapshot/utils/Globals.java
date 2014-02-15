package com.devmix.snapshot.utils;

public class Globals {
	
	public static final int TIME_OUT = 7000;
	public static final String serverIp = "192.168.1.33:8080";
	//public static final String serverIp = "192.168.0.101:8080";
	public static final String rootUrlEnterpriseWS = String.format("http://%s/SNAPSHOT_WS/rest/enterprise",serverIp);
	public static final String rootUrlEstadoWS = String.format("http://%s/SNAPSHOT_WS/rest/estado",serverIp);
	public static final String rootUrlCidadeWS = String.format("http://%s/SNAPSHOT_WS/rest/cidade",serverIp);
	public static final String rootUrlProfileWS = String.format("http://%s/SNAPSHOT_WS/rest/profile",serverIp);
	public static final String rootUrlConfiguracoesWS = String.format("http://%s/SNAPSHOT_WS/rest/configuracoes",serverIp);
	

}
