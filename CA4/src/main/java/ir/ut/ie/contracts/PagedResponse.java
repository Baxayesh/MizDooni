package ir.ut.ie.contracts;

public record PagedResponse<TItem> (
    int totalItems,
    int offset,
    int limit,
    TItem[] items
){
}
