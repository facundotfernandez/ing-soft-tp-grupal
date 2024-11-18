package com.tpIngSoft1.restApi.rules.rule.condition;

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
        @JsonSubTypes.Type(value = VarAnd.class, name = "VarAnd"),
        @JsonSubTypes.Type(value = VarOr.class, name = "VarOr"),
        @JsonSubTypes.Type(value = VarNot.class, name = "VarNot"),
        @JsonSubTypes.Type(value = VarCondition.class, name = "VarCondition"),
})
public interface VariantRule {
    String toString();
    boolean compare(Variant variants);
}
