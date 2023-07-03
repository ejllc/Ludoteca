import { Component, Inject, OnInit } from '@angular/core';
import { DateAdapter } from '@angular/material/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ClientService } from 'src/app/client/client.service';
import { Client } from 'src/app/client/model/Client';
import { GameService } from 'src/app/game/game.service';
import { Game } from 'src/app/game/model/Game';
import { LoanService } from '../loan.service';
import { Loan } from '../model/Loan';


@Component({
  selector: 'app-loan-edit',
  templateUrl: './loan-edit.component.html',
  styleUrls: ['./loan-edit.component.scss']
})
export class LoanEditComponent implements OnInit {
  loan: Loan;
  clients: Client[];
  games: Game[];
  DATE_FORMAT: string = 'yyyy-MM-dd';

  constructor(
    public dialogRef: MatDialogRef<LoanEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private loanService: LoanService,
    private clientService: ClientService,
    private gameService: GameService,
    private snackBar: MatSnackBar,
    private dateAdapter: DateAdapter<Date>,
  ) { }

  ngOnInit(): void {

    this.dateAdapter.setLocale('es');

    if (this.data.loan != null) {
      this.loan = Object.assign({}, this.data.loan);
    } else {
      this.loan = new Loan();
    }

    this.clientService.getAllClients().subscribe(
      clients => {
        this.clients = clients;

        if (this.data.loan?.client != null) {
          const clientFilter: Client[] = clients.filter(client => client.id === this.data.loan.client.id);
          if (clientFilter.length > 0) {
            this.loan.client = clientFilter[0];
          }
        }
      }
    );

    this.gameService.getAllGames().subscribe(
      games => {
        this.games = games;

        if (this.data.loan?.game != null) {
          const gameFilter: Game[] = games.filter(game => game.id === this.data.loan.game.id);
          if (gameFilter.length > 0) {
            this.loan.game = gameFilter[0];
          }
        }
      }
    );
  }

  onSave() {
    const startDateString = typeof this.loan.startDate === 'string' ? this.loan.startDate : this.loan.startDate.toISOString();
    const endDateString = typeof this.loan.endDate === 'string' ? this.loan.endDate : this.loan.endDate.toISOString();
    const startDate = new Date(startDateString);
    const endDate = new Date(endDateString);
    const currentDate = new Date();

    if (endDate < startDate) {
      this.snackBar.open('La fecha de fin no puede ser anterior a la fecha de inicio.', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    const daysDifference = Math.ceil((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
    if (daysDifference > 14) {
      this.snackBar.open('El período de préstamo máximo es de 14 días.', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    if (startDate < currentDate) {
      this.snackBar.open('La fecha de inicio no puede ser anterior a la fecha actual.', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    this.loanService.checkGameAvailability(this.loan.game.id, startDate, endDate).subscribe(
      gameAvailable => {
        if (!gameAvailable) {
          this.snackBar.open('El juego no está disponible en las fechas seleccionadas.', 'Cerrar', {
            duration: 3000
          });
          return;
        }

        this.loanService.getLoansByClientId(this.loan.client.id).subscribe(
          loans => {
            const overlappingLoans = loans.filter(loan => {
              return (startDate >= loan.startDate && startDate <= loan.endDate) ||
                (endDate >= loan.startDate && endDate <= loan.endDate) ||
                (loan.startDate >= startDate && loan.startDate <= endDate) ||
                (loan.endDate >= startDate && loan.endDate <= endDate);
            });

            if (overlappingLoans.length >= 2) {
              this.snackBar.open('El cliente no puede tener más de 2 préstamos en las fechas seleccionadas.', 'Cerrar', {
                duration: 3000
              });
              return;
            }

            this.loanService.saveLoan(this.loan).subscribe(
              () => {
                this.dialogRef.close(true);
                this.snackBar.open('Préstamo guardado correctamente.', 'Cerrar', {
                  duration: 3000
                });
              },
              error => {
                console.log(error);
                this.snackBar.open('Error al guardar el préstamo.', 'Cerrar', {
                  duration: 3000
                });
              }
            );
          },
          error => {
            console.log(error);
            this.snackBar.open('Error al verificar los préstamos del cliente.', 'Cerrar', {
              duration: 3000
            });
          }
        );
      },
      error => {
        console.log(error);
        this.snackBar.open('Error al verificar la disponibilidad del juego.', 'Cerrar', {
          duration: 3000
        });
      }
    );
  }

  onCancel() {
    this.dialogRef.close(false);
  }

  onClose() {
    this.dialogRef.close();
  }

  convert(str) {
    var date = new Date(str),
      mnth = ("0" + (date.getMonth() + 1)).slice(-2),
      day = ("0" + date.getDate()).slice(-2);
    return [date.getFullYear(), mnth, day].join("-");
  }
}
