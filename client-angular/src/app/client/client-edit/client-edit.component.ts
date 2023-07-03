import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ClientService } from '../client.service';
import { Client } from '../model/Client';

@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.scss']
})
export class ClientEditComponent implements OnInit {

  client: Client;
  existingClientName: string; // Nombre del cliente existente antes de realizar cambios

  constructor(
    public dialogRef: MatDialogRef<ClientEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private clientService: ClientService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    if (this.data.client != null) {
      this.client = Object.assign({}, this.data.client);
      this.existingClientName = this.client.name; // Guardar el nombre original del cliente
    } else {
      this.client = new Client();
    }
  }

  onSave() {
    // Verificar si el nombre del cliente ya existe
    if (this.existingClientName && this.client.name === this.existingClientName) {
      // El nombre no ha cambiado, permitir guardar
      this.guardarCliente();
    } else {
      // Verificar si el nombre ya existe en la base de datos
      this.clientService.checkClientNameExists(this.client.name).subscribe(existe => {
        if (existe) {
          // El nombre ya existe, mostrar snackbar de error
          this.snackBar.open('El nombre del cliente ya existe. Por favor, elige un nombre diferente.', 'Cerrar', { duration: 3000 });
        } else {
          // El nombre es único, permitir guardar
          this.guardarCliente();
        }
      });
    }
  }

  guardarCliente() {
    this.clientService.saveClient(this.client).subscribe(resultado => {
      this.dialogRef.close();
    });
  }

  onClose() {
    this.dialogRef.close();
  }
}
