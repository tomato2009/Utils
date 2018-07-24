package com.tomatoman.utils;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * IP Int 之间相互转换
 * 
 * 通常情况下，服务端需要做ip白名单，那就需要一个需要把白名单放到内存性质的缓存中，比如memcache
 * redis等，如果直接保存字符串，一个ip大概需要15个字节（255.255.255.255），IP的没个段很明显符合一个byte
 * 的长度，可以考虑通过byte[] 最终转化为int放到内存中，节省内存
 * 
 * @author tomatoman
 *
 */
public class IP2Int2IP {

	public static void main(String[] args) {
		try {
			System.out.println(ipToInt("255.255.255.255"));
			System.out.println(intToIp(-1));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
     * 将ip转为int
     * @param ip
     * @return int可能为负数
     * @throws UnknownHostException
     */
    public static int ipToInt(String ip) throws UnknownHostException {
        byte[] addr = ipToBytes(ip);
        //reference  java.net.Inet4Address.Inet4Address
        int address  = addr[3] & 0xFF;
        address |= ((addr[2] << 8) & 0xFF00);
        address |= ((addr[1] << 16) & 0xFF0000);
        address |= ((addr[0] << 24) & 0xFF000000);
        return address;
    }
    
    /**
     * 将ip转为int
     * @param ip
     * @return xxx.xxx.xxx.xxx
     */
    public static String intToIp(int ip) {
        byte[] addr = new byte[4];
        addr[0] = (byte) ((ip >>> 24) & 0xFF);
        addr[1] = (byte) ((ip >>> 16) & 0xFF);
        addr[2] = (byte) ((ip >>> 8) & 0xFF);
        addr[3] = (byte) (ip & 0xFF);
        return bytesToIp(addr);
    }
	
	/**
     * 将ip字符串转为byte数组,注意:ip不可以是域名,否则会进行域名解析
     * @param ip
     * @return byte[]
     * @throws UnknownHostException
     */
    public static byte[] ipToBytes(String ip) throws UnknownHostException {
        return Inet4Address.getByName(ip).getAddress();
    }
    
    /**
     * 将byte数组转为ip字符串
     * @param src
     * @return xxx.xxx.xxx.xxx
     */
    public static String bytesToIp(byte[] src) {
        return (src[0] & 0xff) + "." + (src[1] & 0xff) + "." + (src[2] & 0xff)
                + "." + (src[3] & 0xff);
    }

}
