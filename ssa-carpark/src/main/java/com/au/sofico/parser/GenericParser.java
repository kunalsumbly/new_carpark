package com.au.sofico.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;

import com.au.sofico.dto.AbstractParserRequestDTO;
import com.au.sofico.dto.AbstractParserResponseDTO;
import com.au.sofico.parser.metadata.ParseFields;
import com.au.sofico.parser.metadata.ParseMetaObject;


public abstract class GenericParser
		implements GenericParserInterface<AbstractParserResponseDTO, AbstractParserRequestDTO> {
	
	private int startPositing;

	@Override
	public List<AbstractParserResponseDTO> parse(AbstractParserRequestDTO type) throws Exception {
		AbstractParserResponseDTO responseDto = null;
		// read the external xml file
		File metaXmlFile = new File(type.getFileTrfmXmlPath());
		File flatFile = new File(type.getFilePath());

		FileInputStream fis = new FileInputStream(flatFile);
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
					startPositing=0;
					responseDto = (AbstractParserResponseDTO) parse(parseMetaObject.getParseFields(), null, null, line,0);
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

	}

	private Object parse(ParseFields parseFields, Class wrapper, Object classObject, String oneLine, int startPos)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object obj = classObject;

		if (!"complex".equals(parseFields.getTargetFieldType())) {
			/*
			 * if (wrapper != null) { obj = wrapper.newInstance();
			 * 
			 * }
			 */
			processSimpleType(parseFields, wrapper, oneLine, startPos, obj);

		} else {// complex type object
			Class clazzWrapper = Class.forName(parseFields.getClassName()); // EmployeeParserResponseDTO

			// child of classWrapper, so have to be set in the clazzWrapper
			for (int i = 0; i < parseFields.getParseFields().size(); i++) {
				ParseFields childparsefields = parseFields.getParseFields().get(i);
				
				// }
				if (!"complex".equals(childparsefields.getTargetFieldType())) {// employee
																			// dto

					processSimpleType(childparsefields, wrapper, oneLine, startPos, obj);

				} else {// complex object
					Class childClazz = Class.forName(childparsefields.getClassName()); // parsefields.getClassName() would be null for simple types
					if (obj == null) {
					obj = clazzWrapper.newInstance();
					}
					Object childObject = null;
					if (childClazz != null) {
						childObject = childClazz.newInstance();

					}
					// set the child object to parent object
					Method m = clazzWrapper.getDeclaredMethod("set" + childparsefields.getTargetFieldName(), childClazz);
					m.invoke(obj, childObject);
					parse(childparsefields, childClazz, childObject, oneLine, startPos);
				}

			}

		}
		return obj;
	}

	private void processSimpleType(ParseFields parseFields, Class wrapper, String oneLine, int startPos, Object obj)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if ("string".equals(parseFields.getTargetFieldType())) {
			Method m = wrapper.getDeclaredMethod("set" + parseFields.getTargetFieldName(), String.class);
			m.invoke(obj, oneLine.substring(startPositing, parseFields.getTargetFieldLength().intValue()+startPositing));
			startPositing = startPositing + parseFields.getTargetFieldLength().intValue();

		} else if ("integer".equals(parseFields.getTargetFieldType())) {
			Method m = wrapper.getDeclaredMethod("set" + parseFields.getTargetFieldName(), Integer.TYPE);
			m.invoke(obj, Integer.valueOf(oneLine.substring(startPositing, parseFields.getTargetFieldLength().intValue()+startPositing)));
			startPositing = startPositing + parseFields.getTargetFieldLength().intValue();

		} else if ("boolean".equals(parseFields.getTargetFieldType())) {
			Method m = wrapper.getDeclaredMethod("set" + parseFields.getTargetFieldName(), wrapper);
			m.invoke(obj, oneLine.substring(startPositing, parseFields.getTargetFieldLength().intValue()+startPositing));
			startPositing = startPositing + parseFields.getTargetFieldLength().intValue();

		}
	}

}
