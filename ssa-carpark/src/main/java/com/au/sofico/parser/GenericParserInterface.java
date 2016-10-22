package com.au.sofico.parser;

import java.util.List;

public interface GenericParserInterface<T,S> {
	public List<T> parse(S type) throws Exception;

}
