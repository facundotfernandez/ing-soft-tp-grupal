package com.tpIngSoft1.restApi.rules.ruleType;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.rule.condition.VariantRule;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = All.class, name = "All"),
        @JsonSubTypes.Type(value = Count.class, name = "Count"),
        @JsonSubTypes.Type(value = CountAny.class, name = "CountAny"),
        @JsonSubTypes.Type(value = Individual.class, name = "Individual"),
        @JsonSubTypes.Type(value = Mean.class, name = "Mean"),
        @JsonSubTypes.Type(value = Sum.class, name = "Sum"),
})
public interface Evaluable {
    String toString();
    boolean evaluate(List<Variant> variantes, VariantRule condition);
}
