package com.harmonycloud.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.deserializer.DateFormatDeserializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.fasterxml.jackson.databind.DeserializationConfig;
import org.apache.ibatis.io.ResolverUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyDateFormatDeserializer extends DateFormatDeserializer {
//	private static SerializeConfig mapping = new SerializeConfig();
	private static DateFormatDeserializer mapping = new DateFormatDeserializer();
	private String myFormat;
	public MyDateFormatDeserializer(String myFormat) {
		super();
		this.myFormat = myFormat;
	}

	@Override
	protected <Date> Date cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {
		if (myFormat == null) {
			return null;
		}
		if (val instanceof String) {
			String strVal = (String) val;
			if (strVal.length() == 0) {
				return null;
			}

			try {
				return (Date) new SimpleDateFormat(myFormat).parse((String)val);
			} catch (ParseException e) {
				throw new JSONException("parse error");
			}
		}
		throw new JSONException("parse error");
	}

	public static void main(String[] args) throws IOException, ParseException {

		String json = "{\"name\":\"22323\", \"age\": 1234," +
				" \"birthday\": \"2012-12/12 12:12:12\"}";

	}
}
