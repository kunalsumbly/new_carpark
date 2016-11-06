package com.au.sofico.parser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.au.sofico.dto.AbstractParserRequestDTO;
import com.au.sofico.dto.AbstractParserResponseDTO;
import com.au.sofico.parser.metadata.ParseFields;



public abstract class GenericParser
		implements GenericParserInterface<AbstractParserResponseDTO, AbstractParserRequestDTO> {

	private int startPositing;
	
	private String [] stringArray;
	

	
	private List<? extends Object> list = null;

	public int getStartPositing() {
		return startPositing;
	}

	public void setStartPositing(int startPositing) {
		this.startPositing = startPositing;
	}

	protected Object parse(ParseFields parseFields, Class wrapper, Object classObject, String oneLine, int startPos,
			String fileType, String sectionId) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
		
		
		Object obj = classObject;
		
		

		if (!"complex".equals(parseFields.getTargetFieldType())) {
			/*
			 * if (wrapper != null) { obj = wrapper.newInstance();
			 * 
			 * }
			 */
			if ("flatfile".equals(fileType)) {
				processSimpleType(parseFields, wrapper, oneLine, startPos, obj);
			} else {
				processSimpleTypeForExcel(parseFields, wrapper, oneLine, startPos, obj);
			}

		} else {// complex type object
			Class clazzWrapper = Class.forName(parseFields.getClassName()); // EmployeeParserResponseDTO

			// child of classWrapper, so have to be set in the clazzWrapper
			for (int i = 0; i < parseFields.getParseFields().size(); i++) {
				ParseFields childparsefields = parseFields.getParseFields().get(i);

				if (!"flatfile".equals(fileType) && sectionId != null && childparsefields.getId() != null && !sectionId.equals(childparsefields.getId())) {
					continue;			
				}
				// }
				if (!"complex".equals(childparsefields.getTargetFieldType())) {// employee
																				// dto
					if ("flatfile".equals(fileType)) {
						processSimpleType(childparsefields, wrapper, oneLine, startPos, obj);
					} else {
						processSimpleTypeForExcel(childparsefields, wrapper, oneLine, startPos, obj);
					}

				} else {// complex object
					if (childparsefields.isIsList() == null || !childparsefields.isIsList()) {

						Class childClazz = Class.forName(childparsefields.getClassName()); // parsefields.getClassName()
																							// would
																							// be
																							// null
																							// for
																							// simple
																							// types
						if (obj == null) {
							obj = clazzWrapper.newInstance();
						}
						Object childObject = null;
						if (childClazz != null) {
							childObject = childClazz.newInstance();

						}
						// set the child object to parent object
						Method m = clazzWrapper.getDeclaredMethod("set" + childparsefields.getTargetFieldName(),
								childClazz);
						m.invoke(obj, childObject);
						parse(childparsefields, childClazz, childObject, oneLine, startPos, fileType, sectionId);
					} else {
							
						Class childClazz = Class.forName(childparsefields.getClassName()); // parsefields.getClassName()
						// would
						// be
						// null
						// for
						// simple
						// types
						if (obj == null) {
							obj = clazzWrapper.newInstance();
						}
						Object childObject = null;
						if (childClazz != null) {
							childObject = childClazz.newInstance();
						
						}
						// set the child object to parent object
 						Method m = clazzWrapper.getDeclaredMethod("get" + childparsefields.getTargetFieldName());
						((List) m.invoke(obj)).add(childObject);
						parse(childparsefields, childClazz, childObject, oneLine, startPos, fileType, sectionId);
						
						
					}
				}

			}

		}
		//alreadyParsedSheet.put(sectionId, sectionId);
		return obj;
	}

	protected void processSimpleType(ParseFields parseFields, Class wrapper, String oneLine, int startPos, Object obj)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		if ("string".equals(parseFields.getTargetFieldType())) {
			Method m = wrapper.getDeclaredMethod("set" + parseFields.getTargetFieldName(), String.class);
			m.invoke(obj,
					oneLine.substring(startPositing, parseFields.getTargetFieldLength().intValue() + startPositing));
			startPositing = startPositing + parseFields.getTargetFieldLength().intValue();

		} else if ("integer".equals(parseFields.getTargetFieldType())) {
			Method m = wrapper.getDeclaredMethod("set" + parseFields.getTargetFieldName(), Integer.TYPE);
			m.invoke(obj, Integer.valueOf(
					oneLine.substring(startPositing, parseFields.getTargetFieldLength().intValue() + startPositing)));
			startPositing = startPositing + parseFields.getTargetFieldLength().intValue();

		} else if ("boolean".equals(parseFields.getTargetFieldType())) {
			Method m = wrapper.getDeclaredMethod("set" + parseFields.getTargetFieldName(), wrapper);
			m.invoke(obj,
					oneLine.substring(startPositing, parseFields.getTargetFieldLength().intValue() + startPositing));
			startPositing = startPositing + parseFields.getTargetFieldLength().intValue();

		}
	}

	protected void processSimpleTypeForExcel(ParseFields parseFields, Class wrapper, String oneLine, int startPos,
			Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			IllegalArgumentException, ParseException {
		
		
		if(stringArray == null || stringArray.length == 0){
			 stringArray = oneLine.split("\\|~\\|");
		}
		

		for (int i = 0; i < stringArray.length; i++) {
			String strToken = stringArray[i];
			if (strToken != null) {
				if ("string".equals(parseFields.getTargetFieldType())) {
					Method m = wrapper.getDeclaredMethod("set" + parseFields.getTargetFieldName(), String.class);
					stringArray=removeTheElementFromArray(stringArray,strToken);
					m.invoke(obj, strToken);
					break;
				} else if ("integer".equals(parseFields.getTargetFieldType())) {
					Method m = wrapper.getDeclaredMethod("set" + parseFields.getTargetFieldName(), Integer.TYPE);
					m.invoke(obj, Integer.valueOf(strToken));
					stringArray=removeTheElementFromArray(stringArray,strToken);
					break;
				} else if ("boolean".equals(parseFields.getTargetFieldType())) {
					Method m = wrapper.getDeclaredMethod("set" + parseFields.getTargetFieldName(), Boolean.TYPE);
					m.invoke(obj, Boolean.valueOf(strToken.equals("Y") ? true : false));
					stringArray=removeTheElementFromArray(stringArray,strToken);
					break;
				} else if ("date".equals(parseFields.getTargetFieldType())) {
					SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

					Method m = wrapper.getDeclaredMethod("set" + parseFields.getTargetFieldName(), Date.class);
					if(!"NULL".equals(strToken)){
						m.invoke(obj, sf.parse(strToken));
					}else{
						strToken=null;
						m.invoke(obj, strToken);
					}
					
					stringArray=removeTheElementFromArray(stringArray,strToken);
					break;
					// m.invoke(obj, ); }
				}

			}

		}
	}

	private static String [] removeTheElementFromArray(String[] stringArray2, String strToken) {
		String [] newArray = new String [stringArray2.length-1];
		int j=0,k=0;
		for (int i = 0; i < stringArray2.length -1; i++) {
			newArray[j] = stringArray2[i+1];
			/*if (strToken.equals(stringArray2[i])) {
				newArray[j] = stringArray2[i+1];
				
			}else{
				newArray[j] = stringArray2[i+1];
			}*/
			j++;
		}
		
		return newArray;
		
		
	}
	
	/*public static void main(String[] args) {
		String [] astr= new String [4];
		astr[0] = "1";
		astr[1] = "2";
		astr[2] = "2";
		astr[3] = "1";
		String token="2";
		astr = removeTheElementFromArray(astr, token);
		for(int i=0;i < astr.length ;i++){
			System.out.println(astr[i]);
		}
		//System.out.println();
	}*/
}
