package com.kyunam.skeleton.common

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import java.io.IOException
import java.util.*

@JsonComponent
class ErrorSerializer(
        private val messageSourceAccessor: MessageSourceAccessor
) : JsonSerializer<Errors>() {
    override fun serialize(errors: Errors, gen: JsonGenerator, serializers: SerializerProvider?) {
        gen.writeStartArray()
        errors.fieldErrors.forEach {
            try {
                gen.writeStartObject()
                gen.writeStringField("field", it.field)
                gen.writeStringField("objectName", it.objectName)
                gen.writeStringField("defaultMessage", getErrorMessage(it))
                gen.writeStringField("rejectedValue", it.rejectedValue.toString())
                gen.writeEndObject()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
        gen.writeEndArray()
    }

    private fun getErrorMessage(fieldError: FieldError): String? {
        val code = getFirstCode(fieldError)
        return if (!code.isPresent) {
            null
        } else messageSourceAccessor.getMessage(code.get(), fieldError.arguments, fieldError.defaultMessage!!)

    }

    private fun getFirstCode(fieldError: FieldError): Optional<String> {
        val codes = fieldError.codes
        return if (codes == null || codes.isEmpty()) {
            Optional.empty()
        } else Optional.of(codes[0])
    }
}
