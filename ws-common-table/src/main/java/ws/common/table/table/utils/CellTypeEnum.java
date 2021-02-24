package ws.common.table.table.utils;

public enum CellTypeEnum {
    INT("int"), //
    FLOAT("float"), //
    BOOL("bool"), //
    STRING("string"), //
    INT_2("int2"), INT_3("int3"), INT_4("int4"), INT_5("int5"), //
    FLOAT_2("float2"), FLOAT_3("float3"), FLOAT_4("float4"), FLOAT_5("float5"), //
    ARRAY_INT("arrayint"), //
    ARRAY_FLOAT("arrayfloat"), //
    ARRAY_STRING("arraystring"), //
    ARRAY_INT_2("arrayint2"), ARRAY_INT_3("arrayint3"), ARRAY_INT_4("arrayint4"), ARRAY_INT_5("arrayint5"), //
    ARRAY_FLOAT_2("arrayfloat2"), ARRAY_FLOAT_3("arrayfloat3"), ARRAY_FLOAT_4("arrayfloat4"), ARRAY_FLOAT_5("arrayfloat5"),//
    ;//
    private String name;

    private CellTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
