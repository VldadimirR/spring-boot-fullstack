import {Component, OnInit} from '@angular/core';
import {CustomerDTO} from "../../models/custoner-dto";
import {CustomerServicesService} from "../../services/customer/customer-services.service";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss']
})
export class CustomerComponent implements OnInit{
  display = false;

  customers: Array<CustomerDTO> = [];
  constructor(
    private customerServices: CustomerServicesService
  ) {
  }

  ngOnInit(): void {

  }

  private findAllCustomers(){
    this.customerServices.findAll()
      .subscribe(
        {
          next: (data) => {
            this.customers = data;
          }
        })
  }
}
