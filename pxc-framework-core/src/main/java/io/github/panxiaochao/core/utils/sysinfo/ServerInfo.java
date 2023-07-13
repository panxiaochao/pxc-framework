package io.github.panxiaochao.core.utils.sysinfo;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * {@code ServerInfo}
 * <p>
 * ServerInfo Entity
 *
 * @author Lypxc
 * @since 2023-07-07
 */
@Getter
@Setter
public class ServerInfo {

	/**
	 * CPU 相关信息
	 */
	private Cpu cpu = new Cpu();

	/**
	 * 內存 相关信息
	 */
	private Mem mem = new Mem();

	/**
	 * JVM 相关信息
	 */
	private Jvm jvm = new Jvm();

	/**
	 * 服务器 相关信息
	 */
	private SysInfo sys = new SysInfo();

	/**
	 * 磁盘 相关信息
	 */
	private List<DiskInfo> diskInfos = new LinkedList<>();

}
