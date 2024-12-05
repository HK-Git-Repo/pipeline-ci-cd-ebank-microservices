import {Component, OnInit} from '@angular/core';
import {CustomerService} from '../services/customer/customer.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.css'
})
export class CustomerComponent implements OnInit {
  customers: any;

  constructor(private service: CustomerService) {
  }

  ngOnInit(): void {
    this.service.getCustomers().subscribe(
      {
        next: data => {
          this.customers = data
        },
        error: err => {
          console.log(err)
        },
      }
    );
  }
}
