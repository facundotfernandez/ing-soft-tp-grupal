package com.tpIngSoft1.restApi.rules.operator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tpIngSoft1.restApi.rules.rule.And;
import com.tpIngSoft1.restApi.rules.rule.Comparison;
import com.tpIngSoft1.restApi.rules.rule.Not;
import com.tpIngSoft1.restApi.rules.rule.Or;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Equal.class, name = "Equal"),
        @JsonSubTypes.Type(value = NotEqual.class, name = "NotEqual"),
        @JsonSubTypes.Type(value = LessThan.class, name = "LessThan"),
        @JsonSubTypes.Type(value = LessOrEqualThan.class, name = "LessOrEqualThan"),
        @JsonSubTypes.Type(value = GreaterThan.class, name = "GreaterThan"),
})
public interface Comparable {
    String toString();
    boolean compare (String val1, String val2);
}
