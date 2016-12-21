/*
 * Copyright (C) 2009-2016 Lightbend Inc. <https://www.lightbend.com>
 */
package play.mvc;

import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import play.libs.Json;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StatusHeaderTest {

    @Test
    public void sendJsonUsesObjectMapperJsonFactory() throws Exception {
        ObjectMapper originalMapper = Json.mapper();

        ObjectMapper mapper = mock(ObjectMapper.class);
        JsonFactory factory = mock(JsonFactory.class);
        when(mapper.getFactory()).thenReturn(factory);
        JsonGenerator generator = mock(JsonGenerator.class);
        when(factory.createGenerator(any(OutputStream.class), any(JsonEncoding.class))).thenReturn(generator);

        Json.setObjectMapper(mapper);

        StatusHeader statusHeader = new StatusHeader(Http.Status.OK);
        statusHeader.sendJson(originalMapper.createObjectNode(), JsonEncoding.UTF8);

        verify(factory).createGenerator(any(OutputStream.class), any(JsonEncoding.class));
    }

}
