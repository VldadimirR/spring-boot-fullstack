import { Component, OnInit } from '@angular/core';
import { CustomerRegistrationRequest } from '../../models/customer-registration-request';
import { ConfirmationService, MessageService } from 'primeng/api';
import {CustomerServicesService} from "../../services/customer/customer-services.service";
import {CustomerDTO} from "../../models/custoner-dto";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss']
})
export class CustomerComponent implements OnInit{
  display = false;

  customers: Array<CustomerDTO> = [];

  customer: CustomerRegistrationRequest = {};

  operation: 'create' | 'update' = 'create';
  constructor(
    private customerServices: CustomerServicesService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {
  }

  ngOnInit(): void {
    this.findAllCustomers();
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

  createCustomer() {
    this.display = true;
    this.customer = {};
    this.operation = 'create';
  }

  save(customer: CustomerRegistrationRequest) {
    if (customer) {
      if (this.operation === 'create') {
        this.customerServices.registerCustomer(customer)
          .subscribe({
            next: () => {
              this.findAllCustomers();
              this.display = false;
              this.customer = {};
              this.messageService.add(
                {severity:'success',
                  summary: 'Customer saved',
                  detail: `Customer ${customer.name} was successfully saved`
                }
              );
            }
          });
      } else if (this.operation === 'update') {
        this.customerServices.updateCustomer(customer.id, customer)
          .subscribe({
            next: () => {
              this.findAllCustomers();
              this.display = false;
              this.customer = {};
              this.messageService.add(
                {
                  severity:'success',
                  summary: 'Customer updated',
                  detail: `Customer ${customer.name} was successfully updated`
                }
              );
            }
          });
      }
    }
  }

  deleteCustomer(customer: CustomerDTO) {
    this.confirmationService.confirm({
      header: 'Delete customer',
      message: `Are you sure you want to delete ${customer.name}? You can\'t undo this action afterwords`,
      accept: () => {
        this.customerServices.deleteCustomer(customer.id)
          .subscribe({
            next: () => {
              this.findAllCustomers();
              this.messageService.add(
                {
                  severity:'success',
                  summary: 'Customer deleted',
                  detail: `Customer ${customer.name} was successfully deleted`
                }
              );
            }
          });
      }
    });
  }

  updateCustomer(customerDTO: CustomerDTO) {
    this.display = true;
    this.customer = customerDTO;
    this.operation = 'update';
  }

  cancel() {
    this.display = false;
    this.customer = {};
    this.operation = 'create';
  }


}
