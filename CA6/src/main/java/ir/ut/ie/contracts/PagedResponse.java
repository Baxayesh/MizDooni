package ir.ut.ie.contracts;

public record PagedResponse<TItem> (
    long totalItems,
    int offset,
    int limit,
    TItem[] items
){
}
