package com.au.sofico.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;

import com.au.sofico.dto.AbstractParserRequestDTO;
import com.au.sofico.dto.AbstractParserResponseDTO;
import com.au.sofico.parser.metadata.ParseMetaObject;



public class FlatFileParser extends GenericParser {

	@Override
	public List<AbstractParserResponseDTO> parse(AbstractParserRequestDTO type) throws Exception {
		AbstractParserResponseDTO responseDto = null;
		FileInputStream fis = null;
		// read the external xml file
		try{
		File metaXmlFile = new File(type.getFileTrfmXmlPath());
		File flatFile = new File(type.getFilePath());

		 fis = new FileInputStream(flatFile);
		// Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null;
		JAXBContext jaxbContext = JAXBContext.newInstance(ParseMetaObject.class);
		ParseMetaObject parseMetaObject = (ParseMetaObject) jaxbContext.createUnmarshaller().unmarshal(metaXmlFile);
		List<AbstractParserResponseDTO> responseDtoList = new ArrayList<AbstractParserResponseDTO>();
		if ("reader".equals(parseMetaObject.getType())) {
			while ((line = br.readLine()) != null) {
				if (parseMetaObject.getParseFields() != null) {
					// the return should be the response
					setStartPositing(0);
					responseDto = (AbstractParserResponseDTO) parse(parseMetaObject.getParseFields(), null, null, line,0,type.getParserType(),null);
					responseDtoList.add(responseDto);
				}
			}
			//System.out.println("the list size "+employeeList.size());
		} else {// writer

		}

		// transform it to pojo, using JAXB

		// based on meta XMLFile
		// navigate the structure in XML
		// based on the object type set the property of the target object
		// recursion
	
		return responseDtoList;
		}finally{
			fis.close();
		}

	}
}
