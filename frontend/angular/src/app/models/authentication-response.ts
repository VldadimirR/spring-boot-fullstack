import {CustomerDTO} from "./custoner-dto";
export interface AuthenticationResponse {

  token? : string;

  customerDTO: CustomerDTO;
}
