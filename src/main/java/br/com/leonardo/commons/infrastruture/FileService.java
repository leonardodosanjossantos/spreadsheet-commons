package br.com.leonardo.commons.infrastruture;

import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FileService {

	private static final Logger log = LoggerFactory.getLogger(FileService.class);

	public void removeFile(File file) {
		file.delete();
	}

	public File getFile(String originalFilename, int hashCode, byte[] bytes) {
		File file = null;
		try {
			String fileName = originalFilename;
			file = new File(hashCode + "_" + System.currentTimeMillis() + "."
					+ fileName.substring(fileName.lastIndexOf(".") + 1));
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.close();
		} catch (Throwable e) {
			log.error("Error getting file", e);
		}
		return file;
	}

}
