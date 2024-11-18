package com.tpIngSoft1.restApi.rules.rule;

import com.tpIngSoft1.restApi.domain.Variant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = And.class, name = "And"),
        @JsonSubTypes.Type(value = Or.class, name = "Or"),
        @JsonSubTypes.Type(value = Not.class, name = "Not"),
        @JsonSubTypes.Type(value = Comparison.class, name = "Comparison"),
})
public interface Rule {
    boolean evaluate(List<Variant> variants);
    String toString();
    List<String> getError();
}
