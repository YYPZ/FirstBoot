package com.ye.FirstBoot.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class CompressUtil {
	public static int BUFFER_SIZE = 2048;

	public static void main(String[] args) throws Exception {

		compressToZip("E:\\mydata", "E:\\mydata发送.zip");
		unZip("E:\\mydata发送.zip", "E:\\unzipT");
		// decompressZip2Files("E:\\mydata发送.zip", "E:\\unzipT");

	}

	public static void compressToZip(String sourceFilePath, String compressFile) throws Exception {

		ZipArchiveOutputStream zipOutput = null;

		try {
			File zipFile = new File(compressFile);
			zipOutput = (ZipArchiveOutputStream) new ArchiveStreamFactory().createArchiveOutputStream(
					ArchiveStreamFactory.ZIP, new BufferedOutputStream(new FileOutputStream(zipFile), BUFFER_SIZE));
			zipOutput.setEncoding("UTF-8");
			zipOutput.setUseZip64(Zip64Mode.AsNeeded);
			Map<String, File> fileMap = findFile(sourceFilePath);
			BufferedInputStream bufferedInputStream = null;
			File file;
			for (Map.Entry<String, File> mapEntry : fileMap.entrySet()) {
				try {
					file = mapEntry.getValue();
					ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, mapEntry.getKey());
					zipArchiveEntry.setSize(file.length());
					zipOutput.putArchiveEntry(zipArchiveEntry);
					if (!file.isDirectory()) {
						bufferedInputStream = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
						IOUtils.copy(bufferedInputStream, zipOutput);
					}

					zipOutput.closeArchiveEntry();

				} finally {
					IOUtils.closeQuietly(bufferedInputStream);
				}
			}
			zipOutput.finish();
			zipOutput.close();
		} catch (Exception e) {
			IOUtils.closeQuietly(zipOutput);
			throw e;
		}
	}

	public static Map<String, File> findFile(String path) {
		Map<String, File> map = new HashMap<String, File>();
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (File f : files) {
				list(f, null, map);
			}
		}
		return map;

	}

	private static void list(File f, String parent, Map<String, File> map) {
		String name = f.getName();
		if (parent != null) {
			name = parent + "/" + name;// 设置在zip包里的相对路径
		}
		if (f.isFile()) {
			map.put(name, f);
		} else if (f.isDirectory()) {
			map.put(name + "/", f);
			for (File file : f.listFiles()) {
				list(file, name, map);
			}
		}
	}

	public static List unZip(String zipfile, String destDir) throws Exception {
		File zipFile = new File(zipfile);
		return unZip(zipFile, destDir);
	}

	public static List unZip(File zipfile, String destDir) throws Exception {
		if (StringUtils.isBlank(destDir)) {
			destDir = zipfile.getParent();
		}
		destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
		ZipArchiveInputStream is = null;
		List fileNames = new ArrayList();
		try {
			is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipfile), BUFFER_SIZE));

			ArchiveEntry entry = null;
			File file;
			String fileName;
			while ((entry = is.getNextEntry()) != null) {
				fileName = entry.getName();
				fileNames.add(entry.getName());
				file = new File(destDir, entry.getName());
				if (entry.isDirectory()) {
					if (!file.isDirectory() && !file.mkdirs()) {
						throw new IOException("failed to create directory " + file);
					}
				} else {

					File parent = file.getParentFile();
					if (!parent.isDirectory() && !parent.mkdirs()) {
						throw new IOException("failed to create directory " + parent);
					}
					OutputStream os = null;
					try {
						os = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
						IOUtils.copy(is, os);

					} finally {
						IOUtils.closeQuietly(os);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			IOUtils.closeQuietly(is);
		}
		return fileNames;
	}

}
