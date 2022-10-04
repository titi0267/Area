export interface RawRegisterBody {
  firstName: string | undefined;
  lastName: string | undefined;
  email: string | undefined;
  password: string | undefined;
}

export interface FormatedRegisterBody {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}
