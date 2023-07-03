import { Loan } from "src/app/loan/model/Loan";
import { Pageable } from "./page/Pageable";

export class LoanPage {
    content: Loan[];
    pageable: Pageable;
    totalElements: number;
}