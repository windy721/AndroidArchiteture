package com.jim.androidarchiteture.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	public static byte[] zip(byte[] data) throws IOException {
		return zip(data,5,1);
	}

	public static byte[] zip(byte[] data,int level,int pass) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream os = new ZipOutputStream(bos);
		os.setLevel(level);
		ZipEntry ze;
		try {
			for (int i = 0; i < pass; i++) {
				bos.reset();
				ze = new ZipEntry("zip"+i);
				os.flush();
				os.putNextEntry(ze);
				os.write(data);
				os.closeEntry();
				os.finish();
			}
			return bos.toByteArray();
		} finally{
			os.close();
			bos.close();
		}
	}
	
	public static byte[] ungzip(byte[] data) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		java.util.zip.ZipInputStream is = new java.util.zip.ZipInputStream(bis);
		ByteArrayOutputStream resultData = new ByteArrayOutputStream();
		try{
			is.getNextEntry();
			byte[] buffer = new byte[4096]; 
			int read;
			while((read = is.read(buffer))>0){
				resultData.write(buffer, 0, read);
			}
			return resultData.toByteArray();
		}finally{
			is.close();
			bis.close();
			resultData.close();
		}
	}
	
	public static byte[] unzip(byte[] data) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		java.util.zip.ZipInputStream is = new java.util.zip.ZipInputStream(bis);
		ByteArrayOutputStream resultData = new ByteArrayOutputStream();
		try{
			is.getNextEntry();
			byte[] buffer = new byte[4096]; 
			int read;
			while((read = is.read(buffer))>0){
				resultData.write(buffer, 0, read);
			}
			return resultData.toByteArray();
		}finally{
			is.close();
			bis.close();
			resultData.close();
		}
	}
}
