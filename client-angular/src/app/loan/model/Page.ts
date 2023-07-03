export interface Page<T> {
    content: T[];
    pageable: any;
    totalElements: number;
}