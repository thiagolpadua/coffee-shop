import { catchError, map } from 'rxjs/operators';
import { Product } from './product.model';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient } from '@angular/common/http';
import { EMPTY, Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ProductService {

  baseUrl = 'http://localhost:8080/item';

  constructor(private snackBar: MatSnackBar,
    private htttp: HttpClient) { }

  showMessage(msg: string, isError: boolean = false): void {
    this.snackBar.open(msg, 'X', {
      duration: 3000,
      horizontalPosition: "right",
      verticalPosition: "top",
      panelClass: isError ? ['msg-error'] : ['msg-success']
    });
  }

  create(product: Product): Observable<Product> {
    return this.htttp.post<Product>(this.baseUrl, product).pipe(
      map((obj) => obj),
      catchError((error) => this.errorHandler(error))
    );
  }

  read(): Observable<Product[]> {
    return this.htttp.get<Product[]>(this.baseUrl).pipe(
      map((obj) => obj),
      catchError((error) => this.errorHandler(error))
    );
  }

  errorHandler(e: any): Observable<any> {
    this.showMessage('Something wrong happened!', true);
    return EMPTY;
  }

}
