package com.au.sofico.dto;

public abstract class AbstractParserRequestDTO {
	
	public String getFileTrfmXmlPath() {
		return fileTrfmXmlPath;
	}

	public void setFileTrfmXmlPath(String fileTrfmXmlPath) {
		this.fileTrfmXmlPath = fileTrfmXmlPath;
	}

	private String filePath;
	
	private String fileTrfmXmlPath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
