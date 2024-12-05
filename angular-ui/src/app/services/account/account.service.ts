import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  API_URL: string = "http://localhost:8080/ACCOUNT-SERVICE/";
  constructor(private http :HttpClient) {

  }

  getAccounts(): Observable<Object>{
    return this.http.get(this.API_URL+"accounts");
  }
}
