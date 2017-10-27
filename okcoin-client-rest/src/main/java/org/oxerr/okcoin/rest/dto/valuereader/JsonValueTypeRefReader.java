package org.oxerr.okcoin.rest.dto.valuereader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonValueTypeRefReader<T> implements ValueReader<T> {

	private final Logger log = Logger.getLogger(getClass().getName());

	private final ObjectMapper objectMapper;

	private final TypeReference<T> valueTypeRef;

	public JsonValueTypeRefReader(ObjectMapper objectMapper,
			TypeReference<T> valueTypeRef) {
		this.objectMapper = objectMapper;
		this.valueTypeRef = valueTypeRef;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T read(InputStream inputStream) throws IOException {
		if (log.isLoggable(Level.FINEST)) {
			inputStream = IOUtils.buffer(inputStream);
			inputStream.mark(Integer.MAX_VALUE);
			String s = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			log.log(Level.FINEST, "JSON: {0}", s);
			inputStream.reset();
		}
		return objectMapper.readValue(inputStream, valueTypeRef);
	}

}