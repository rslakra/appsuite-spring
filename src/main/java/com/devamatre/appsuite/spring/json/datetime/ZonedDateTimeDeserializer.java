package com.devamatre.appsuite.spring.json.datetime;

import com.devamatre.framework.core.TimeUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Rohtash Lakra
 * @created 3/21/23 12:36 PM
 */
@Component
public final class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    /**
     * @param jsonParser
     * @param deserializationContext
     * @return
     * @throws IOException
     */
    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
        return ZonedDateTime.parse(jsonParser.getText(), DateTimeFormatter.ofPattern(TimeUtils.ZONED_DATE_TIME_FORMAT));
    }
}
