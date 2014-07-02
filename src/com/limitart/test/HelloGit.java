package com.limitart.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * HelloWorld
 * 
 * @author hank
 */
public class HelloGit{

	public static void main(String[] args){
		//CopyAssets("D://win7我的文档-桌面-收藏夹//Desktop//新建文件夹", "g://haha");
		CopyAssets("./assets", "g://haha");
	}

	/**
	 * 复制源目录所有到目标目录
	 * 
	 * @param assetDir
	 * @param dir
	 */
	public static void CopyAssets(String assetDir ,String dir){
		File srcDir = new File(assetDir);
		if(!srcDir.exists()){
			System.out.println("要复制的目录不存在"+assetDir);
			return;
		}
		if(!srcDir.isDirectory()){
			System.out.println("源地址不是一个目录"+assetDir);
			return;
		}
		File[] files = srcDir.listFiles();
		for (File temp : files) {
			copyFolder(temp.getAbsolutePath(), dir);
		}
	}

	/**
	 * 复制文件到目标目录
	 * 
	 * @param srcFilePath
	 *            源文件地址
	 * @param destFilePath
	 *            目标目录地址
	 */
	public static void copyFolder(String srcFilePath ,String destFilePath){
		File oldFile = new File(srcFilePath);
		if (!oldFile.exists()) {// 文件都不存在,复制毛
			System.out.println("文件都不存在,复制毛");
			return;
		}
		File newFile = new File(destFilePath);
		if (!newFile.exists()) {// 文件不存在
			newFile.mkdir();
		}
		if (newFile.isFile()) {// 如果是文件，直接return掉
			System.out.println("目标地址是个文件大姐，不是文件夹");
			return;
		}
		if (oldFile.isDirectory()) {// 如果该文件是目录
			File[] listFiles = oldFile.listFiles();
			if (listFiles == null) {
				System.out.println("这个错误都出现了，说明GG了");
				return;
			}
			File dirFile = new File(destFilePath + File.separator + oldFile.getName());
			dirFile.mkdir();
			for(File temp:listFiles){
				copyFolder(temp.getAbsolutePath(), dirFile.getAbsolutePath());
			}
	
		} else {// 如果是文件夹
			if (!oldFile.canRead()) {
				System.out.println(oldFile + "没有读取权限!");
				return;
			}
			File destFile = new File(destFilePath + File.separator + oldFile.getName());
			try {
				destFile.createNewFile();
			} catch (IOException e) {
				System.out.println("建立新文件失败!");
				e.printStackTrace();
			}
			if (!destFile.canWrite()) {
				System.out.println(destFile + "没有写入权限!");
				return;
			}
			FileInputStream input = null;
			try {
				input = new FileInputStream(oldFile);
			} catch (FileNotFoundException e1) {
				System.out.println("文件输入流建立失败,没有找到文件" + oldFile);
				e1.printStackTrace();
			}
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(destFile);
			} catch (FileNotFoundException e) {
				System.out.println("文件输出流建立失败,没有找到文件" + destFile);
				e.printStackTrace();
			}
			System.out.println("开始复制文件" + oldFile + "->" + destFile);
			byte[] b = new byte[1024];
			int len;
			try {
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				output.flush();
				output.close();
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
