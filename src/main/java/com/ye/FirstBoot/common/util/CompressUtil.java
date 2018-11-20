package com.ye.FirstBoot.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

		compress("E:\\mydata", "E:\\mydata发送.zip");
		unZip("E:\\mydata发送.zip", "E:\\unzipT");
		// decompressZip2Files("E:\\mydata发送.zip", "E:\\unzipT");

	}

	public static void compress(String sourceFilePath, String compressFile) throws Exception {

		ZipArchiveOutputStream zipOutput = null;
		try {
			File zipFile = new File(compressFile);
			zipOutput = (ZipArchiveOutputStream) new ArchiveStreamFactory()
					.createArchiveOutputStream(ArchiveStreamFactory.ZIP, new FileOutputStream(zipFile));
			zipOutput.setEncoding("UTF-8");
			zipOutput.setUseZip64(Zip64Mode.AsNeeded);
			Map<String, File> fileMap = findFile(sourceFilePath);
			InputStream inputStream = null;
			File file;
			for (Map.Entry<String, File> mapEntry : fileMap.entrySet()) {
				try {
					file = mapEntry.getValue();
					ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, mapEntry.getKey());
					zipOutput.putArchiveEntry(zipArchiveEntry);
					if (!file.isDirectory()) {
						inputStream = new FileInputStream(file);
						IOUtils.copy(inputStream, zipOutput);
					}

					zipOutput.closeArchiveEntry();

				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
				}
			}
			zipOutput.finish();
			zipOutput.close();
		} catch (Exception e) {
			if (zipOutput != null) {
				zipOutput.close();
			}
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

	/**
	 * 将zip压缩包解压成文件到指定文件夹
	 *
	 * @param zipFilePath
	 *            要解压的文件
	 * @param targetDirPath
	 *            解压的目的路径
	 * @return 是否成功
	 */
	public static boolean decompressZip2Files(String zipFilePath, String targetDirPath) {

		InputStream inputStream = null;
		OutputStream outputStream = null;
		// zip文件输入流
		ZipArchiveInputStream zipArchiveInputStream = null;
		ArchiveEntry archiveEntry = null;
		try {
			File zipFile = new File(zipFilePath);
			inputStream = new FileInputStream(zipFile);
			zipArchiveInputStream = new ZipArchiveInputStream(inputStream, "UTF-8");

			while (null != (archiveEntry = zipArchiveInputStream.getNextEntry())) {
				// 获取文件名
				String archiveEntryFileName = archiveEntry.getName();
				// 构造解压后文件的存放路径
				String archiveEntryPath = targetDirPath + archiveEntryFileName;
				// 把解压出来的文件写到指定路径
				File entryFile = new File(archiveEntryPath);
				if (!entryFile.exists()) {
					boolean mkdirs = entryFile.getParentFile().mkdirs();

				}
				byte[] buffer = new byte[1024 * 5];
				outputStream = new FileOutputStream(entryFile);
				int len = -1;
				while ((len = zipArchiveInputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
				}
				outputStream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (null != outputStream) {
					outputStream.close();
				}
				if (null != zipArchiveInputStream) {
					zipArchiveInputStream.close();
				}
				if (null != inputStream) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
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
			is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipfile), BUFFER_SIZE), "UTF-8");

			ArchiveEntry entry = null;
			File file;
			String fileName;
			while ((entry = is.getNextEntry()) != null) {
				fileNames.add(entry.getName());
				if (entry.isDirectory()) {
					File directory = new File(destDir, entry.getName());
					directory.mkdirs();
				} else {
					OutputStream os = null;
					fileName = entry.getName();
					if (fileName.lastIndexOf("/") >= 0) {
						fileName = fileName.substring(0, fileName.lastIndexOf("/"));
						File directory = new File(destDir + "/" + fileName);
						if (!directory.exists()) {
							directory.mkdirs();
						}
					}
					file = new File(destDir, entry.getName());

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
