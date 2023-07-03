import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';
import { LoanEditComponent } from '../loan-edit/loan-edit.component';
import { LoanService } from '../loan.service';
import { Loan } from '../model/Loan';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { Game } from 'src/app/game/model/Game';
import { Client } from 'src/app/client/model/Client';
import { GameService } from 'src/app/game/game.service';
import { ClientService } from 'src/app/client/client.service';

@Component({
  selector: 'app-loan-list',
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.scss']
})
export class LoanListComponent implements OnInit {

  pageNumber: number = 0;
  pageSize: number = 10;
  totalElements: number = 0;

  loans: Loan[];
  games: Game[];
  clients: Client[];
  filterGame: Game;
  filterClient: Client;
  filterStartDate: Date;

  dataSource = new MatTableDataSource<Loan>();
  displayedColumns: string[] = ['id', 'client', 'game', 'startDate', 'endDate', 'action'];

  constructor(
    private loanService: LoanService,
    private gameService: GameService,
    private clientService: ClientService,
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {

    this.gameService.getGames().subscribe(
      games => this.games = games
    );

    this.clientService.getClients().subscribe(
      clients => this.clients = clients
    )

    this.loadPage();

  }

  onCleanFilter(): void {

    this.filterGame = null;
    this.filterClient = null;
    this.filterStartDate = null;

    this.onSearch();

  }

  filterLoans(): void {
    let filteredLoans = this.loans;

    if (this.filterGame) {
      filteredLoans = filteredLoans.filter(loan => loan.game.id === this.filterGame.id);
    }

    if (this.filterClient) {
      filteredLoans = filteredLoans.filter(loan => loan.client.id === this.filterClient.id);
    }

    if (this.filterStartDate) {
      filteredLoans = filteredLoans.filter(loan => {
        const startDate = new Date(loan.startDate);
        const endDate = new Date(loan.endDate);

        // Verificar si la fecha seleccionada está dentro del rango del préstamo
        return startDate <= this.filterStartDate && (!endDate || endDate >= this.filterStartDate);
      });
    }

    this.totalElements = filteredLoans.length;
    const startIndex = this.pageNumber * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.dataSource.data = filteredLoans.slice(startIndex, endIndex);
  }

  onSearch(): void {
    this.loanService.getAllLoans().subscribe(loans => {
      this.loans = loans;
      this.filterLoans();
    });
  }

  loadPage(event?: PageEvent) {
    if (event != null) {
      this.pageSize = event.pageSize;
      this.pageNumber = event.pageIndex;
    }

    this.loanService.getAllLoans().subscribe(loans => {
      this.loans = loans;
      this.filterLoans();
    });
  }

  createLoan() {
    const dialogRef = this.dialog.open(LoanEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  editLoan(loan: Loan) {
    const dialogRef = this.dialog.open(LoanEditComponent, {
      data: { loan: loan }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  deleteLoan(loan: Loan) {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: { title: "Eliminar préstamo", description: "Atención, si borra el préstamo se perderán sus datos.<br> ¿Desea eliminar el préstamo?" }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loanService.deleteLoan(loan.id).subscribe(result => {
          this.ngOnInit();
        });
      }
    });
  }
}
