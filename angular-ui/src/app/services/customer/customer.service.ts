import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  API_URL: string = "http://localhost:8080/CUSTOMER-SERVICE/";
  constructor(private http :HttpClient) {

  }

  getCustomers(): Observable<Object>{
    return this.http.get(this.API_URL+"customers");
  }
}
