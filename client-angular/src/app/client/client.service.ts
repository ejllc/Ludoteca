import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Client } from './model/Client';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(
    private http: HttpClient
  ) { }

  getClients(): Observable<Client[]> {
    return this.http.get<Client[]>('http://localhost:8080/client');
  }

  saveClient(client: Client): Observable<Client> {
    let url = 'http://localhost:8080/client';
    if (client.id != null) url += '/' + client.id;

    return this.checkClientNameExists(client.name).pipe(
      switchMap(exists => {
        if (exists) {
          return new Observable<Client>((observer) => {
            observer.error('El nombre del cliente ya existe');
          });
        } else {
          return this.http.put<Client>(url, client);
        }
      })
    );
  }

  deleteClient(idClient: number): Observable<any> {
    return this.http.delete('http://localhost:8080/client/' + idClient);
  }

  getAllClients(): Observable<Client[]> {
    return this.http.get<Client[]>('http://localhost:8080/client');
  }

  checkClientNameExists(clientName: string): Observable<boolean> {
    return this.getAllClients().pipe(
      map(clients => clients.some(client => client.name === clientName))
    );
  }
}
