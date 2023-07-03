import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { LoanPage } from '../core/model/LoanPage';
import { Pageable } from '../core/model/page/Pageable';
import { Loan } from './model/Loan';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  constructor(private http: HttpClient) { }

  getLoans(pageable: Pageable): Observable<LoanPage> {
    return this.http.post<LoanPage>('http://localhost:8080/loan', { pageable: pageable });
  }

  saveLoan(loan: Loan): Observable<void> {
    let url = 'http://localhost:8080/loan';
    if (loan.id != null) url += '/' + loan.id;
    return this.http.put<void>(url, loan);
  }

  deleteLoan(idLoan: number): Observable<void> {
    return this.http.delete<void>('http://localhost:8080/loan/' + idLoan);
  }

  getAllLoans(): Observable<Loan[]> {
    return this.http.get<Loan[]>('http://localhost:8080/loan');
  }

  checkGameAvailability(gameId: number, startDate: Date, endDate: Date): Observable<boolean> {
    const startDateString = startDate.toISOString().split('T')[0];
    const endDateString = endDate.toISOString().split('T')[0];

    return this.http.get<Loan[]>('http://localhost:8080/loan').pipe(
      map(loans => {
        const overlappingLoan = loans.find(
          loan =>
            loan.game.id === gameId &&
            (new Date(loan.startDate) <= new Date(endDateString) && new Date(loan.endDate) >= new Date(startDateString))
        );

        return !overlappingLoan;
      }),
      catchError(() => of(false))
    );
  }

  checkClientAvailability(clientId: number): Observable<boolean> {
    return this.http.get<Loan[]>('http://localhost:8080/loan').pipe(
      map(loans => {
        const activeLoans = loans.filter(loan => loan.client.id === clientId && !loan.returned);
        return activeLoans.length < 2;
      }),
      catchError(() => of(false))
    );
  }

  getLoansByClientId(clientId: number): Observable<Loan[]> {
    return this.http.get<Loan[]>('http://localhost:8080/loan?clientId=' + clientId);
  }
}
